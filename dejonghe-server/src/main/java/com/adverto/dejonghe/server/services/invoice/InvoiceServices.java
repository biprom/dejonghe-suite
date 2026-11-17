package com.adverto.dejonghe.server.services.invoice;

import com.adverto.dejonghe.common.dbservices.DeviceService;
import com.adverto.dejonghe.common.entities.images.DocumentBundle;
import com.adverto.dejonghe.common.entities.installation.Device;
import com.adverto.dejonghe.server.Controllers.BillitController;
import com.adverto.dejonghe.server.Controllers.PdfController;
import com.adverto.dejonghe.server.DTO.billitDto.AddressDTO;
import com.adverto.dejonghe.server.DTO.billitDto.CustomerDTO;
import com.adverto.dejonghe.server.DTO.billitDto.IdentifiersDTO;
import com.adverto.dejonghe.server.DTO.billitDto.InvoiceDTO;
import com.adverto.dejonghe.server.DTO.billitDto.OrderLinesDTO;
import com.adverto.dejonghe.server.DTO.billitDto.OrderPDFDTO;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderHeader;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderTime;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.enums.fleet.Fleet;
import com.adverto.dejonghe.common.entities.enums.fleet.FleetWorkType;
import com.adverto.dejonghe.common.entities.enums.product.VAT;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkLocation;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkType;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.implementations.ProductImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InvoiceServices {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;
    @Autowired
    PdfController pdfController;
    @Autowired
    BillitController billitController;
    @Autowired
    DeviceService deviceService;

    @Value("${rootTemplateProforma}")
    FileSystemResource proformaResourceJRXML;
    @Value("${rootTemplateInvoice}")
    FileSystemResource invoiceResourceJRXML;
    @Value("${rootTemplateCreditNote}")
    FileSystemResource creditNoteResourceJRXML;
    @Value("${rootTemplateAttachement}")
    FileSystemResource attachementResourceJRXML;
    @Value( "${rootFolder}" )
    private String rootFolder;

    Map<String, Object> parameters = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    List<JasperPrint> prints = new ArrayList<>();
    JRPdfExporter exporter = new JRPdfExporter();

    ByteArrayOutputStream baosAttachement = new ByteArrayOutputStream();

    byte[] invoiceBytes;
    byte[] attachementBytes;

    String base64PdfInvoice;
    String base64PdfAttachement;

    String response;

    ConfirmDialog noCustomerDialog;

    public InvoiceServices() {
        setUpNoCustomerDialog();
    }

    private void setUpNoCustomerDialog() {
        noCustomerDialog = new ConfirmDialog();
        noCustomerDialog.setCloseOnEsc(false);
        noCustomerDialog.setWidth("50%");
        noCustomerDialog.add(new H2("Deze werkbon bevat geen geldige klant!"));
        noCustomerDialog.add(new H3("Gelieve de klant in de database in te geven en deze opnieuw in de werkbon te selecteren."));
        noCustomerDialog.setConfirmText("OK");
        noCustomerDialog.addConfirmListener(event -> noCustomerDialog.close());
    }

    private static final Map<String, Pattern> VAT_PATTERNS = Map.of(
            "BE", Pattern.compile("^BE(\\d{4})(\\d{3})(\\d{3})$"),
            "NL", Pattern.compile("^NL(\\d{9})B(\\d{2})$"),
            "DE", Pattern.compile("^DE(\\d{9})$"),
            "FR", Pattern.compile("^FR([A-Z0-9]{2})(\\d{9})$"),
            "LU", Pattern.compile("^LU(\\d{8})$")
    );

    public String vatFormat(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }

        // 1. Opschonen
        String cleaned = input
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");

        // 2. Landcode detecteren
        if (cleaned.length() < 2) {
            return cleaned;
        }

        String country = cleaned.substring(0, 2);
        Pattern pattern = VAT_PATTERNS.get(country);

        if (pattern == null) {
            // Onbekend land → geen formattering
            return cleaned;
        }

        // 3. Formatteren
        Matcher matcher = pattern.matcher(cleaned);
        if (!matcher.matches()) {
            // Onvolledig of fout → toon ruwe input
            return cleaned;
        }

        return switch (country) {
            case "BE" -> "BE" + matcher.group(1) + "." + matcher.group(2) + "." + matcher.group(3);
            case "NL" -> "NL" + matcher.group(1) + "B" + matcher.group(2);
            case "DE" -> "DE" + matcher.group(1);
            case "FR" -> "FR " + matcher.group(1) + " " + matcher.group(2);
            default -> cleaned;
        };
    }

    public Integer getNewProFormaInvoiceNumber() {
        Optional<Invoice> optionalInvoice = invoiceService.getLastProFormaInvoice();
        if(!optionalInvoice.isEmpty()){
            return Integer.valueOf(optionalInvoice.get().getInvoiceNumber()+1);
        }
        else{
            return 260001;
        }
    }

    public Integer getNewFinalInvoiceNumber() {
        Optional<Invoice> optionalInvoice = invoiceService.getLastFinalInvoice();
        if(!optionalInvoice.isEmpty()){
            return Integer.valueOf(optionalInvoice.get().getFinalInvoiceNumber()+1);
        }
        else{
            return 260001;
        }
    }

    private int compareOnderdeel(String s1, String s2) {
        List<Object> parts1 = splitAlphaNumeric(s1);
        List<Object> parts2 = splitAlphaNumeric(s2);

        int len = Math.min(parts1.size(), parts2.size());

        for (int i = 0; i < len; i++) {
            Object p1 = parts1.get(i);
            Object p2 = parts2.get(i);

            int cmp;
            if (p1 instanceof String && p2 instanceof String) {
                cmp = ((String) p1).compareToIgnoreCase((String) p2);
            } else if (p1 instanceof Number && p2 instanceof Number) {
                cmp = Double.compare(((Number) p1).doubleValue(), ((Number) p2).doubleValue());
            } else {
                // String vs Number → String komt altijd eerst
                cmp = (p1 instanceof String) ? -1 : 1;
            }

            if (cmp != 0) return cmp;
        }

        // Als alles gelijk is, kortere string komt eerst
        return Integer.compare(parts1.size(), parts2.size());
    }

    private List<Object> splitAlphaNumeric(String input) {
        List<Object> parts = new ArrayList<>();
        if(input == null){
            input = "";
        }
        Matcher matcher = Pattern.compile("(\\d+[\\.,]?\\d*|\\D+)").matcher(input);
        while (matcher.find()) {
            String part = matcher.group(1).trim();
            if (part.matches("\\d+[\\.,]?\\d*")) {
                part = part.replace(",", "."); // vervang komma door punt
                try {
                    parts.add(Double.parseDouble(part));
                } catch (NumberFormatException e) {
                    parts.add(part); // fallback: behandel als string
                }
            } else {
                parts.add(part);
            }
        }
        return parts;
    }

    private List<String> splitText(String text, int maxLength) {
        List<String> result = new ArrayList<>();

        for (String line : text.split("\\R")) {
            String remaining = line.trim();

            while (remaining.length() > maxLength) {
                int splitPos = remaining.lastIndexOf(' ', maxLength);

                // Geen spatie gevonden? Hard afkappen op maxLength
                if (splitPos <= 0) {
                    splitPos = maxLength;
                }

                result.add(remaining.substring(0, splitPos).trim());
                remaining = remaining.substring(splitPos).trim();
            }

            if (!remaining.isEmpty()) {
                result.add(remaining);
            }
        }

        return result;
    }

    /** Ongeldige of ontbrekende afstand wordt behandeld als 0 km. */
    private double getValidDistance(Address address) {
        if (address == null || address.getDistance() == null) {
            return 0.0;
        }
        double distance = address.getDistance();
        return Double.isFinite(distance) && distance >= 0.0 ? distance : 0.0;
    }

    /** Zichtbare controle voor facturatie bij nog niet ingevulde kilometers. */
    private void markDistanceForInvoiceReview(Product kilometerProduct, double kilometers) {
        if (kilometers == 0.0) {
            kilometerProduct.setInternalName(
                    kilometerProduct.getInternalName() + " - AFSTAND NOG TE CONTROLEREN (0 km)");
        }
    }

    /**
     * Samengestelde proforma.
     *
     * De geldelijke lijnen worden altijd via dezelfde werkwijze per werkbon
     * gemaakt als de proforma per dag. Hierdoor worden minimumbedragen,
     * opleggerforfaits, kraanuren en aftrekken identiek toegepast.
     * Enkel de sortering en datumweergave verschillen.
     */
    public Invoice generateMergedInvoice(Set<WorkOrder> workOrderSet) {
        Invoice invoice = getnerateInvoicePerDay(workOrderSet);
        if (invoice == null) {
            return null;
        }

        LocalDate starterDate = workOrderSet.stream()
                .filter(Objects::nonNull)
                .filter(wo -> Boolean.TRUE.equals(wo.getStarter()))
                .map(WorkOrder::getWorkDateTime)
                .filter(Objects::nonNull)
                .map(LocalDateTime::toLocalDate)
                .findFirst()
                .orElseGet(() -> workOrderSet.stream()
                        .filter(Objects::nonNull)
                        .map(WorkOrder::getWorkDateTime)
                        .filter(Objects::nonNull)
                        .map(LocalDateTime::toLocalDate)
                        .min(LocalDate::compareTo)
                        .orElse(LocalDate.now()));

        List<Product> comments = new ArrayList<>();
        List<Product> materials = new ArrayList<>();
        List<Product> workHours = new ArrayList<>();
        List<Product> travel = new ArrayList<>();

        for (Product product : invoice.getProductList()) {
            if (product == null) {
                continue;
            }
            if (Boolean.TRUE.equals(product.getBComment())) {
                if ((product.getInternalName() != null && !product.getInternalName().isBlank())
                        || (product.getTotalPrice() != null && product.getTotalPrice() != 0.0)) {
                    comments.add(product);
                }
            } else if (Boolean.TRUE.equals(product.getBWorkHour())) {
                workHours.add(product);
            } else if (Boolean.TRUE.equals(product.getBTravel())) {
                travel.add(product);
            } else if ((product.getInternalName() != null && !product.getInternalName().isBlank())
                    || (product.getTotalPrice() != null && product.getTotalPrice() != 0.0)) {
                materials.add(product);
            }
        }

        // Zoals in de originele samengestelde proforma: materiaal op naam
        // sorteren, met de extra opties onderaan.
        Collections.reverse(materials);
        materials.sort((a, b) -> compareOnderdeel(a.getInternalName(), b.getInternalName()));
        materials.sort(Comparator.comparing(p ->
                p.getProductLevel1() != null
                        && "Extra".equalsIgnoreCase(p.getProductLevel1().getName())));

        // Identieke automatisch aangemaakte kosten samenvoegen zonder ze
        // opnieuw met hoeveelheid x prijs te berekenen. De totalPrice is
        // de SOM van de reeds per werkbon afgeronde regelbedragen.
        BigDecimal expectedTotal = sumRoundedBillableLines(invoice.getProductList());
        List<Product> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(materials);
        result.addAll(mergeGeneratedCostLines(workHours));
        result.addAll(mergeGeneratedCostLines(travel));

        for (int i = 0; i < result.size(); i++) {
            Product product = result.get(i);
            product.setDate(starterDate);
            product.setDateToShowOnInvoice(null);
            product.setShowDate(i == 0);
        }

        BigDecimal mergedTotal = sumRoundedBillableLines(result);
        if (expectedTotal.compareTo(mergedTotal) != 0) {
            throw new IllegalStateException(
                    "Samengestelde proforma wijkt af van proforma per dag: "
                            + expectedTotal + " vs " + mergedTotal);
        }

        invoice.setProductList(result);
        return invoice;
    }

    private BigDecimal sumRoundedBillableLines(List<Product> products) {
        return products.stream()
                .filter(Objects::nonNull)
                .filter(p -> !Boolean.TRUE.equals(p.getMergedProduct()))
                .map(Product::getTotalPrice)
                .filter(Objects::nonNull)
                .map(price -> BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Voegt enkel kostenregels samen met exact hetzelfde artikel, omschrijving,
     * factuurvlaggen, btw en eenheidsprijs. De netto bedragen worden op
     * regelniveau afgerond vóór het optellen, zoals bij de dagproforma.
     * Regels met 0 hoeveelheid of zonder artikelcode blijven afzonderlijk.
     */
    private List<Product> mergeGeneratedCostLines(List<Product> products) {
        List<Product> result = new ArrayList<>();
        Map<String, Product> merged = new LinkedHashMap<>();

        for (Product product : products) {
            Double amount = product.getSelectedAmount();
            Double total = product.getTotalPrice();
            String code = product.getProductCode();
            if (amount == null || amount <= 0 || !Double.isFinite(amount)
                    || total == null || !Double.isFinite(total)
                    || code == null || code.isBlank()
                    || Boolean.TRUE.equals(product.getMergedProduct())
                    || Boolean.TRUE.equals(product.getMergedInvisibleProduct())
                    || Boolean.TRUE.equals(product.getBAttachement())) {
                result.add(product);
                continue;
            }

            // Werkuren en verplaatsingen kunnen verschillende tarieven hebben.
            // Daarom alleen aggregeren bij dezelfde berekende eenheidsprijs.
            BigDecimal unitPrice = BigDecimal.valueOf(total)
                    .divide(BigDecimal.valueOf(amount), 8, RoundingMode.HALF_UP);
            String key = code + "\u0001" + Objects.toString(product.getInternalName(), "")
                    + "\u0001" + Objects.toString(product.getVat(), "")
                    + "\u0001" + Boolean.TRUE.equals(product.getBWorkHour())
                    + "\u0001" + Boolean.TRUE.equals(product.getBTravel())
                    + "\u0001" + unitPrice.toPlainString();

            Product previous = merged.get(key);
            if (previous == null) {
                Product copy = copyProductForProforma(product);
                copy.setTotalPrice(roundInvoiceLine(total));
                merged.put(key, copy);
                result.add(copy);
            } else {
                previous.setSelectedAmount(previous.getSelectedAmount() + amount);
                previous.setTotalPrice(BigDecimal.valueOf(previous.getTotalPrice())
                        .add(BigDecimal.valueOf(roundInvoiceLine(total))).doubleValue());
            }
        }
        return result;
    }

    private double roundInvoiceLine(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Product copyProductForProforma(Product original) {
        Product copy = new Product();
        BeanUtils.copyProperties(original, copy);
        return copy;
    }

    /**
     * Zorg dat bij een industrieklant ook de eenheidsprijs op de factuur
     * correct terugvalt op de agroprijs. Bestaande totalPrice van handmatig
     * gekozen materialen wordt bewust niet opnieuw berekend.
     */
    private void applyIndustryPriceFallback(List<Product> products, Customer customer) {
        if (customer == null || !Boolean.TRUE.equals(customer.getBIndustry())) {
            return;
        }
        for (Product product : products) {
            if (product == null) {
                continue;
            }
            Double industryPrice = product.getSellPriceIndustry();
            Double agroPrice = product.getSellPrice();
            if ((industryPrice == null || industryPrice == 0.0) && agroPrice != null) {
                product.setSellPriceIndustry(agroPrice);
            }
        }
    }

    /** Industrieprijs gebruiken wanneer ingevuld, anders agroprijs. */
    private double getCorrectSellPrice(Product product, Customer customer) {
        double agroPrice = product.getSellPrice() == null ? 0.0 : product.getSellPrice();
        double industryPrice = product.getSellPriceIndustry() == null
                ? 0.0 : product.getSellPriceIndustry();
        if (customer != null && Boolean.TRUE.equals(customer.getBIndustry())
                && industryPrice > 0.0) {
            return industryPrice;
        }
        return agroPrice;
    }

    public Invoice getnerateInvoicePerDay(Set<WorkOrder> workOrderSet) {

        List<WorkOrder> sortedWorkOrderList = new ArrayList<>(workOrderSet);
        sortedWorkOrderList.sort(Comparator.comparing(WorkOrder::getWorkDateTime));

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(getNewProFormaInvoiceNumber());
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setExpiryDate(LocalDate.now().plusDays(14));
        invoice.setBFinalInvoice(false);

        Address workAddress = sortedWorkOrderList.stream().findFirst().get().getWorkAddress();
        invoice.setWorkAddress(workAddress);
        Optional<List<Customer>> optCustomer = customerService.getCustomerByWorkAddress(workAddress);
        if(optCustomer.isEmpty()){
            noCustomerDialog.open();
            return null;
        }
        else{
            if(optCustomer.get().size() >= 2){
                Notification.show("Er zijn meerdere klanten met hetzelfde Werkadres");
            }
            invoice.setCustomer(optCustomer.get().get(0));
        }

        if (optCustomer.get().size() >= 2) {
            Notification.show("Er zijn meerdere klanten met hetzelfde Werkadres");
        }
        invoice.setCustomer(optCustomer.get().get(0));

        List<String> allFotoIds = sortedWorkOrderList.stream()
                .map(WorkOrder::getImageList)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        invoice.setImageList(allFotoIds);

        invoice.setWorkOrderList(new LinkedHashSet<>(sortedWorkOrderList));

        List<Product> allProducts = new ArrayList<>();

        for (WorkOrder workOrder : sortedWorkOrderList) {
            int workOrderStartIndex = allProducts.size();

            //generate empty line
//                Product emptyLine = new Product();
//                emptyLine.setDate(workOrder.getWorkDateTime().toLocalDate());
//                emptyLine.setTeamNumber(0);
//                emptyLine.setBComment(true);
//                allProducts.add(emptyLine);

            //generate Title per day
            //place title first


            try {

                String tile = workOrder.getWorkOrderHeaderList().stream()
                        .map(WorkOrderHeader::getTitle)
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .distinct()
                        .collect(Collectors.joining(System.lineSeparator()));

                if (tile != null && !tile.isBlank()) {

                    List<String> commentRowList = splitText(tile, 90);

                    for (String row : commentRowList) {
                        Product newProduct = new Product();
                        newProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        newProduct.setInternalName(row);
                        newProduct.setTeamNumber(0);
                        newProduct.setBComment(true);
                        allProducts.add(newProduct);
                    }
                }
            } catch (Exception e) {
                Notification.show("De starter bevat geen commentaar voor op de proforma!");
            }

            //generate Comments/Products per day
            //place comment first


            try {

                String comment = workOrder.getWorkOrderHeaderList().stream()
                        .map(WorkOrderHeader::getDescription)
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .distinct()
                        .collect(Collectors.joining(System.lineSeparator()));

                if (comment != null && !comment.isBlank()) {

                    List<String> commentRowList = splitText(comment, 90);

                    for (String row : commentRowList) {
                        Product newProduct = new Product();
                        newProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        newProduct.setInternalName(row);
                        newProduct.setTeamNumber(0);
                        newProduct.setBComment(true);
                        allProducts.add(newProduct);
                    }
                }
            } catch (Exception e) {
                Notification.show("De starter bevat geen commentaar voor op de proforma!");
            }

            //retrieve selected Products
            List<Product> selectedProducts = workOrder.getProductList().stream()
                    .filter(Objects::nonNull)
                    .map(this::copyProductForProforma)
                    .filter((product -> (product.getInternalName() != null) && (product.getInternalName().length() > 0)))
                    .collect(Collectors.toList());

            Collections.reverse(selectedProducts);

            //add date of looped Workorder to the Products so we can generate right attachements!
            selectedProducts.stream().forEach(product -> product.setDate(workOrder.getWorkDateTime().toLocalDate()));

            //sort selected Products
            Comparator<Product> productComparator = (o1, o2) -> compareOnderdeel(o1.getInternalName(), o2.getInternalName());
            selectedProducts.sort(productComparator);

            //place all options at the bottom of the list
            selectedProducts.sort(Comparator.comparing(
                    p -> (p.getProductLevel1() != null) && ("Extra".equalsIgnoreCase(p.getProductLevel1().getName()))
            ));

            //add sorted Products to list
            allProducts.addAll(selectedProducts);


            Double generalHoursOnTheMove = 0.0;
            Double programHoursOnTheMove = 0.0;
            Double centrifugeHoursOnTheMove = 0.0;

            Double generalHoursLocal = 0.0;
            Double programHoursLocal = 0.0;
            Double centrifugeHoursLocal = 0.0;

            int i = 0;
            for (WorkOrderHeader workOrderHeader : workOrder.getWorkOrderHeaderList()) {

                int numberOfTechnicians = 0;

                if ((workOrder.getWorkOrderHeaderList() != null) && (workOrder.getWorkOrderHeaderList().size() > 0) && (workOrderHeader.getWorkOrderTimeList() != null)) {
                    if (i == 0) {
                        numberOfTechnicians = numberOfTechnicians + 1 + workOrder.getExtraEmployeesTeam1().size();
                    }
                    if (i == 1) {
                        numberOfTechnicians = numberOfTechnicians + 1 + workOrder.getExtraEmployeesTeam2().size();
                    }
                    if (i == 2) {
                        numberOfTechnicians = numberOfTechnicians + 1 + workOrder.getExtraEmployeesTeam3().size();
                    }
                    if (i == 3) {
                        numberOfTechnicians = numberOfTechnicians + 1 + workOrder.getExtraEmployeesTeam4().size();
                    }
                    if ((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE)) && (workOrderHeader.getWorkType() == WorkType.GENERAL)) {
                        for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                            if((workOrderTime.getTimeUp() != null) && (workOrderTime.getTimeDown() != null)){
                                generalHoursOnTheMove = generalHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                    }
                    if ((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE) && (workOrderHeader.getWorkType()) == WorkType.CENTRIFUGE)) {
                        for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                            if((workOrderTime.getTimeUp() != null) && (workOrderTime.getTimeDown() != null)) {
                                centrifugeHoursOnTheMove = centrifugeHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes()) - workOrderTime.getPauze()) / 60.0);
                            }
                        }
                    }
                    if ((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE) && (workOrderHeader.getWorkType()) == WorkType.PROGRAMMATIC)) {
                        for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                            if((workOrderTime.getTimeUp() != null) && (workOrderTime.getTimeDown() != null)){
                                programHoursOnTheMove = programHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                    }
                    if ((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE)) && (workOrderHeader.getWorkType() == WorkType.GENERAL)) {
                        for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                            if((workOrderTime.getTimeStart() != null) && (workOrderTime.getTimeStop() != null)){
                                generalHoursLocal = generalHoursLocal + (numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze()) / 60.0));
                            }
                        }
                    }
                    if ((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE) && (workOrderHeader.getWorkType()) == WorkType.CENTRIFUGE)) {
                        for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                            if((workOrderTime.getTimeStart() != null) && (workOrderTime.getTimeStop() != null)){
                                centrifugeHoursLocal = centrifugeHoursLocal + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                    }
                    if ((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE) && (workOrderHeader.getWorkType()) == WorkType.PROGRAMMATIC)) {
                        for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                            if((workOrderTime.getTimeStart() != null) && (workOrderTime.getTimeStop() != null)){
                                programHoursLocal = programHoursLocal + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                    }
                    if((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE) && (workOrderHeader.getWorkType()) == WorkType.PICKUP)){
                        for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                            if(workOrderTime.getTimeStart() != null && workOrderTime.getTimeStop() != null){
                                programHoursLocal = programHoursLocal + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze())/60.0);
                            }
                        }
                    }
                    i++;
                }
            }

            if (optCustomer.get().getFirst().getBIndustry() == true) {

                if (generalHoursLocal > 0) {
                    Product workHourRegularLocalIndustrieProduct = copyProductForProforma(productService.getWorkhourForRegularLocal().get());
                    workHourRegularLocalIndustrieProduct.setSelectedAmount(Double.valueOf(generalHoursLocal));
                    workHourRegularLocalIndustrieProduct.setTotalPrice(Double.valueOf(generalHoursLocal) * getCorrectSellPrice(workHourRegularLocalIndustrieProduct, invoice.getCustomer()));
                    workHourRegularLocalIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourRegularLocalIndustrieProduct.setBWorkHour(true);
                    workHourRegularLocalIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularLocalIndustrieProduct);
                }

                if (centrifugeHoursLocal > 0) {
                    Product workHourCentrifugeLocalIndustrieProduct = copyProductForProforma(productService.getWorkhourForCentrifugeLocal().get());
                    workHourCentrifugeLocalIndustrieProduct.setSelectedAmount(Double.valueOf(centrifugeHoursLocal));
                    workHourCentrifugeLocalIndustrieProduct.setTotalPrice(Double.valueOf(centrifugeHoursLocal) * getCorrectSellPrice(workHourCentrifugeLocalIndustrieProduct, invoice.getCustomer()));
                    workHourCentrifugeLocalIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourCentrifugeLocalIndustrieProduct.setBWorkHour(true);
                    workHourCentrifugeLocalIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeLocalIndustrieProduct);
                }

                if (programHoursLocal > 0) {
                    Product workHourProgramLocalIndustrieProduct = copyProductForProforma(productService.getWorkhourForProgammationLocal().get());
                    workHourProgramLocalIndustrieProduct.setSelectedAmount(Double.valueOf(programHoursLocal));
                    workHourProgramLocalIndustrieProduct.setTotalPrice(Double.valueOf(programHoursLocal) * getCorrectSellPrice(workHourProgramLocalIndustrieProduct, invoice.getCustomer()));
                    workHourProgramLocalIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourProgramLocalIndustrieProduct.setBWorkHour(true);
                    workHourProgramLocalIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramLocalIndustrieProduct);
                }

                if (generalHoursOnTheMove > 0) {
                    Product workHourRegularOnTheMoveIndustrieProduct = copyProductForProforma(productService.getWorkhourForRegularOnTheMove().get());
                    workHourRegularOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(generalHoursOnTheMove));
                    workHourRegularOnTheMoveIndustrieProduct.setTotalPrice(Double.valueOf(generalHoursOnTheMove) * getCorrectSellPrice(workHourRegularOnTheMoveIndustrieProduct, invoice.getCustomer()));
                    workHourRegularOnTheMoveIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourRegularOnTheMoveIndustrieProduct.setBWorkHour(true);
                    workHourRegularOnTheMoveIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularOnTheMoveIndustrieProduct);
                }

                if (centrifugeHoursOnTheMove > 0) {
                    Product workHourCentrifugeOnTheMoveIndustrieProduct = copyProductForProforma(productService.getWorkhourForCentrifugeOnTheMove().get());
                    workHourCentrifugeOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(centrifugeHoursOnTheMove));
                    workHourCentrifugeOnTheMoveIndustrieProduct.setTotalPrice(Double.valueOf(centrifugeHoursOnTheMove) * getCorrectSellPrice(workHourCentrifugeOnTheMoveIndustrieProduct, invoice.getCustomer()));
                    workHourCentrifugeOnTheMoveIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourCentrifugeOnTheMoveIndustrieProduct.setBWorkHour(true);
                    workHourCentrifugeOnTheMoveIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeOnTheMoveIndustrieProduct);
                }

                if (programHoursOnTheMove > 0) {
                    Product workHourProgramOnTheMoveIndustrieProduct = copyProductForProforma(productService.getWorkhourForProgammationOnTheMove().get());
                    workHourProgramOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(programHoursOnTheMove));
                    workHourProgramOnTheMoveIndustrieProduct.setTotalPrice(Double.valueOf(programHoursOnTheMove) * getCorrectSellPrice(workHourProgramOnTheMoveIndustrieProduct, invoice.getCustomer()));
                    workHourProgramOnTheMoveIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourProgramOnTheMoveIndustrieProduct.setBWorkHour(true);
                    workHourProgramOnTheMoveIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramOnTheMoveIndustrieProduct);
                }

            } else {
                if (generalHoursLocal > 0) {
                    Product workHourRegularLocalAgroProduct = copyProductForProforma(productService.getWorkhourForRegularLocal().get());
                    workHourRegularLocalAgroProduct.setSelectedAmount(Double.valueOf(generalHoursLocal));
                    workHourRegularLocalAgroProduct.setTotalPrice(Double.valueOf(generalHoursLocal) * workHourRegularLocalAgroProduct.getSellPrice());
                    workHourRegularLocalAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourRegularLocalAgroProduct.setBWorkHour(true);
                    workHourRegularLocalAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularLocalAgroProduct);
                }

                if (centrifugeHoursLocal > 0) {
                    Product workHourCentrifugeLocalAgroProduct = copyProductForProforma(productService.getWorkhourForCentrifugeLocal().get());
                    workHourCentrifugeLocalAgroProduct.setSelectedAmount(Double.valueOf(centrifugeHoursLocal));
                    workHourCentrifugeLocalAgroProduct.setTotalPrice(Double.valueOf(centrifugeHoursLocal) * workHourCentrifugeLocalAgroProduct.getSellPrice());
                    workHourCentrifugeLocalAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourCentrifugeLocalAgroProduct.setBWorkHour(true);
                    workHourCentrifugeLocalAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeLocalAgroProduct);
                }

                if (programHoursLocal > 0) {
                    Product workHourProgramLocalAgroProduct = copyProductForProforma(productService.getWorkhourForProgammationLocal().get());
                    workHourProgramLocalAgroProduct.setSelectedAmount(Double.valueOf(programHoursLocal));
                    workHourProgramLocalAgroProduct.setTotalPrice(Double.valueOf(programHoursLocal) * workHourProgramLocalAgroProduct.getSellPrice());
                    workHourProgramLocalAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourProgramLocalAgroProduct.setBWorkHour(true);
                    workHourProgramLocalAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramLocalAgroProduct);
                }
                if (generalHoursOnTheMove > 0) {
                    Product workHourRegularOnTheMoveAgroProduct = copyProductForProforma(productService.getWorkhourForRegularOnTheMove().get());
                    workHourRegularOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(generalHoursOnTheMove));
                    workHourRegularOnTheMoveAgroProduct.setTotalPrice(Double.valueOf(generalHoursOnTheMove) * workHourRegularOnTheMoveAgroProduct.getSellPrice());
                    workHourRegularOnTheMoveAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourRegularOnTheMoveAgroProduct.setBWorkHour(true);
                    workHourRegularOnTheMoveAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularOnTheMoveAgroProduct);
                }

                if (centrifugeHoursOnTheMove > 0) {
                    Product workHourCentrifugeOnTheMoveAgroProduct = copyProductForProforma(productService.getWorkhourForCentrifugeOnTheMove().get());
                    workHourCentrifugeOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(centrifugeHoursOnTheMove));
                    workHourCentrifugeOnTheMoveAgroProduct.setTotalPrice(Double.valueOf(centrifugeHoursOnTheMove) * workHourCentrifugeOnTheMoveAgroProduct.getSellPrice());
                    workHourCentrifugeOnTheMoveAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourCentrifugeOnTheMoveAgroProduct.setBWorkHour(true);
                    workHourCentrifugeOnTheMoveAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeOnTheMoveAgroProduct);
                }

                if (programHoursOnTheMove > 0) {
                    Product workHourProgramOnTheMoveAgroProduct = copyProductForProforma(productService.getWorkhourForProgammationOnTheMove().get());
                    workHourProgramOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(programHoursOnTheMove));
                    workHourProgramOnTheMoveAgroProduct.setTotalPrice(Double.valueOf(programHoursOnTheMove) * workHourProgramOnTheMoveAgroProduct.getSellPrice());
                    workHourProgramOnTheMoveAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                    workHourProgramOnTheMoveAgroProduct.setBWorkHour(true);
                    workHourProgramOnTheMoveAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramOnTheMoveAgroProduct);
                }
            }

            // add movement to Proforma (ook 0 km bij ON_THE_MOVE)
            Double amountKmRegular = 0.0;
            Integer amountRidesRegular = 0;
            Double amountKmTrailer = 0.0;
            Integer amountRidesTrailer = 0;
            Double amountKmCrane = 0.0;
            Integer amountRidesCrane = 0;
            Double amountHoursCraneRegular = 0.0;
            Double amountHoursCraneIntens = 0.0;
            Integer amountForfait = 0;

            boolean hasRegularTravel = false;
            boolean hasTrailerTravel = false;
            boolean hasCraneTravel = false;
            boolean hasOnTheMoveWorkOrder = workOrder.getWorkLocation() == WorkLocation.ON_THE_MOVE;

            if (workOrder.getWorkOrderHeaderList() != null) {
                for (WorkOrderHeader workOrderHeader : workOrder.getWorkOrderHeaderList()) {
                    if (workOrderHeader == null) {
                        continue;
                    }
                    if (workOrder.getWorkLocation() == WorkLocation.ON_THE_MOVE) {
                        double distance = getValidDistance(workOrder.getWorkAddress());
                        Fleet fleet = workOrderHeader.getFleet();
                        if (fleet == Fleet.VAN || fleet == Fleet.ATEGO) {
                            amountKmRegular += distance;
                            amountRidesRegular++;
                            hasRegularTravel = true;
                        } else if (fleet == Fleet.TRUCK_TRAILER) {
                            amountKmTrailer += distance;
                            amountRidesTrailer++;
                            hasTrailerTravel = true;
                        } else if (fleet == Fleet.TRUCK_CRANE) {
                            amountKmCrane += distance;
                            amountRidesCrane++;
                            hasCraneTravel = true;
                            if (workOrderHeader.getFleetWorkType() == FleetWorkType.DELIVERY) {
                                amountForfait++;
                            }
                        }
                    }
                    if (workOrderHeader.getFleetHours() != null &&
                            Double.isFinite(workOrderHeader.getFleetHours()) &&
                            workOrderHeader.getFleetHours() >= 0.0) {
                        if (workOrderHeader.getFleetWorkType() == FleetWorkType.REGULAR) {
                            amountHoursCraneRegular += workOrderHeader.getFleetHours();
                        }
                        if (workOrderHeader.getFleetWorkType() == FleetWorkType.INTENS) {
                            amountHoursCraneIntens += workOrderHeader.getFleetHours();
                        }
                    }
                }
            }

            if (optCustomer.get().getFirst().getBIndustry() == true) {
                if (hasRegularTravel || (hasOnTheMoveWorkOrder && !hasTrailerTravel && !hasCraneTravel)) {
                    Product regularKm = copyProductForProforma(productService.getRegularKm().get());
                    regularKm.setSelectedAmount(amountKmRegular);
                    regularKm.setTotalPrice(amountKmRegular * getCorrectSellPrice(regularKm, invoice.getCustomer()));
                    regularKm.setTeamNumber(0);
                    markDistanceForInvoiceReview(regularKm, amountKmRegular);
                    regularKm.setBTravel(true);
                    regularKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                    if(amountRidesRegular > 1) {
                        regularKm.setInternalName(regularKm.getInternalName() + "("+ amountRidesRegular + " x heen en terug)");
                    }
                    allProducts.add(regularKm);
                }
                if (hasTrailerTravel) {
                    if (amountKmTrailer > 0.0) {
                        Product forfaitTrailer = copyProductForProforma(productService.getWorkHoursTrailerForfait().get());
                        forfaitTrailer.setSelectedAmount(1.0);
                        forfaitTrailer.setTotalPrice(getCorrectSellPrice(forfaitTrailer, invoice.getCustomer()));
                        forfaitTrailer.setTeamNumber(0);
                        forfaitTrailer.setBTravel(true);
                        forfaitTrailer.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(forfaitTrailer);
                    }

                    Product trailerKm = copyProductForProforma(productService.getRegularTrailer().get());
                    trailerKm.setSelectedAmount(amountKmTrailer);
                    trailerKm.setTotalPrice(amountKmTrailer * getCorrectSellPrice(trailerKm, invoice.getCustomer()));
                    trailerKm.setTeamNumber(0);
                    markDistanceForInvoiceReview(trailerKm, amountKmTrailer);
                    trailerKm.setBTravel(true);
                    trailerKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                    if(amountRidesTrailer > 1) {
                        trailerKm.setInternalName(trailerKm.getInternalName() + "("+ amountRidesTrailer + " x heen en terug)");
                    }
                    allProducts.add(trailerKm);
                }
                if (hasCraneTravel) {
                    Product craneKm = copyProductForProforma(productService.getRegularCrane().get());
                    craneKm.setSelectedAmount(amountKmCrane);
                    craneKm.setTotalPrice(amountKmCrane * getCorrectSellPrice(craneKm, invoice.getCustomer()));
                    craneKm.setTeamNumber(0);
                    markDistanceForInvoiceReview(craneKm, amountKmCrane);
                    craneKm.setBTravel(true);
                    craneKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                    if(amountRidesCrane > 1) {
                        craneKm.setInternalName(craneKm.getInternalName() + "("+ amountRidesCrane + " x heen en terug)");
                    }
                    allProducts.add(craneKm);
                }
                if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular >= 3.0)) {
                    Product hoursCraneRegularIndustry = copyProductForProforma(productService.getWorkHoursCraneRegular().get());
                    hoursCraneRegularIndustry.setSelectedAmount(amountHoursCraneRegular);
                    hoursCraneRegularIndustry.setTotalPrice(amountHoursCraneRegular * getCorrectSellPrice(hoursCraneRegularIndustry, invoice.getCustomer()));
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneRegularIndustry);
                }
                else if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular < 3.0)) {
                    Product hoursCraneRegularIndustry = copyProductForProforma(productService.getWorkHoursCraneRegular().get());
                    hoursCraneRegularIndustry.setSelectedAmount(3.0);
                    hoursCraneRegularIndustry.setTotalPrice(3.0 * getCorrectSellPrice(hoursCraneRegularIndustry, invoice.getCustomer()));
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneRegularIndustry);
                }
                if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens >= 4.0)) {
                    Product hoursCraneIntenseIndustry = copyProductForProforma(productService.getWorkHoursCraneIntense().get());
                    hoursCraneIntenseIndustry.setSelectedAmount(amountHoursCraneIntens);
                    hoursCraneIntenseIndustry.setTotalPrice(amountHoursCraneIntens * getCorrectSellPrice(hoursCraneIntenseIndustry, invoice.getCustomer()));
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens < 4.0)) {
                    Product hoursCraneIntenseIndustry = copyProductForProforma(productService.getWorkHoursCraneIntense().get());
                    hoursCraneIntenseIndustry.setSelectedAmount(4.0);
                    hoursCraneIntenseIndustry.setTotalPrice(4.0 * getCorrectSellPrice(hoursCraneIntenseIndustry, invoice.getCustomer()));
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                if (amountForfait > 0) {
                    Product hoursCraneForfaitIndustry = copyProductForProforma(productService.getWorkHoursCraneForfait().get());
                    hoursCraneForfaitIndustry.setSelectedAmount(Double.valueOf(amountForfait));
                    hoursCraneForfaitIndustry.setTotalPrice(amountForfait * getCorrectSellPrice(hoursCraneForfaitIndustry, invoice.getCustomer()));
                    hoursCraneForfaitIndustry.setTeamNumber(0);
                    hoursCraneForfaitIndustry.setBTravel(true);
                    hoursCraneForfaitIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneForfaitIndustry);
                }
            } else {
                if (hasRegularTravel || (hasOnTheMoveWorkOrder && !hasTrailerTravel && !hasCraneTravel)) {
                    Product regularKm = copyProductForProforma(productService.getRegularKm().get());
                    regularKm.setSelectedAmount(amountKmRegular);
                    regularKm.setTotalPrice(amountKmRegular * regularKm.getSellPrice());
                    regularKm.setTeamNumber(0);
                    markDistanceForInvoiceReview(regularKm, amountKmRegular);
                    regularKm.setBTravel(true);
                    regularKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                    if(amountRidesRegular > 1) {
                        regularKm.setInternalName(regularKm.getInternalName() + "("+ amountRidesRegular + " x heen en terug)");
                    }
                    allProducts.add(regularKm);
                }
                if (hasTrailerTravel) {

                    if (amountKmTrailer > 0.0) {

                        Product forfaitTrailer = copyProductForProforma(productService.getWorkHoursTrailerForfait().get());
                        forfaitTrailer.setSelectedAmount(1.0);
                        forfaitTrailer.setTotalPrice(forfaitTrailer.getSellPrice());
                        forfaitTrailer.setTeamNumber(0);
                        forfaitTrailer.setBTravel(true);
                        forfaitTrailer.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(forfaitTrailer);

                    }


                    Product trailerKm = copyProductForProforma(productService.getRegularTrailer().get());
                    trailerKm.setSelectedAmount(amountKmTrailer);
                    trailerKm.setTotalPrice(amountKmTrailer * trailerKm.getSellPrice());
                    trailerKm.setTeamNumber(0);
                    markDistanceForInvoiceReview(trailerKm, amountKmTrailer);
                    trailerKm.setBTravel(true);
                    trailerKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                    if(amountRidesTrailer > 1) {
                        trailerKm.setInternalName(trailerKm.getInternalName() + "("+ amountRidesTrailer + " x heen en terug)");
                    }
                    allProducts.add(trailerKm);
                }
                if (hasCraneTravel) {
                    Product craneKm = copyProductForProforma(productService.getRegularCrane().get());
                    craneKm.setSelectedAmount(amountKmCrane);
                    craneKm.setTotalPrice(amountKmCrane * craneKm.getSellPrice());
                    craneKm.setTeamNumber(0);
                    markDistanceForInvoiceReview(craneKm, amountKmCrane);
                    craneKm.setBTravel(true);
                    craneKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                    if(amountRidesCrane > 1) {
                        craneKm.setInternalName(craneKm.getInternalName() + "("+ amountRidesCrane + " x heen en terug)");
                    }
                    allProducts.add(craneKm);
                }
                if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular >= 3.0)) {
                    Product hoursCraneRegularIndustry = copyProductForProforma(productService.getWorkHoursCraneRegular().get());
                    hoursCraneRegularIndustry.setSelectedAmount(amountHoursCraneRegular);
                    hoursCraneRegularIndustry.setTotalPrice(amountHoursCraneRegular * hoursCraneRegularIndustry.getSellPrice());
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneRegularIndustry);
                }
                else if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular < 3.0)) {
                    Product hoursCraneRegularIndustry = copyProductForProforma(productService.getWorkHoursCraneRegular().get());
                    hoursCraneRegularIndustry.setSelectedAmount(3.0);
                    hoursCraneRegularIndustry.setTotalPrice(3.0 * hoursCraneRegularIndustry.getSellPrice());
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneRegularIndustry);
                }
                if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens >= 4.0)) {
                    Product hoursCraneIntenseIndustry = copyProductForProforma(productService.getWorkHoursCraneIntense().get());
                    hoursCraneIntenseIndustry.setSelectedAmount(amountHoursCraneIntens);
                    hoursCraneIntenseIndustry.setTotalPrice(amountHoursCraneIntens * hoursCraneIntenseIndustry.getSellPrice());
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens < 4.0)) {
                    Product hoursCraneIntenseIndustry = copyProductForProforma(productService.getWorkHoursCraneIntense().get());
                    hoursCraneIntenseIndustry.setSelectedAmount(4.0);
                    hoursCraneIntenseIndustry.setTotalPrice(4.0 * hoursCraneIntenseIndustry.getSellPrice());
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                if (amountForfait > 0) {
                    Product hoursCraneForfaitIndustry = copyProductForProforma(productService.getWorkHoursCraneForfait().get());
                    hoursCraneForfaitIndustry.setSelectedAmount(Double.valueOf(amountForfait));
                    hoursCraneForfaitIndustry.setTotalPrice(amountForfait * hoursCraneForfaitIndustry.getSellPrice());
                    hoursCraneForfaitIndustry.setTeamNumber(0);
                    hoursCraneForfaitIndustry.setBTravel(true);
                    hoursCraneForfaitIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                    allProducts.add(hoursCraneForfaitIndustry);
                }
            }

            //add roadTax / Tunneltax to workorder

            Double totalTax = 0.0;
            Double totalTunnelTax = 0.0;

            for (WorkOrderHeader workOrderHeader : workOrder.getWorkOrderHeaderList()) {
                Double roadTax = 0.0;
                Double tunnelTax = 0.0;
                if ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.VAN))) {
                    if (workOrderHeader.getRoadTax() != null) {
                        roadTax = workOrderHeader.getRoadTax();
                    } else {
                        roadTax = 0.0;
                    }
                    if (workOrderHeader.getTunnelTax() != null) {
                        tunnelTax = workOrderHeader.getTunnelTax();
                    } else {
                        tunnelTax = 0.0;
                    }
                }
                if ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.ATEGO))) {

                    if (workOrderHeader.getRoadTax() != null) {
                        if ((workAddress.getRoadTaxAtego() != null) && (workAddress.getRoadTaxAtego() > workOrderHeader.getRoadTax())) {
                            roadTax = workAddress.getRoadTaxAtego();
                        } else {
                            roadTax = workOrderHeader.getRoadTax();
                        }
                    } else {
                        roadTax = Optional.ofNullable(workAddress.getRoadTaxAtego())
                                .orElse(0.0);
                    }
                    if (workOrderHeader.getTunnelTax() != null) {
                        tunnelTax = workOrderHeader.getTunnelTax();
                    } else {
                        tunnelTax = 0.0;
                    }
                }
                if ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_TRAILER))) {
                    if (workOrderHeader.getRoadTax() != null) {
                        if ((workAddress.getRoadTaxActros() != null) && (workAddress.getRoadTaxActros() > workOrderHeader.getRoadTax())) {
                            roadTax = workAddress.getRoadTaxActros();
                        } else {
                            roadTax = workOrderHeader.getRoadTax();
                        }
                    } else {
                        roadTax = Optional.ofNullable(workAddress.getRoadTaxActros())
                                .orElse(0.0);
                    }
                    if (workOrderHeader.getTunnelTax() != null) {
                        tunnelTax = workOrderHeader.getTunnelTax();
                    } else {
                        tunnelTax = 0.0;
                    }
                }
                if ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_CRANE))) {
                    if (workOrderHeader.getRoadTax() != null) {
                        if ((workAddress.getRoadTaxArocs() != null) && (workAddress.getRoadTaxArocs() > workOrderHeader.getRoadTax())) {
                            roadTax = workAddress.getRoadTaxArocs();
                        } else {
                            roadTax = workOrderHeader.getRoadTax();
                        }
                    } else {
                        roadTax = Optional.ofNullable(workAddress.getRoadTaxArocs())
                                .orElse(0.0);
                    }
                    if (workOrderHeader.getTunnelTax() != null) {
                        tunnelTax = workOrderHeader.getTunnelTax();
                    } else {
                        tunnelTax = 0.0;
                    }
                }
                totalTax += roadTax;
                totalTunnelTax += tunnelTax;
            }


            if (totalTax > 0.0) {
                Product roadTaxProduct = new Product();
                roadTaxProduct.setSelectedAmount(1.0);
                roadTaxProduct.setInternalName("Wegentaks");
                roadTaxProduct.setSellPrice(totalTax);
                roadTaxProduct.setTotalPrice(1.0 * totalTax);
                roadTaxProduct.setBTravel(true);
                roadTaxProduct.setVat(VAT.EENENTWINTIG);
                roadTaxProduct.setTeamNumber(0);
                roadTaxProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                allProducts.add(roadTaxProduct);
            }

            if (totalTunnelTax > 0.0) {
                Product tunnelTaxProduct = new Product();
                tunnelTaxProduct.setSelectedAmount(1.0);
                tunnelTaxProduct.setInternalName("tunneltaks");
                tunnelTaxProduct.setSellPrice(totalTunnelTax);
                tunnelTaxProduct.setTotalPrice(1.0 * totalTunnelTax);
                tunnelTaxProduct.setBTravel(true);
                tunnelTaxProduct.setVat(VAT.EENENTWINTIG);
                tunnelTaxProduct.setTeamNumber(0);
                tunnelTaxProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                allProducts.add(tunnelTaxProduct);
            }

            Product emptyProduct1 = new Product();
            emptyProduct1.setTeamNumber(0);
            emptyProduct1.setBComment(true);
            emptyProduct1.setInternalName("");
            emptyProduct1.setDate(workOrder.getWorkDateTime().toLocalDate());
            allProducts.add(emptyProduct1);

            // OPTIES / MACHINE-UREN: slechts eenmaal per werkbon aftrekken.
            // Nooit opnieuw op alle reeds verwerkte werkbonnen uitvoeren.
            Invoice workOrderInvoice = new Invoice();
            workOrderInvoice.setCustomer(invoice.getCustomer());
            workOrderInvoice.setProductList(
                    new ArrayList<>(allProducts.subList(workOrderStartIndex, allProducts.size())));
            checkIfToolsHoursAreSubtractedFromWorkOrder(workOrderInvoice);
        }

        // De eerste regel van elke datum markeren, na alle werkbonnen.
        allProducts.stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getDate() != null)
                .collect(Collectors.groupingBy(
                        Product::getDate, LinkedHashMap::new, Collectors.toList()))
                .forEach((date, products) -> {
                    products.get(0).setDateToShowOnInvoice(
                            date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                });

        applyIndustryPriceFallback(allProducts, invoice.getCustomer());
        invoice.setProductList(allProducts);
        return invoice;
    }

    public List<String> generateInvoicePDF(Invoice invoice){

        List<String>linkList = new ArrayList<>();

        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;

        baosAttachement = new ByteArrayOutputStream();

        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter COMPAREDFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        parameters.clear();

        try {
            if((invoice.getProjectWorkAddress() != null)){
                parameters.put("werfAdres", "Werfadres : " + "\n" +
                        invoice.getProjectWorkAddress().getAddressName() + "\n" +
                        invoice.getProjectWorkAddress().getStreet() + "\n" +
                        invoice.getProjectWorkAddress().getZip() + " " +
                        invoice.getProjectWorkAddress().getCity());
            }
            else{
                parameters.put("werfAdres", "Werfadres : " + "\n" +
                        invoice.getWorkAddress().getStreet() + "\n" +
                        invoice.getWorkAddress().getZip() + " " +
                        invoice.getWorkAddress().getCity());
            }
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige servicelocatie te zorgen aub");
        }

        try {
            Address invoiceAddress = invoice.getCustomer().getAddresses().stream().filter(x -> (x.getInvoiceAddress() != null)&&(x.getInvoiceAddress() == true)).findFirst().get();
            parameters.put("facturatieAdres", invoice.getCustomer().getName() + "\n" +
                    invoiceAddress.getStreet() + "\n" +
                    invoiceAddress.getZip() + " " +
                    invoiceAddress.getCity());
        }
        catch (Exception e){
            //get first Address in list
            Address invoiceAddress = invoice.getCustomer().getAddresses().stream().findFirst().get();
            parameters.put("facturatieAdres", invoice.getCustomer().getName() + "\n" +
                    invoiceAddress.getStreet() + "\n" +
                    invoiceAddress.getZip() + " " +
                    invoiceAddress.getCity());
        }

        try {
            parameters.put("btwNummer", vatFormat(invoice.getCustomer().getVatNumber()));
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        try {
            if((invoice.getBFinalInvoice() != null) && (invoice.getBFinalInvoice() == true)){
                parameters.put("factuurNummer", invoice.getFinalInvoiceNumber().toString());
            }
            else{
                parameters.put("factuurNummer", invoice.getInvoiceNumber().toString());
            }
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        try {
            parameters.put("datum", invoice.getInvoiceDate().format(FORMATTER));
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        try {
            parameters.put("vervalDatum", invoice.getExpiryDate().format(FORMATTER));
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        //only show dates at the beginning / rest of block set as null
        //round total amount
        LocalDate vorigeDatum = null;

        for (Product item : invoice.getProductList()) {
            double roundedTotalPrice = BigDecimal
                    .valueOf(item.getTotalPrice())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            item.setTotalPrice(roundedTotalPrice);

            if (item.getDate().equals(vorigeDatum)) {
                item.setShowDate(false);
            } else {
                vorigeDatum = item.getDate();
            }
        }

        List<Product> totalProductList = invoice.getProductList().stream()
                .filter(x -> !Boolean.TRUE.equals(x.getMergedInvisibleProduct()))
                .collect(Collectors.toList());

        List<Product> attachments = totalProductList.stream()
                .filter(product -> {
                    if((product.getBAttachement() == null) || (product.getBAttachement() == false)){
                        return false;
                    }
                    else{
                        return true;
                    }
                })
                .collect(Collectors.toList());


        List<Product> products = totalProductList.stream()
                .filter(product -> {
                    if((product.getBAttachement() == null) || (product.getBAttachement() == false)){
                        return true;
                    }
                    else{
                        return false;
                    }
                })
                .collect(Collectors.toList());


        // Generate pointer for every different attachment date

        if (!attachments.isEmpty()) {

            List<LocalDate> uniqueDates = attachments.stream()
                    .map(Product::getAttachementNumber)
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            for (LocalDate uniqueDate : uniqueDates) {

                Product pointerProduct = new Product();

                pointerProduct.setTeamNumber(0);
                pointerProduct.setSelectedAmount(1.0);

                double totalPrice = attachments.stream()
                        .filter(p -> uniqueDate.equals(p.getAttachementNumber()))
                        .map(Product::getTotalPrice)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .sum();

                pointerProduct.setTotalPrice(totalPrice);
                pointerProduct.setSellPrice(totalPrice);
                pointerProduct.setSellPriceIndustry(totalPrice);

                pointerProduct.setVat(attachments.stream()
                        .filter(p -> uniqueDate.equals(p.getAttachementNumber()))
                        .findFirst()
                        .map(Product::getVat)
                        .orElse(null));

                pointerProduct.setInternalName("materiaal (zie bijlage)");

                // Datum van de huidige bijlage
                String dateString = uniqueDate.format(COMPAREDFORMATTER);


                //    Zoek commentaar op DEZELFDE datum

                OptionalInt indexCommentOpt = IntStream.range(0, products.size())
                        .filter(i -> dateString.equals(
                                products.get(i).getDateToShowOnInvoice()))
                        .filter(i -> Boolean.TRUE.equals(
                                products.get(i).getBComment()))
                        .findFirst();


                // 4. Voeg verwijzing toe op correcte positie

                if (indexCommentOpt.isPresent()) {

                    // Begin van het commentaarblok van de juiste datum
                    int lastCommentIndex = indexCommentOpt.getAsInt();

                    // Doorloop uitsluitend de opeenvolgende commentaarlijnen
                    while (lastCommentIndex + 1 < products.size()) {

                        Product nextProduct = products.get(lastCommentIndex + 1);

                        // Stop zodra de volgende lijn geen commentaar meer is of een 0- artikel
                        boolean isComment = Boolean.TRUE.equals(nextProduct.getBComment());
                        //boolean isZeroPosition = "0".equals(nextProduct.getPositionNumber());

                        if (!isComment) {
                            break;
                        }

                        // Een nieuwe ingevulde factuurdatum betekent
                        // dat er een nieuw blok begint
                        String nextDate = nextProduct.getDateToShowOnInvoice();

                        if (nextDate != null && !nextDate.isBlank()) {
                            break;
                        }

                        lastCommentIndex++;
                    }

                    // Verwijzing na de laatste opeenvolgende commentaarlijn
                    products.add(lastCommentIndex + 1, pointerProduct);

                } else {

                    products.add(pointerProduct);
                }
            }
        }


        //if there is a PO nubmer add it to the end of the list
        if((invoice.getPoNumber() != null) && (invoice.getPoNumber().length() > 0)){
            Product emptyLine = new Product();
            emptyLine.setTeamNumber(0);
            emptyLine.setBComment(true);

            Product poNumber = new Product();
            poNumber.setTeamNumber(0);
            poNumber.setBComment(true);
            poNumber.setInternalName("Uw referentie : " + invoice.getPoNumber());

            products.add(emptyLine);
            products.add(poNumber);
        }

        Collections.reverse(products);
        ProductImplementation productImplementation = new ProductImplementation(products,invoice.getCustomer());

        parameters.put( "ItemDataSource", productImplementation);

        Double totalPriceInvoice = invoice.getProductList().stream()
                .filter(product -> ((product.getMergedProduct() == null) || (product.getMergedProduct() == false)) && (product.getTotalPrice() != null))
                .mapToDouble(Product::getTotalPrice)
                .sum();

        parameters.put("netto", totalPriceInvoice);

        if(invoice.getCustomer().getVatNumber().contains("BE")){
            double btwBedrag = invoice.getProductList().stream()
                    .filter(product -> ((product.getMergedProduct() == null) || (product.getMergedProduct() == false)) && (product.getTotalPrice() != null))
                    .mapToDouble(x -> (x.getTotalPrice() * x.getVat().getValue()) / 100)
                    .sum();

            btwBedrag = BigDecimal.valueOf(btwBedrag)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            parameters.put("btwBedrag", btwBedrag);
        }
        else{
            parameters.put("btwBedrag",0.0);
        }


        if((invoice.getBFinalInvoice() != null) && (invoice.getBFinalInvoice() == true) && (totalPriceInvoice != null) && (totalPriceInvoice >= 0)){
            try {
                jasperReport = JasperCompileManager.compileReport( invoiceResourceJRXML.getInputStream() );
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                Notification.show("Kan de JRXML template niet vinden op de server!");
            }
        }
        else if((invoice.getBFinalInvoice() != null) && (invoice.getBFinalInvoice() == true) && (totalPriceInvoice != null) && (totalPriceInvoice < 0)){
            try {
                jasperReport = JasperCompileManager.compileReport( creditNoteResourceJRXML.getInputStream() );
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                Notification.show("Kan de JRXML template niet vinden op de server!");
            }
        }
        else{
            try {
                jasperReport = JasperCompileManager.compileReport( proformaResourceJRXML.getInputStream() );
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                Notification.show("Kan de JRXML template niet vinden op de server!");
            }
        }

        try {
            jasperPrint  = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(  ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String formatted;
        String exportName;

        if((invoice.getBFinalInvoice() != null) && (invoice.getBFinalInvoice() == true)){
            formatted = String.valueOf(invoice.getFinalInvoiceNumber()).substring(0, 2) + "-" + String.valueOf(invoice.getFinalInvoiceNumber()).substring(2);
            exportName = rootFolder + ""+ formatted+".pdf";
        }
        else{
            formatted = String.valueOf(invoice.getInvoiceNumber()).substring(0, 2) + "-" + String.valueOf(invoice.getInvoiceNumber()).substring(2);
            exportName = rootFolder + ""+ formatted+".pdf";
        }

        try {
            invoiceBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            Files.write(Paths.get(exportName), invoiceBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }


        //now show it in a new tab in the browser
        linkList.add(exportName);
        pdfController.setPdfNaam(""+ formatted+".pdf");

        UI.getCurrent().getPage().open(
                "/pdf/invoice/" + invoice.getId(),
                "_blank"
        );

        // now generate the attachement
        if(attachments.size() > 0){

            List<LocalDate> uniqueDates = attachments.stream()
                    .map(Product::getAttachementNumber)
                    .distinct()
                    .collect(Collectors.toList());

            prints.clear();

            for(LocalDate date : uniqueDates){
                generateAttachement(date, invoice,attachments.stream().filter(product -> product.getAttachementNumber().equals(date)).collect(Collectors.toList()));
            }

            try {

                exporter.setExporterInput(SimpleExporterInput.getInstance(prints));



//                exporter.setExporterOutput(
//                        new SimpleOutputStreamExporterOutput(rootFolder + "all_attachments.pdf")
//                );
//
//                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baosAttachement));

                exporter.setExporterOutput(
                        new SimpleOutputStreamExporterOutput(baosAttachement)
                );

                exporter.exportReport();

                attachementBytes = baosAttachement.toByteArray();

                Files.write(
                        Paths.get(rootFolder + "all_attachments.pdf"),
                        attachementBytes
                );

                linkList.add(rootFolder + "all_attachments.pdf");

                SimplePdfExporterConfiguration configuration =
                        new SimplePdfExporterConfiguration();

                exporter.setConfiguration(configuration);

                exporter.exportReport();

                attachementBytes = baosAttachement.toByteArray();

            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //now show it in a new tab in the browser
            UI.getCurrent().getPage().open("/attachement", "_blank");
        }
        else{
            attachementBytes = null;
        }
        return linkList;
    }

    private void generateAttachement(LocalDate datum, Invoice invoice, List<Product> attachments) {
        JasperReport jasperReportAttachement = null;
        JasperPrint jasperPrintAttachement = null;

        try {
            jasperReportAttachement = JasperCompileManager.compileReport( attachementResourceJRXML.getInputStream() );
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            Notification.show("Kan de Attachement- JRXML template niet vinden op de server!");
        }

        parameters.clear();

        //TODO change invoice and finalInvoiceNumber
        try {
            if((invoice.getBFinalInvoice() != null) && (invoice.getBFinalInvoice() == true)){
                parameters.put("factuurNummer", invoice.getFinalInvoiceNumber().toString());
            }
            else{
                parameters.put("factuurNummer", invoice.getInvoiceNumber().toString());
            }
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        try {
            parameters.put("datum",datum.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) );
        }
        catch (Exception e){
            Notification.show("Gelieve een bijlagenaam in te geven aub");
        }

        Collections.reverse(attachments);
        for(Product product : attachments){
            double roundedTotalPrice = BigDecimal
                    .valueOf(product.getTotalPrice())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            product.setTotalPrice(roundedTotalPrice);
        }
        ProductImplementation productImplementation = new ProductImplementation(attachments,invoice.getCustomer());
        parameters.put( "ItemDataSource", productImplementation);

        try {
            jasperPrintAttachement  = JasperFillManager.fillReport(jasperReportAttachement, parameters, new JREmptyDataSource(  ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        prints.add(jasperPrintAttachement);
    }

    /**
     * Trek de bewerkingstijd slechts eenmaal af van de (eerste) werkurenregel
     * van de meegegeven werkbon. De oproep bij proformageneratie ontvangt
     * daarom enkel de producten van de betreffende werkbon.
     *
     * De hoeveelheden worden zoals in de bestaande logica afgetrokken; de
     * gebruikte OPAT-eenheden moeten overeenkomen met de WU-eenheden.
     */
    public void checkIfToolsHoursAreSubtractedFromWorkOrder(Invoice selectedInvoice) {
        if (selectedInvoice == null || selectedInvoice.getProductList() == null) {
            return;
        }

        Set<String> optionCodes = Set.of(
                "OPAT-laser", "OPAT-plooi", "OPAT-laser-plooi", "OPAT-dr-fr", "OPAT-cncsn");

        double hoursToSubtract = selectedInvoice.getProductList().stream()
                .filter(Objects::nonNull)
                .filter(p -> optionCodes.contains(p.getProductCode()))
                .map(Product::getSelectedAmount)
                .filter(Objects::nonNull)
                .filter(Double::isFinite)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (hoursToSubtract == 0.0) {
            return;
        }

        selectedInvoice.getProductList().stream()
                .filter(Objects::nonNull)
                .filter(p -> Boolean.TRUE.equals(p.getBWorkHour()))
                .filter(p -> p.getProductCode() != null && p.getProductCode().contains("WU"))
                .findFirst()
                .ifPresent(workHour -> {
                    double currentHours = workHour.getSelectedAmount() == null
                            ? 0.0 : workHour.getSelectedAmount();
                    double correctedHours = currentHours - hoursToSubtract;
                    workHour.setSelectedAmount(correctedHours);
                    workHour.setTotalPrice(correctedHours *
                            getCorrectSellPrice(workHour, selectedInvoice.getCustomer()));
                });
    }

    /**
     * Som van afgeronde artikelbedragen zonder de originele producten te
     * muteren. Ook de samengestelde proforma gebruikt exact deze grondslag.
     */
    public Optional<Double> calcTotalNetFromInvoice(Invoice invoice) {
        if (invoice == null || invoice.getProductList() == null) {
            return Optional.empty();
        }
        return Optional.of(sumRoundedBillableLines(invoice.getProductList()).doubleValue());
    }

    /** Btw op afgeronde artikelbedragen; rond de totale btw slechts eenmaal af. */
    public Optional<Double> calcTotalTaxFromInvoice(Invoice invoice) {
        if (invoice == null || invoice.getProductList() == null) {
            return Optional.empty();
        }
        if (invoice.getCustomer() == null || invoice.getCustomer().getVatNumber() == null
                || !invoice.getCustomer().getVatNumber().contains("BE")) {
            return Optional.of(0.0);
        }

        BigDecimal totalTax = invoice.getProductList().stream()
                .filter(Objects::nonNull)
                .filter(p -> !Boolean.TRUE.equals(p.getMergedProduct()))
                .filter(p -> p.getTotalPrice() != null && p.getVat() != null)
                .map(p -> BigDecimal.valueOf(roundInvoiceLine(p.getTotalPrice()))
                        .multiply(BigDecimal.valueOf(p.getVat().getValue()))
                        .movePointLeft(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return Optional.of(totalTax.doubleValue());
    }

    public Optional<Double> getTotalPayed(Invoice invoice) {
        if((invoice != null) && (invoice.getPaymentList() != null) && (invoice.getPaymentList().size() > 0)){
            Double totalPayedAmount = invoice.getPaymentList().stream().map(item -> item.getPaymentAmount()).reduce(0.0, Double::sum);
            return Optional.of(totalPayedAmount);
        }
        else{
            return Optional.of(0.0);
        }

    }


    public Optional<Double> getTotalPayedFromInvoiceList(List<Invoice> invoiceList) {
        Double totalPayed = 0.0;
        if ((invoiceList != null) && (invoiceList.size() > 0)) {
            for (Invoice invoice : invoiceList) {
                if ((invoice != null) && (invoice.getPaymentList() != null) && (invoice.getPaymentList().size() > 0)) {
                    Double payed = invoice.getPaymentList().stream().map(item -> item.getPaymentAmount()).reduce(0.0, Double::sum);
                    totalPayed = totalPayed + payed;
                }
            }
            return Optional.of(totalPayed);
        }
        return Optional.of(0.0);
    }

    public Optional<Double> getTotalToBePayedFromInvoiceList(List<Invoice> invoiceList) {
        Double toBePayed = 0.0;
        Double totalToBePayed = 0.0;
        if ((invoiceList != null) && (invoiceList.size() > 0)) {
            for (Invoice invoice : invoiceList) {
                if ((invoice != null) && (invoice.getPaymentList() != null) && (invoice.getPaymentList().size() > 0)) {
                    Double payed = invoice.getPaymentList().stream().map(item -> item.getPaymentAmount()).reduce(0.0, Double::sum);
                    totalToBePayed +=
                            calcTotalNetFromInvoice(invoice).orElse(0.0)
                                    + calcTotalTaxFromInvoice(invoice).orElse(0.0)
                                    - payed;
                }
                else{
                    totalToBePayed +=
                            calcTotalNetFromInvoice(invoice).orElse(0.0)
                                    + calcTotalTaxFromInvoice(invoice).orElse(0.0);
                }
            }
            return Optional.of(totalToBePayed);
        }
        return Optional.of(0.0);
    }

    public String generateInvoicePDFAndSendToBillit(Invoice item) {
        //generate PDF
        this.generateInvoicePDF(item);
        //Encode PDF Invoice
        base64PdfInvoice = Base64.getEncoder().encodeToString(invoiceBytes);
        //Encode Attachement
        if(attachementBytes != null){
            base64PdfAttachement = Base64.getEncoder().encodeToString(attachementBytes);
        }
        else{
            base64PdfAttachement = null;
        }
        try {
            String json = mapper.writeValueAsString(getInvoiceJson(item));
            response = billitController.sendOrder(json,base64PdfInvoice);
        } catch (Exception e) {
            Notification notification = new Notification();
            notification.setText("Factuur kon niet naar Billit gestuurd worden: " + e.getMessage());
            notification.setThemeName("error");
            notification.open();
            setFaultStatusForInvoice(item);
            return "";
        }
        try{
            int billitNumber = Integer.parseInt(response);

            item.setBillitNumber(billitNumber > 0 ? billitNumber : 666);
            item.setSendToBillit(true);
            item.setBillitError(false);
            item.setUnpaid(true);
            invoiceService.save(item);
            return response;
        }
        catch(Exception e){
            Notification notification = new Notification();
            notification.setText("Kon de Billit status niet bewaren");
            notification.setThemeName("error");
            notification.open();
            setFaultStatusForInvoice(item);
            return "";
        }
    }

    private void setFaultStatusForInvoice(Invoice item) {
        item.setBillitError(true);
        invoiceService.save(item);
    }

    private InvoiceDTO getInvoiceJson(Invoice item) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        if(this.calcTotalNetFromInvoice(item).get() >=0){
            invoiceDTO.setOrderType("Invoice");
            invoiceDTO.setOrderDirection("Income");
        }
        else{
            invoiceDTO.setOrderType("CreditNote");
            invoiceDTO.setOrderDirection("Income");
        }


        if((item.getPoNumber() != null) && (item.getPoNumber().length() > 0)){
            invoiceDTO.setPoNumber(item.getPoNumber());
        }
        else{
            invoiceDTO.setPoNumber("");
        }

        String s = String.valueOf(item.getFinalInvoiceNumber());
        invoiceDTO.setOrderNumber(s.length() > 2
                ? s.substring(0, 2) + "/" + s.substring(2)
                : s);
        invoiceDTO.setOrderDate(String.valueOf(item.getInvoiceDate()));
        invoiceDTO.setExpiryDate(String.valueOf(item.getExpiryDate()));

        OrderPDFDTO orderPDFDTO = new OrderPDFDTO();
        orderPDFDTO.setFileName("invoice.pdf");
        orderPDFDTO.setFileContent(base64PdfInvoice);
        invoiceDTO.setOrderPDFDTO(orderPDFDTO);

        if(attachementBytes != null){
            List<OrderPDFDTO> attachementPDFDTOList = new ArrayList<>();
            OrderPDFDTO attachementPDFDTO = new OrderPDFDTO();
            attachementPDFDTO.setFileName("attachement.pdf");
            attachementPDFDTO.setFileContent(base64PdfAttachement);
            attachementPDFDTOList.add(attachementPDFDTO);
            invoiceDTO.setAttachementPDFDTO(attachementPDFDTOList);
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName(item.getCustomer().getName());
        customerDTO.setVatNumber(item.getCustomer().getVatNumber());
        customerDTO.setAddressDTOList(getAddressListForCustomer(item.getCustomer()));
        customerDTO.setPartyType("Customer");

        List<IdentifiersDTO>identifiersDTOList = new ArrayList<>();
        IdentifiersDTO identifiersDTO = new IdentifiersDTO();
        identifiersDTO.setIdentifierType("VAT");
        String vat = item.getCustomer().getVatNumber().replaceAll("[A-Za-z.\\s]", "");
        identifiersDTO.setIdentifier(vat);
        identifiersDTOList.add(identifiersDTO);
        customerDTO.setIdentifiersDTOList(identifiersDTOList);

        List<AddressDTO>addressDTOList = new ArrayList<>();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressType("InvoiceAddress");
        addressDTOList.add(addressDTO);
        customerDTO.setAddressDTOList(addressDTOList);

        invoiceDTO.setCustomerDTO(customerDTO);

        List<OrderLinesDTO>orderLinesDTOList = new ArrayList<>();
        OrderLinesDTO orderLinesDTO = new OrderLinesDTO();
        orderLinesDTO.setQuantity("1.0");
        orderLinesDTO.setUnitPriceExcl(String.valueOf(Math.abs(this.calcTotalNetFromInvoice(item).get())));
        orderLinesDTO.setVatPercentage(String.valueOf(Math.abs(item.getProductList().stream().filter(product -> (product.getBComment() != null) && (product.getBComment() == false)).collect(Collectors.toList()).getFirst().getVat().getValue())));
        orderLinesDTO.setDescription("Factuur : " + Double.valueOf(item.getFinalInvoiceNumber()));
        orderLinesDTOList.add(orderLinesDTO);
        invoiceDTO.setOrderLinesDTOList(orderLinesDTOList);

        return invoiceDTO;
    }

    private List<AddressDTO> getAddressListForCustomer(Customer customer) {

        List<AddressDTO> addressDTOList = new ArrayList<>();

        if (customer == null) {
            return addressDTOList;
        }

        if (customer.getAddresses() == null || customer.getAddresses().isEmpty()) {
            return addressDTOList;
        }

        Address invoiceAddress = customer.getAddresses().stream()
                .filter(Objects::nonNull)
                .filter(address -> Boolean.TRUE.equals(address.getInvoiceAddress()))
                .findFirst()
                .orElse(null);

        if (invoiceAddress == null) {
            return addressDTOList;
        }

        AddressDTO invoiceAddressDTO = new AddressDTO();

        invoiceAddressDTO.setAddressType("InvoiceAddress");
        invoiceAddressDTO.setName(customer.getName());
        invoiceAddressDTO.setCity(invoiceAddress.getCity());
        invoiceAddressDTO.setStreet(invoiceAddress.getStreet());
        invoiceAddressDTO.setStreetNumber(invoiceAddress.getNumber());
        invoiceAddressDTO.setZipCode(invoiceAddress.getZip());

        addressDTOList.add(invoiceAddressDTO);

        return addressDTOList;
    }

    public void checkZeroPositionsAndSaveThemToWorkAddress(Invoice selectedInvoice) {

        if (selectedInvoice == null) {
            return;
        }

        Address invoiceWorkAddress = selectedInvoice.getProjectWorkAddress() != null
                ? selectedInvoice.getProjectWorkAddress()
                : selectedInvoice.getWorkAddress();

        if (invoiceWorkAddress == null) {
            Notification.show("Geen werfadres gevonden!");
            return;
        }

        if (selectedInvoice.getProductList() == null
                || selectedInvoice.getProductList().isEmpty()) {
            return;
        }

        Optional<List<Customer>> customerByWorkAddress =
                customerService.getCustomerByWorkAddress(invoiceWorkAddress);

        if (customerByWorkAddress.isEmpty()
                || customerByWorkAddress.get().isEmpty()) {

            Notification.show("Geen klant gevonden met de naam van het werfadres!");
            return;
        }

        Customer customer = customerByWorkAddress.get().getFirst();

        if (customer.getAddresses() == null
                || customer.getAddresses().isEmpty()) {
            return;
        }

        List<Address> workAddresses = customer.getAddresses().stream()
                .filter(Objects::nonNull)
                .filter(address ->
                        Boolean.FALSE.equals(address.getInvoiceAddress())
                )
                .toList();

        if (workAddresses.isEmpty()) {
            Notification.show("Geen werkadres gevonden bij de klant!");
            return;
        }

        Address selectedWorkAddress;

        if (workAddresses.size() == 1) {

            /*
             * Er is slechts één werkadres.
             * Dit adres wordt automatisch gebruikt,
             * ongeacht de naam van invoiceWorkAddress.
             */
            selectedWorkAddress = workAddresses.getFirst();

        } else {

            /*
             * Er zijn meerdere werkadressen.
             * Zoek dan het adres met dezelfde naam
             * als het werfadres van de factuur.
             */
            selectedWorkAddress = workAddresses.stream()
                    .filter(address ->
                            Objects.equals(
                                    address.getAddressName(),
                                    invoiceWorkAddress.getAddressName()
                            )
                    )
                    .findFirst()
                    .orElse(null);

            if (selectedWorkAddress == null) {
                Notification.show(
                        "Geen overeenkomend werkadres gevonden bij de klant!"
                );
                return;
            }
        }

        selectedInvoice.getProductList().stream()
                .filter(Objects::nonNull)
                .filter(product ->
                        "0".equals(product.getPositionNumber())
                )
                .forEach(product -> {

                    if (product.getSelectedAmount() == null) {
                        return;
                    }

                    for (int i = 0;
                         i < product.getSelectedAmount().intValue();
                         i++) {

                        Device device = new Device();

                        device.setDeviceName(
                                product.getInternalName()
                        );

                        device.setCode(
                                product.getProductCode()
                        );

                        if (selectedInvoice.getFinalInvoiceNumber() != null) {

                            device.setInvoiceNumber(
                                    String.valueOf(
                                            selectedInvoice.getFinalInvoiceNumber()
                                    )
                            );
                        }

                        device.setDate(
                                selectedInvoice.getInvoiceDate()
                        );

                        if (product.getProductLevel2() != null) {

                            String level2 =
                                    product.getProductLevel2().getName();

                            if ("Separatietechnieken".equals(level2)) {

                                if (product.getProductLevel3() != null) {

                                    device.setType(
                                            product.getProductLevel3().getName()
                                    );
                                }

                            } else {

                                device.setType(level2);
                            }
                        }

                        String id = deviceService.save(device);

                        if (selectedWorkAddress.getCoupledDeviceList() == null) {

                            selectedWorkAddress.setCoupledDeviceList(
                                    new ArrayList<>()
                            );
                        }

                        selectedWorkAddress
                                .getCoupledDeviceList()
                                .add(id);
                    }
                });

        // =====================================================
        // FOTOBUNDELS AANMAKEN VANUIT DE WERKBONNEN
        // =====================================================

        List<DocumentBundle> bundles =
                selectedWorkAddress.getCoupledDocumentBundle() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(
                        selectedWorkAddress.getCoupledDocumentBundle());

        if (selectedInvoice.getWorkOrderList() != null) {

            for (WorkOrder workOrder : selectedInvoice.getWorkOrderList()) {

                if (workOrder == null
                        || workOrder.getId() == null
                        || workOrder.getImageList() == null
                        || workOrder.getImageList().isEmpty()) {
                    continue;
                }

                List<String> imageIds = workOrder.getImageList().stream()
                        .filter(Objects::nonNull)
                        .filter(id -> !id.isBlank())
                        .distinct()
                        .toList();

                if (imageIds.isEmpty()) {
                    continue;
                }

                // Voorkom dat dezelfde werkbon meerdere keren
                // een fotobundel aanmaakt.
                String bundleId = "workorder-" + workOrder.getId();

                boolean alreadyExists = bundles.stream()
                        .filter(Objects::nonNull)
                        .anyMatch(bundle -> bundleId.equals(bundle.getId()));

                if (alreadyExists) {
                    continue;
                }

                String description = getWorkOrderDescription(workOrder);

                LocalDate bundleDate =
                        workOrder.getWorkDateTime() != null
                                ? workOrder.getWorkDateTime().toLocalDate()
                                : selectedInvoice.getInvoiceDate();

                DocumentBundle bundle = DocumentBundle.builder()
                        .id(bundleId)
                        .date(bundleDate)
                        .comment(description)
                        .image(true)
                        .document(false)
                        .idDocumentList(new ArrayList<>(imageIds))
                        .idMachine(new ArrayList<>())
                        .build();

                bundles.add(bundle);
            }
        }

        selectedWorkAddress.setCoupledDocumentBundle(bundles);

        customerService.save(customer);
    }

    private String getWorkOrderDescription(WorkOrder workOrder) {

        if (workOrder.getWorkOrderHeaderList() == null) {
            return "";
        }

        return workOrder.getWorkOrderHeaderList().stream()
                .filter(Objects::nonNull)
                .map(header -> {

                    String title = header.getTitle() == null
                            ? ""
                            : header.getTitle().trim();

                    String description = header.getDescription() == null
                            ? ""
                            : header.getDescription().trim();

                    return Stream.of(title, description)
                            .filter(text -> !text.isBlank())
                            .collect(Collectors.joining("\n"));
                })
                .filter(text -> !text.isBlank())
                .distinct()
                .collect(Collectors.joining("\n\n"));
    }

    public void addReminder(Invoice invoice) {
        if (invoice.getReminderLevel() < 3) {
            invoice.setReminderLevel(invoice.getReminderLevel() + 1);
        }
    }

    public void removeReminder(Invoice invoice) {
        if (invoice.getReminderLevel() > 0) {
            invoice.setReminderLevel(invoice.getReminderLevel() - 1);
        }
    }

    public String getBase64PdfInvoice() {
        return base64PdfInvoice;
    }

    public String getBase64PdfAttachement() {
        return base64PdfAttachement;
    }

}
