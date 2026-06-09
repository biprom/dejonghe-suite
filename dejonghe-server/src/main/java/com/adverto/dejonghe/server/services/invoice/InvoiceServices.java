package com.adverto.dejonghe.server.services.invoice;

import com.adverto.dejonghe.common.dbservices.DeviceService;
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
import com.vaadin.flow.component.notification.Notification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
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

    public Invoice generateMergedInvoice(Set<WorkOrder> workOrderSet){
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(getNewProFormaInvoiceNumber());
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setExpiryDate(LocalDate.now().plusDays(14));
        invoice.setBFinalInvoice(false);

        Address workAddress = workOrderSet.stream().findFirst().get().getWorkAddress();
        invoice.setWorkAddress(workAddress);

        Optional<List<Customer>> optCustomer = customerService.getCustomerByWorkAddress(workAddress);
        if(optCustomer.isEmpty()){
            Customer customer = new Customer();
            customer.setId(LocalDateTime.now().toString());
            customer.setName(workAddress.getAddressName());
            customer.setVatNumber("");
            customer.setComment("");
            customer.setAlertMessage("");
            List<Address>addressList = new ArrayList<>();
            Address address = new Address();
            addressList.add(address);
            customer.setAddresses(addressList);
            optCustomer = Optional.of(List.of(customer));
        }
        else{
            if(optCustomer.get().size() >= 2){
                Notification.show("Er zijn meerdere klanten met hetzelfde Werkadres");
            }
            invoice.setCustomer(optCustomer.get().get(0));

            List<String> allFotoIds = workOrderSet.stream()
                    .map(WorkOrder::getImageList)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            invoice.setImageList(allFotoIds);

            invoice.setWorkOrderList(workOrderSet);

            List<Product> allProducts = new ArrayList<>();

            //get comment of first WorkOrder and add it as comment
            workOrderSet.forEach(workOrder -> {
                try {
                    String comment = workOrder.getWorkOrderHeaderList().getFirst().getDescription();

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
            });

            //retrieve selected Products
            List<Product> selectedProducts = workOrderSet.stream()
                    .map(WorkOrder::getProductList)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            //sort selected Products
            Comparator<Product> productComparator = (o1, o2) -> compareOnderdeel(o1.getInternalName(), o2.getInternalName());
            selectedProducts.sort(productComparator);

            //add sorted Products to list
            allProducts.addAll(selectedProducts.stream().filter(product->(product.getInternalName() != null) && (product.getInternalName().length() > 0)).collect(Collectors.toList()));

            //place all options at the bottom of the list
            allProducts.sort(Comparator.comparing(
                    p -> (p.getProductLevel1() != null)&&("Extra".equalsIgnoreCase(p.getProductLevel1().getName()))
            ));


            Double generalHoursOnTheMove = 0.0;
            Double programHoursOnTheMove = 0.0;
            Double centrifugeHoursOnTheMove = 0.0;

            Double generalHoursLocal = 0.0;
            Double programHoursLocal = 0.0;
            Double centrifugeHoursLocal = 0.0;


            //Calculate workhours
            for(WorkOrder workOrder : workOrderSet){
                int i = 0;
                for(WorkOrderHeader workOrderHeader : workOrder.getWorkOrderHeaderList()){

                    int numberOfTechnicians = 0;

                    if((workOrder.getWorkOrderHeaderList() != null) && (workOrder.getWorkOrderHeaderList().size() > 0) && (workOrderHeader.getWorkOrderTimeList() != null)){
                        if(i == 0){
                            numberOfTechnicians = numberOfTechnicians + 1 + workOrder.getExtraEmployeesTeam1().size();
                        }
                        if(i == 1){
                            numberOfTechnicians = numberOfTechnicians + 1 + workOrder.getExtraEmployeesTeam2().size();
                        }
                        if(i == 2){
                            numberOfTechnicians = numberOfTechnicians + 1+ workOrder.getExtraEmployeesTeam3().size();
                        }
                        if(i == 3){
                            numberOfTechnicians = numberOfTechnicians+ 1 + workOrder.getExtraEmployeesTeam4().size();
                        }

                        if((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE)) && (workOrderHeader.getWorkType() == WorkType.GENERAL)){
                            for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                                generalHoursOnTheMove = generalHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze())/60.0);
                            }
                        }
                        if((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE) && (workOrderHeader.getWorkType()) == WorkType.CENTRIFUGE)){
                            for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                                centrifugeHoursOnTheMove = centrifugeHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze())/60.0);
                            }
                        }
                        if((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE) && (workOrderHeader.getWorkType()) == WorkType.PROGRAMMATIC)){
                            for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                                programHoursOnTheMove = programHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze())/60.0);
                            }
                        }
                        if((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE)) && (workOrderHeader.getWorkType() == WorkType.GENERAL)){
                            for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                                generalHoursLocal = generalHoursLocal + (numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze())/60.0));
                            }
                        }
                        if((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE) && (workOrderHeader.getWorkType()) == WorkType.CENTRIFUGE)){
                            for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                                centrifugeHoursLocal = centrifugeHoursLocal + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze())/60.0);
                            }
                        }
                        if((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE) && (workOrderHeader.getWorkType()) == WorkType.PROGRAMMATIC)){
                            for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                                programHoursLocal = programHoursLocal + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze())/60.0);
                            }
                        }
                        i++;
                    }
                }
            }


            if(optCustomer.get().getFirst().getBIndustry() == true){

                if(generalHoursLocal > 0){
                    Product workHourRegularLocalIndustrieProduct = productService.getWorkhourForRegularLocal().get();
                    workHourRegularLocalIndustrieProduct.setSelectedAmount(Double.valueOf(generalHoursLocal));
                    workHourRegularLocalIndustrieProduct.setTotalPrice(workHourRegularLocalIndustrieProduct.getSellPriceIndustry()*Double.valueOf(generalHoursLocal));
                    workHourRegularLocalIndustrieProduct.setBWorkHour(true);
                    workHourRegularLocalIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularLocalIndustrieProduct);
                }

                if(centrifugeHoursLocal > 0){
                    Product workHourCentrifugeLocalIndustrieProduct = productService.getWorkhourForCentrifugeLocal().get();
                    workHourCentrifugeLocalIndustrieProduct.setSelectedAmount(Double.valueOf(centrifugeHoursLocal));
                    workHourCentrifugeLocalIndustrieProduct.setTotalPrice(workHourCentrifugeLocalIndustrieProduct.getSellPriceIndustry()*Double.valueOf(centrifugeHoursLocal));
                    workHourCentrifugeLocalIndustrieProduct.setBWorkHour(true);
                    workHourCentrifugeLocalIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeLocalIndustrieProduct);
                }

                if(programHoursLocal > 0){
                    Product workHourProgramLocalIndustrieProduct = productService.getWorkhourForProgammationLocal().get();
                    workHourProgramLocalIndustrieProduct.setSelectedAmount(Double.valueOf(programHoursLocal));
                    workHourProgramLocalIndustrieProduct.setTotalPrice(workHourProgramLocalIndustrieProduct.getSellPriceIndustry()*Double.valueOf(programHoursLocal));
                    workHourProgramLocalIndustrieProduct.setBWorkHour(true);
                    workHourProgramLocalIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramLocalIndustrieProduct);
                }

                if(generalHoursOnTheMove > 0){
                    Product workHourRegularOnTheMoveIndustrieProduct = productService.getWorkhourForRegularOnTheMove().get();
                    workHourRegularOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(generalHoursOnTheMove));
                    workHourRegularOnTheMoveIndustrieProduct.setTotalPrice(workHourRegularOnTheMoveIndustrieProduct.getSellPriceIndustry()*Double.valueOf(generalHoursOnTheMove));
                    workHourRegularOnTheMoveIndustrieProduct.setBWorkHour(true);
                    workHourRegularOnTheMoveIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularOnTheMoveIndustrieProduct);
                }

                if(centrifugeHoursOnTheMove > 0){
                    Product workHourCentrifugeOnTheMoveIndustrieProduct = productService.getWorkhourForCentrifugeOnTheMove().get();
                    workHourCentrifugeOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(centrifugeHoursOnTheMove));
                    workHourCentrifugeOnTheMoveIndustrieProduct.setTotalPrice(workHourCentrifugeOnTheMoveIndustrieProduct.getSellPriceIndustry()*Double.valueOf(centrifugeHoursOnTheMove));
                    workHourCentrifugeOnTheMoveIndustrieProduct.setBWorkHour(true);
                    workHourCentrifugeOnTheMoveIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeOnTheMoveIndustrieProduct);
                }

                if(programHoursOnTheMove > 0){
                    Product workHourProgramOnTheMoveIndustrieProduct = productService.getWorkhourForProgammationOnTheMove().get();
                    workHourProgramOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(programHoursOnTheMove));
                    workHourProgramOnTheMoveIndustrieProduct.setTotalPrice(workHourProgramOnTheMoveIndustrieProduct.getSellPriceIndustry()*Double.valueOf(programHoursOnTheMove));
                    workHourProgramOnTheMoveIndustrieProduct.setBWorkHour(true);
                    workHourProgramOnTheMoveIndustrieProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramOnTheMoveIndustrieProduct);
                }

            }

            else{
                if(generalHoursLocal > 0){
                    Product workHourRegularLocalAgroProduct = productService.getWorkhourForRegularLocal().get();
                    workHourRegularLocalAgroProduct.setSelectedAmount(Double.valueOf(generalHoursLocal));
                    workHourRegularLocalAgroProduct.setTotalPrice(workHourRegularLocalAgroProduct.getSellPrice()*Double.valueOf(generalHoursLocal));
                    workHourRegularLocalAgroProduct.setBWorkHour(true);
                    workHourRegularLocalAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularLocalAgroProduct);
                }

                if(centrifugeHoursLocal > 0){
                    Product workHourCentrifugeLocalAgroProduct = productService.getWorkhourForCentrifugeLocal().get();
                    workHourCentrifugeLocalAgroProduct.setSelectedAmount(Double.valueOf(centrifugeHoursLocal));
                    workHourCentrifugeLocalAgroProduct.setTotalPrice(workHourCentrifugeLocalAgroProduct.getSellPrice()*Double.valueOf(centrifugeHoursLocal));
                    workHourCentrifugeLocalAgroProduct.setBWorkHour(true);
                    workHourCentrifugeLocalAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeLocalAgroProduct);
                }

                if(programHoursLocal > 0){
                    Product workHourProgramLocalAgroProduct = productService.getWorkhourForProgammationLocal().get();
                    workHourProgramLocalAgroProduct.setSelectedAmount(Double.valueOf(programHoursLocal));
                    workHourProgramLocalAgroProduct.setTotalPrice(workHourProgramLocalAgroProduct.getSellPrice()*Double.valueOf(programHoursLocal));
                    workHourProgramLocalAgroProduct.setBWorkHour(true);
                    workHourProgramLocalAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramLocalAgroProduct);
                }
                if(generalHoursOnTheMove > 0){
                    Product workHourRegularOnTheMoveAgroProduct = productService.getWorkhourForRegularOnTheMove().get();
                    workHourRegularOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(generalHoursOnTheMove));
                    workHourRegularOnTheMoveAgroProduct.setTotalPrice(workHourRegularOnTheMoveAgroProduct.getSellPrice()*Double.valueOf(generalHoursOnTheMove));
                    workHourRegularOnTheMoveAgroProduct.setBWorkHour(true);
                    workHourRegularOnTheMoveAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourRegularOnTheMoveAgroProduct);
                }

                if(centrifugeHoursOnTheMove > 0){
                    Product workHourCentrifugeOnTheMoveAgroProduct = productService.getWorkhourForCentrifugeOnTheMove().get();
                    workHourCentrifugeOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(centrifugeHoursOnTheMove));
                    workHourCentrifugeOnTheMoveAgroProduct.setTotalPrice(workHourCentrifugeOnTheMoveAgroProduct.getSellPrice()*Double.valueOf(centrifugeHoursOnTheMove));
                    workHourCentrifugeOnTheMoveAgroProduct.setBWorkHour(true);
                    workHourCentrifugeOnTheMoveAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourCentrifugeOnTheMoveAgroProduct);
                }

                if(programHoursOnTheMove > 0){
                    Product workHourProgramOnTheMoveAgroProduct = productService.getWorkhourForProgammationOnTheMove().get();
                    workHourProgramOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(programHoursOnTheMove));
                    workHourProgramOnTheMoveAgroProduct.setTotalPrice(workHourProgramOnTheMoveAgroProduct.getSellPrice()*Double.valueOf(programHoursOnTheMove));
                    workHourProgramOnTheMoveAgroProduct.setBWorkHour(true);
                    workHourProgramOnTheMoveAgroProduct.setTeamNumber(0);
                    allProducts.add(workHourProgramOnTheMoveAgroProduct);
                }
            }

            // add movement to Proforma
            Double amountKmRegular = 0.0;
            Integer amountRidesRegular = 0;
            Double amountKmTrailer = 0.0;
            Integer amountRidesTrailer = 0;
            Double amountKmCrane = 0.0;
            Integer amountRidesCrane = 0;
            Double amountHoursCraneRegular = 0.0;
            Double amountHoursCraneIntens = 0.0;
            Integer amountForfait = 0;

            for(WorkOrder workOrder : workOrderSet) {
                for (WorkOrderHeader workOrderHeader : workOrder.getWorkOrderHeaderList()) {
                    if((workAddress.getDistance() != null) && (((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.VAN))) ||
                            ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.ATEGO))))){
                        amountKmRegular = amountKmRegular + (workAddress.getDistance());
                        amountRidesRegular = amountRidesRegular + 1;
                    }
                    if((workAddress.getDistance() != null) && (workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_TRAILER))){
                        amountKmTrailer = amountKmTrailer + (workAddress.getDistance());
                        amountRidesTrailer = amountRidesTrailer + 1;
                    }
                    if((workAddress.getDistance() != null) && (workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_CRANE))){
                        amountKmCrane = amountKmCrane + (workAddress.getDistance());
                        amountRidesCrane = amountRidesCrane + 1;
                        if(workOrderHeader.getFleetWorkType().equals(FleetWorkType.DELIVERY)){
                            amountForfait = ++amountForfait;
                        }
                    }
                    if((workOrderHeader.getFleetHours() != null) && (workOrderHeader.getFleetHours() >= 0.0)){
                        if(workOrderHeader.getFleetWorkType().equals(FleetWorkType.REGULAR)){
                            amountHoursCraneRegular = amountHoursCraneRegular + workOrderHeader.getFleetHours();
                        }
                        if(workOrderHeader.getFleetWorkType().equals(FleetWorkType.INTENS)){
                            amountHoursCraneIntens = amountHoursCraneIntens + workOrderHeader.getFleetHours();
                        }
                    }
                }
            }

            if(optCustomer.get().getFirst().getBIndustry() == true){
                if(amountKmRegular > 0.0){
                    Product regularKm = productService.getRegularKm().get();
                    regularKm.setSelectedAmount(amountKmRegular);
                    regularKm.setTotalPrice(amountKmRegular*regularKm.getSellPriceIndustry());
                    regularKm.setBTravel(true);
                    regularKm.setTeamNumber(0);
                    if(amountRidesRegular > 1) {
                        regularKm.setInternalName(regularKm.getInternalName() + "("+ amountRidesRegular + " x heen en terug)");
                    }
                    allProducts.add(regularKm);
                }
                if(amountKmTrailer > 0.0){
                    Product forfaitTtrailer = productService.getWorkHoursTrailerForfait().get();
                    forfaitTtrailer.setSelectedAmount(1.0);
                    forfaitTtrailer.setTotalPrice(forfaitTtrailer.getSellPriceIndustry());
                    forfaitTtrailer.setBTravel(true);
                    forfaitTtrailer.setTeamNumber(0);
                    allProducts.add(forfaitTtrailer);

                    Product trailerKm = productService.getRegularTrailer().get();
                    trailerKm.setSelectedAmount(amountKmTrailer);
                    trailerKm.setTotalPrice(amountKmTrailer*trailerKm.getSellPriceIndustry());
                    trailerKm.setBTravel(true);
                    trailerKm.setTeamNumber(0);
                    if(amountRidesRegular > 1) {
                        trailerKm.setInternalName(trailerKm.getInternalName() + "("+ amountRidesTrailer + " x heen en terug)");
                    }
                    allProducts.add(trailerKm);
                }
                if(amountKmCrane > 0.0){
                    Product craneKm = productService.getRegularCrane().get();
                    craneKm.setSelectedAmount(amountKmCrane);
                    craneKm.setTotalPrice(amountKmCrane*craneKm.getSellPriceIndustry());
                    craneKm.setBTravel(true);
                    craneKm.setTeamNumber(0);
                    if(amountRidesRegular > 1) {
                        craneKm.setInternalName(craneKm.getInternalName() + "("+ amountRidesCrane + " x heen en terug)");
                    }
                    allProducts.add(craneKm);
                }
                if((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular >= 3.0)){
                    Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                    hoursCraneRegularIndustry.setSelectedAmount(amountHoursCraneRegular);
                    hoursCraneRegularIndustry.setTotalPrice(amountHoursCraneRegular*hoursCraneRegularIndustry.getSellPriceIndustry());
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneRegularIndustry);
                }
                else if((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular < 3.0)){
                    Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                    hoursCraneRegularIndustry.setSelectedAmount(3.0);
                    hoursCraneRegularIndustry.setTotalPrice(3.0*hoursCraneRegularIndustry.getSellPriceIndustry());
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneRegularIndustry);
                }
                else if((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens >= 4.0)){
                    Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                    hoursCraneIntenseIndustry.setSelectedAmount(amountHoursCraneIntens);
                    hoursCraneIntenseIndustry.setTotalPrice(amountHoursCraneIntens*hoursCraneIntenseIndustry.getSellPriceIndustry());
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                else if((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens < 4.0)){
                    Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                    hoursCraneIntenseIndustry.setSelectedAmount(4.0);
                    hoursCraneIntenseIndustry.setTotalPrice(4.0*hoursCraneIntenseIndustry.getSellPriceIndustry());
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                if(amountForfait > 0){
                    Product hoursCraneForfaitIndustry = productService.getWorkHoursCraneForfait().get();
                    hoursCraneForfaitIndustry.setSelectedAmount(Double.valueOf(amountForfait));
                    hoursCraneForfaitIndustry.setTotalPrice(amountForfait*hoursCraneForfaitIndustry.getSellPriceIndustry());
                    hoursCraneForfaitIndustry.setBTravel(true);
                    hoursCraneForfaitIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneForfaitIndustry);
                }
            }
            else{
                if(amountKmRegular > 0.0){
                    Product regularKm = productService.getRegularKm().get();
                    regularKm.setSelectedAmount(amountKmRegular);
                    regularKm.setTotalPrice(amountKmRegular*regularKm.getSellPrice());
                    regularKm.setBTravel(true);
                    regularKm.setTeamNumber(0);
                    if(amountRidesRegular > 1) {
                        regularKm.setInternalName(regularKm.getInternalName() + "("+ amountRidesRegular + " x heen en terug)");
                    }
                    allProducts.add(regularKm);
                }
                if(amountKmTrailer > 0.0){

                    Product forfaitTrailer = productService.getWorkHoursTrailerForfait().get();
                    forfaitTrailer.setSelectedAmount(1.0);
                    forfaitTrailer.setTotalPrice(forfaitTrailer.getSellPrice());
                    forfaitTrailer.setBTravel(true);
                    forfaitTrailer.setTeamNumber(0);
                    allProducts.add(forfaitTrailer);

                    Product trailerKm = productService.getRegularTrailer().get();
                    trailerKm.setSelectedAmount(amountKmTrailer);
                    trailerKm.setTotalPrice(amountKmTrailer*trailerKm.getSellPrice());
                    trailerKm.setBTravel(true);
                    trailerKm.setTeamNumber(0);
                    if(amountRidesRegular > 1) {
                        trailerKm.setInternalName(trailerKm.getInternalName() + "("+ amountRidesTrailer + " x heen en terug)");
                    }
                    allProducts.add(trailerKm);
                }
                if(amountKmCrane > 0.0){
                    Product craneKm = productService.getRegularCrane().get();
                    craneKm.setSelectedAmount(amountKmCrane);
                    craneKm.setTotalPrice(amountKmCrane*craneKm.getSellPrice());
                    craneKm.setBTravel(true);
                    craneKm.setTeamNumber(0);
                    if(amountRidesRegular > 1) {
                        craneKm.setInternalName(craneKm.getInternalName() + "("+ amountRidesCrane + " x heen en terug)");
                    }
                    allProducts.add(craneKm);
                }
                if((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular >= 3.0)){
                    Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                    hoursCraneRegularIndustry.setSelectedAmount(amountHoursCraneRegular);
                    hoursCraneRegularIndustry.setTotalPrice(amountHoursCraneRegular*hoursCraneRegularIndustry.getSellPrice());
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneRegularIndustry);
                }
                else if((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular < 3.0)){
                    Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                    hoursCraneRegularIndustry.setSelectedAmount(3.0);
                    hoursCraneRegularIndustry.setTotalPrice(3.0*hoursCraneRegularIndustry.getSellPrice());
                    hoursCraneRegularIndustry.setBTravel(true);
                    hoursCraneRegularIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneRegularIndustry);
                }
                else if((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens >= 4.0)){
                    Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                    hoursCraneIntenseIndustry.setSelectedAmount(amountHoursCraneIntens);
                    hoursCraneIntenseIndustry.setTotalPrice(amountHoursCraneIntens*hoursCraneIntenseIndustry.getSellPrice());
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                else if((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens < 4.0)){
                    Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                    hoursCraneIntenseIndustry.setSelectedAmount(4.0);
                    hoursCraneIntenseIndustry.setTotalPrice(4.0*hoursCraneIntenseIndustry.getSellPrice());
                    hoursCraneIntenseIndustry.setBTravel(true);
                    hoursCraneIntenseIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneIntenseIndustry);
                }
                if(amountForfait > 0){
                    Product hoursCraneForfaitIndustry = productService.getWorkHoursCraneForfait().get();
                    hoursCraneForfaitIndustry.setSelectedAmount(Double.valueOf(amountForfait));
                    hoursCraneForfaitIndustry.setTotalPrice(amountForfait*hoursCraneForfaitIndustry.getSellPrice());
                    hoursCraneForfaitIndustry.setBTravel(true);
                    hoursCraneForfaitIndustry.setTeamNumber(0);
                    allProducts.add(hoursCraneForfaitIndustry);
                }
            }

            //add roadTax / Tunneltax to workorder

            Double totalTax = 0.0;
            Double totalTunnelTax = 0.0;
            for(WorkOrder workOrder : workOrderSet){
                for(WorkOrderHeader workOrderHeader : workOrder.getWorkOrderHeaderList()){
                    Double roadTax = 0.0;
                    Double tunnelTax = 0.0;
                    if((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.VAN))){
                        if(workOrderHeader.getRoadTax() != null){
                            roadTax = workOrderHeader.getRoadTax();
                        }
                        else{
                            roadTax = 0.0;
                        }
                        if(workOrderHeader.getTunnelTax() != null){
                            tunnelTax = workOrderHeader.getTunnelTax();
                        }
                        else{
                            tunnelTax = 0.0;
                        }
                    }
                    if((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.ATEGO))){

                        if(workOrderHeader.getRoadTax() != null){
                            if((workAddress.getRoadTaxAtego() != null) && (workAddress.getRoadTaxAtego() > workOrderHeader.getRoadTax())){
                                roadTax = workAddress.getRoadTaxAtego();
                            }
                            else{
                                roadTax = workOrderHeader.getRoadTax();
                            }
                        }
                        else{
                            roadTax = workAddress.getRoadTaxAtego();
                        }
                        if(workOrderHeader.getTunnelTax() != null){
                            tunnelTax = workOrderHeader.getTunnelTax();
                        }
                        else{
                            tunnelTax = 0.0;
                        }
                    }
                    if((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_TRAILER))){
                        if(workOrderHeader.getRoadTax() != null){
                            if((workAddress.getRoadTaxActros() != null) && (workAddress.getRoadTaxActros() > workOrderHeader.getRoadTax())){
                                roadTax = workAddress.getRoadTaxActros();
                            }
                            else{
                                roadTax = workOrderHeader.getRoadTax();
                            }
                        }
                        else{
                            roadTax = workAddress.getRoadTaxActros();
                        }
                        if(workOrderHeader.getTunnelTax() != null){
                            tunnelTax = workOrderHeader.getTunnelTax();
                        }
                        else{
                            tunnelTax = 0.0;
                        }
                    }
                    if((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_CRANE))){
                        if(workOrderHeader.getRoadTax() != null){
                            if((workAddress.getRoadTaxArocs() != null) && (workAddress.getRoadTaxArocs() > workOrderHeader.getRoadTax())){
                                roadTax = workAddress.getRoadTaxArocs();
                            }
                            else{
                                roadTax = workOrderHeader.getRoadTax();
                            }
                        }
                        else{
                            roadTax = workAddress.getRoadTaxArocs();
                        }
                        if(workOrderHeader.getTunnelTax() != null){
                            tunnelTax = workOrderHeader.getTunnelTax();
                        }
                        else{
                            tunnelTax = 0.0;
                        }
                    }
                    if(roadTax != null){
                        totalTax += roadTax;
                    }
                    if(tunnelTax != null){
                        totalTunnelTax += tunnelTax;
                    }
                }
            }

            if(totalTax > 0.0){
                Product roadTaxProduct = new Product();
                roadTaxProduct.setSelectedAmount(1.0);
                roadTaxProduct.setInternalName("Wegentaks");
                roadTaxProduct.setSellPrice(totalTax);
                roadTaxProduct.setTotalPrice(1.0 * totalTax);
                roadTaxProduct.setBTravel(true);
                roadTaxProduct.setVat(VAT.EENENTWINTIG);
                roadTaxProduct.setTeamNumber(0);
                allProducts.add(roadTaxProduct);
            }

            if (totalTunnelTax > 0.0){
                Product tunnelTaxProduct = new Product();
                tunnelTaxProduct.setSelectedAmount(1.0);
                tunnelTaxProduct.setInternalName("tunneltaks");
                tunnelTaxProduct.setSellPrice(totalTunnelTax);
                tunnelTaxProduct.setTotalPrice(1.0 * totalTunnelTax);
                tunnelTaxProduct.setBTravel(true);
                tunnelTaxProduct.setVat(VAT.EENENTWINTIG);
                tunnelTaxProduct.setTeamNumber(0);
                allProducts.add(tunnelTaxProduct);
            }

            //All products has to have the same date as the starter.
            //this because it is a merged invoice with possibly one attachement
            LocalDateTime starterDateTime = workOrderSet.stream().filter(workorder -> workorder.getStarter() == true).findFirst().get().getWorkDateTime();
            allProducts.stream().forEach(product -> product.setDate(starterDateTime.toLocalDate()));

            invoice.setProductList(allProducts);
            checkIfToolsHoursAreSubtractedFromWorkOrder(invoice);
        }
        return invoice;
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
        Optional<List<Customer>> optCustomer = customerService.getCustomerByWorkAddress(workAddress)
        ;
        if(optCustomer.isEmpty()){
            Customer customer = new Customer();
            customer.setId(LocalDateTime.now().toString());
            customer.setName(workAddress.getAddressName());
            customer.setVatNumber("");
            customer.setComment("");
            customer.setAlertMessage("");
            List<Address>addressList = new ArrayList<>();
            Address address = new Address();
            addressList.add(address);
            customer.setAddresses(addressList);
            optCustomer = Optional.of(List.of(customer));
        }
        else {
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

                //generate empty line
//                Product emptyLine = new Product();
//                emptyLine.setDate(workOrder.getWorkDateTime().toLocalDate());
//                emptyLine.setTeamNumber(0);
//                emptyLine.setBComment(true);
//                allProducts.add(emptyLine);

                //generate Comments/Products per day
                //place comment first


                try {
                    String comment = workOrder.getWorkOrderHeaderList().getFirst().getDescription();

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
                        .filter((product -> (product.getInternalName() != null) && (product.getInternalName().length() > 0)))
                        .collect(Collectors.toList());

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
                                generalHoursOnTheMove = generalHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                        if ((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE) && (workOrderHeader.getWorkType()) == WorkType.CENTRIFUGE)) {
                            for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                                centrifugeHoursOnTheMove = centrifugeHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                        if ((workOrder.getWorkLocation().equals(WorkLocation.ON_THE_MOVE) && (workOrderHeader.getWorkType()) == WorkType.PROGRAMMATIC)) {
                            for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                                programHoursOnTheMove = programHoursOnTheMove + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeUp(), workOrderTime.getTimeDown()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                        if ((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE)) && (workOrderHeader.getWorkType() == WorkType.GENERAL)) {
                            for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                                generalHoursLocal = generalHoursLocal + (numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze()) / 60.0));
                            }
                        }
                        if ((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE) && (workOrderHeader.getWorkType()) == WorkType.CENTRIFUGE)) {
                            for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                                centrifugeHoursLocal = centrifugeHoursLocal + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                        if ((workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE) && (workOrderHeader.getWorkType()) == WorkType.PROGRAMMATIC)) {
                            for (WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()) {
                                programHoursLocal = programHoursLocal + numberOfTechnicians * (((Duration.between(workOrderTime.getTimeStart(), workOrderTime.getTimeStop()).toMinutes())-workOrderTime.getPauze()) / 60.0);
                            }
                        }
                        i++;
                    }
                }

                if (optCustomer.get().getFirst().getBIndustry() == true) {

                    if (generalHoursLocal > 0) {
                        Product workHourRegularLocalIndustrieProduct = productService.getWorkhourForRegularLocal().get();
                        workHourRegularLocalIndustrieProduct.setSelectedAmount(Double.valueOf(generalHoursLocal));
                        workHourRegularLocalIndustrieProduct.setTotalPrice(Double.valueOf(generalHoursLocal) * workHourRegularLocalIndustrieProduct.getSellPriceIndustry());
                        workHourRegularLocalIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourRegularLocalIndustrieProduct.setBWorkHour(true);
                        workHourRegularLocalIndustrieProduct.setTeamNumber(0);
                        allProducts.add(workHourRegularLocalIndustrieProduct);
                    }

                    if (centrifugeHoursLocal > 0) {
                        Product workHourCentrifugeLocalIndustrieProduct = productService.getWorkhourForCentrifugeLocal().get();
                        workHourCentrifugeLocalIndustrieProduct.setSelectedAmount(Double.valueOf(centrifugeHoursLocal));
                        workHourCentrifugeLocalIndustrieProduct.setTotalPrice(Double.valueOf(centrifugeHoursLocal) * workHourCentrifugeLocalIndustrieProduct.getSellPriceIndustry());
                        workHourCentrifugeLocalIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourCentrifugeLocalIndustrieProduct.setBWorkHour(true);
                        workHourCentrifugeLocalIndustrieProduct.setTeamNumber(0);
                        allProducts.add(workHourCentrifugeLocalIndustrieProduct);
                    }

                    if (programHoursLocal > 0) {
                        Product workHourProgramLocalIndustrieProduct = productService.getWorkhourForProgammationLocal().get();
                        workHourProgramLocalIndustrieProduct.setSelectedAmount(Double.valueOf(programHoursLocal));
                        workHourProgramLocalIndustrieProduct.setTotalPrice(Double.valueOf(programHoursLocal) * workHourProgramLocalIndustrieProduct.getSellPriceIndustry());
                        workHourProgramLocalIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourProgramLocalIndustrieProduct.setBWorkHour(true);
                        workHourProgramLocalIndustrieProduct.setTeamNumber(0);
                        allProducts.add(workHourProgramLocalIndustrieProduct);
                    }

                    if (generalHoursOnTheMove > 0) {
                        Product workHourRegularOnTheMoveIndustrieProduct = productService.getWorkhourForRegularOnTheMove().get();
                        workHourRegularOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(generalHoursOnTheMove));
                        workHourRegularOnTheMoveIndustrieProduct.setTotalPrice(Double.valueOf(generalHoursOnTheMove) * workHourRegularOnTheMoveIndustrieProduct.getSellPriceIndustry());
                        workHourRegularOnTheMoveIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourRegularOnTheMoveIndustrieProduct.setBWorkHour(true);
                        workHourRegularOnTheMoveIndustrieProduct.setTeamNumber(0);
                        allProducts.add(workHourRegularOnTheMoveIndustrieProduct);
                    }

                    if (centrifugeHoursOnTheMove > 0) {
                        Product workHourCentrifugeOnTheMoveIndustrieProduct = productService.getWorkhourForCentrifugeOnTheMove().get();
                        workHourCentrifugeOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(centrifugeHoursOnTheMove));
                        workHourCentrifugeOnTheMoveIndustrieProduct.setTotalPrice(Double.valueOf(centrifugeHoursOnTheMove) * workHourCentrifugeOnTheMoveIndustrieProduct.getSellPriceIndustry());
                        workHourCentrifugeOnTheMoveIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourCentrifugeOnTheMoveIndustrieProduct.setBWorkHour(true);
                        workHourCentrifugeOnTheMoveIndustrieProduct.setTeamNumber(0);
                        allProducts.add(workHourCentrifugeOnTheMoveIndustrieProduct);
                    }

                    if (programHoursOnTheMove > 0) {
                        Product workHourProgramOnTheMoveIndustrieProduct = productService.getWorkhourForProgammationOnTheMove().get();
                        workHourProgramOnTheMoveIndustrieProduct.setSelectedAmount(Double.valueOf(programHoursOnTheMove));
                        workHourProgramOnTheMoveIndustrieProduct.setTotalPrice(Double.valueOf(programHoursOnTheMove) * workHourProgramOnTheMoveIndustrieProduct.getSellPriceIndustry());
                        workHourProgramOnTheMoveIndustrieProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourProgramOnTheMoveIndustrieProduct.setBWorkHour(true);
                        workHourProgramOnTheMoveIndustrieProduct.setTeamNumber(0);
                        allProducts.add(workHourProgramOnTheMoveIndustrieProduct);
                    }

                } else {
                    if (generalHoursLocal > 0) {
                        Product workHourRegularLocalAgroProduct = productService.getWorkhourForRegularLocal().get();
                        workHourRegularLocalAgroProduct.setSelectedAmount(Double.valueOf(generalHoursLocal));
                        workHourRegularLocalAgroProduct.setTotalPrice(Double.valueOf(generalHoursLocal) * workHourRegularLocalAgroProduct.getSellPrice());
                        workHourRegularLocalAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourRegularLocalAgroProduct.setBWorkHour(true);
                        workHourRegularLocalAgroProduct.setTeamNumber(0);
                        allProducts.add(workHourRegularLocalAgroProduct);
                    }

                    if (centrifugeHoursLocal > 0) {
                        Product workHourCentrifugeLocalAgroProduct = productService.getWorkhourForCentrifugeLocal().get();
                        workHourCentrifugeLocalAgroProduct.setSelectedAmount(Double.valueOf(centrifugeHoursLocal));
                        workHourCentrifugeLocalAgroProduct.setTotalPrice(Double.valueOf(centrifugeHoursLocal) * workHourCentrifugeLocalAgroProduct.getSellPrice());
                        workHourCentrifugeLocalAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourCentrifugeLocalAgroProduct.setBWorkHour(true);
                        workHourCentrifugeLocalAgroProduct.setTeamNumber(0);
                        allProducts.add(workHourCentrifugeLocalAgroProduct);
                    }

                    if (programHoursLocal > 0) {
                        Product workHourProgramLocalAgroProduct = productService.getWorkhourForProgammationLocal().get();
                        workHourProgramLocalAgroProduct.setSelectedAmount(Double.valueOf(programHoursLocal));
                        workHourProgramLocalAgroProduct.setTotalPrice(Double.valueOf(programHoursLocal) * workHourProgramLocalAgroProduct.getSellPrice());
                        workHourProgramLocalAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourProgramLocalAgroProduct.setBWorkHour(true);
                        workHourProgramLocalAgroProduct.setTeamNumber(0);
                        allProducts.add(workHourProgramLocalAgroProduct);
                    }
                    if (generalHoursOnTheMove > 0) {
                        Product workHourRegularOnTheMoveAgroProduct = productService.getWorkhourForRegularOnTheMove().get();
                        workHourRegularOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(generalHoursOnTheMove));
                        workHourRegularOnTheMoveAgroProduct.setTotalPrice(Double.valueOf(generalHoursOnTheMove) * workHourRegularOnTheMoveAgroProduct.getSellPrice());
                        workHourRegularOnTheMoveAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourRegularOnTheMoveAgroProduct.setBWorkHour(true);
                        workHourRegularOnTheMoveAgroProduct.setTeamNumber(0);
                        allProducts.add(workHourRegularOnTheMoveAgroProduct);
                    }

                    if (centrifugeHoursOnTheMove > 0) {
                        Product workHourCentrifugeOnTheMoveAgroProduct = productService.getWorkhourForCentrifugeOnTheMove().get();
                        workHourCentrifugeOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(centrifugeHoursOnTheMove));
                        workHourCentrifugeOnTheMoveAgroProduct.setTotalPrice(Double.valueOf(centrifugeHoursOnTheMove) * workHourCentrifugeOnTheMoveAgroProduct.getSellPrice());
                        workHourCentrifugeOnTheMoveAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourCentrifugeOnTheMoveAgroProduct.setBWorkHour(true);
                        workHourCentrifugeOnTheMoveAgroProduct.setTeamNumber(0);
                        allProducts.add(workHourCentrifugeOnTheMoveAgroProduct);
                    }

                    if (programHoursOnTheMove > 0) {
                        Product workHourProgramOnTheMoveAgroProduct = productService.getWorkhourForProgammationOnTheMove().get();
                        workHourProgramOnTheMoveAgroProduct.setSelectedAmount(Double.valueOf(programHoursOnTheMove));
                        workHourProgramOnTheMoveAgroProduct.setTotalPrice(Double.valueOf(programHoursOnTheMove) * workHourProgramOnTheMoveAgroProduct.getSellPrice());
                        workHourProgramOnTheMoveAgroProduct.setDate(workOrder.getWorkDateTime().toLocalDate());
                        workHourProgramOnTheMoveAgroProduct.setBWorkHour(true);
                        workHourProgramOnTheMoveAgroProduct.setTeamNumber(0);
                        allProducts.add(workHourProgramOnTheMoveAgroProduct);
                    }
                }

                // add movement to Proforma
                Double amountKmRegular = 0.0;
                Integer amountRidesRegular = 0;
                Double amountKmTrailer = 0.0;
                Integer amountRidesTrailer = 0;
                Double amountKmCrane = 0.0;
                Integer amountRidesCrane = 0;
                Double amountHoursCraneRegular = 0.0;
                Double amountHoursCraneIntens = 0.0;
                Integer amountForfait = 0;


                for (WorkOrderHeader workOrderHeader : workOrder.getWorkOrderHeaderList()) {
                    if ((workAddress.getDistance() != null) && ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.VAN))) ||
                            ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.ATEGO)))) {
                        amountKmRegular = amountKmRegular + (workAddress.getDistance());
                        amountRidesRegular = amountRidesRegular + 1;
                    }
                    if ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_TRAILER))) {
                        amountKmTrailer = amountKmTrailer + (workAddress.getDistance());
                        amountRidesTrailer = amountRidesTrailer + 1;
                    }
                    if ((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_CRANE))) {
                        amountKmCrane = amountKmCrane + (workAddress.getDistance());
                        amountRidesCrane = amountRidesCrane + 1;
                        if (workOrderHeader.getFleetWorkType().equals(FleetWorkType.DELIVERY)) {
                            amountForfait = ++amountForfait;
                        }
                    }
                    if ((workOrderHeader.getFleetHours() != null) && (workOrderHeader.getFleetHours() >= 0.0)) {
                        if (workOrderHeader.getFleetWorkType().equals(FleetWorkType.REGULAR)) {
                            amountHoursCraneRegular = amountHoursCraneRegular + workOrderHeader.getFleetHours();
                        }
                        if (workOrderHeader.getFleetWorkType().equals(FleetWorkType.INTENS)) {
                            amountHoursCraneIntens = amountHoursCraneIntens + workOrderHeader.getFleetHours();
                        }
                    }
                }


                if (optCustomer.get().getFirst().getBIndustry() == true) {
                    if (amountKmRegular > 0.0) {
                        Product regularKm = productService.getRegularKm().get();
                        regularKm.setSelectedAmount(amountKmRegular);
                        regularKm.setTotalPrice(amountKmRegular * regularKm.getSellPriceIndustry());
                        regularKm.setTeamNumber(0);
                        regularKm.setBTravel(true);
                        regularKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                        if(amountRidesRegular > 1) {
                            regularKm.setInternalName(regularKm.getInternalName() + "("+ amountRidesRegular + " x heen en terug)");
                        }
                        allProducts.add(regularKm);
                    }
                    if (amountKmTrailer > 0.0) {
                        Product forfaitTrailer = productService.getWorkHoursTrailerForfait().get();
                        forfaitTrailer.setSelectedAmount(1.0);
                        forfaitTrailer.setTotalPrice(forfaitTrailer.getSellPriceIndustry());
                        forfaitTrailer.setTeamNumber(0);
                        forfaitTrailer.setBTravel(true);
                        forfaitTrailer.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(forfaitTrailer);

                        Product trailerKm = productService.getRegularTrailer().get();
                        trailerKm.setSelectedAmount(amountKmTrailer);
                        trailerKm.setTotalPrice(amountKmTrailer * trailerKm.getSellPriceIndustry());
                        trailerKm.setTeamNumber(0);
                        trailerKm.setBTravel(true);
                        trailerKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                        if(amountRidesTrailer > 1) {
                            trailerKm.setInternalName(trailerKm.getInternalName() + "("+ amountRidesTrailer + " x heen en terug)");
                        }
                        allProducts.add(trailerKm);
                    }
                    if (amountKmCrane > 0.0) {
                        Product craneKm = productService.getRegularCrane().get();
                        craneKm.setSelectedAmount(amountKmCrane);
                        craneKm.setTotalPrice(amountKmCrane * craneKm.getSellPriceIndustry());
                        craneKm.setTeamNumber(0);
                        craneKm.setBTravel(true);
                        craneKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                        if(amountRidesCrane > 1) {
                            craneKm.setInternalName(craneKm.getInternalName() + "("+ amountRidesCrane + " x heen en terug)");
                        }
                        allProducts.add(craneKm);
                    }
                    if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular >= 3.0)) {
                        Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                        hoursCraneRegularIndustry.setSelectedAmount(amountHoursCraneRegular);
                        hoursCraneRegularIndustry.setTotalPrice(amountHoursCraneRegular * hoursCraneRegularIndustry.getSellPriceIndustry());
                        hoursCraneRegularIndustry.setTeamNumber(0);
                        hoursCraneRegularIndustry.setBTravel(true);
                        hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneRegularIndustry);
                    }
                    else if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular < 3.0)) {
                        Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                        hoursCraneRegularIndustry.setSelectedAmount(3.0);
                        hoursCraneRegularIndustry.setTotalPrice(3.0 * hoursCraneRegularIndustry.getSellPriceIndustry());
                        hoursCraneRegularIndustry.setTeamNumber(0);
                        hoursCraneRegularIndustry.setBTravel(true);
                        hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneRegularIndustry);
                    }
                    else if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens >= 4.0)) {
                        Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                        hoursCraneIntenseIndustry.setSelectedAmount(amountHoursCraneIntens);
                        hoursCraneIntenseIndustry.setTotalPrice(amountHoursCraneIntens * hoursCraneIntenseIndustry.getSellPriceIndustry());
                        hoursCraneIntenseIndustry.setTeamNumber(0);
                        hoursCraneIntenseIndustry.setBTravel(true);
                        hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneIntenseIndustry);
                    }
                    else if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens < 4.0)) {
                        Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                        hoursCraneIntenseIndustry.setSelectedAmount(4.0);
                        hoursCraneIntenseIndustry.setTotalPrice(4.0 * hoursCraneIntenseIndustry.getSellPriceIndustry());
                        hoursCraneIntenseIndustry.setTeamNumber(0);
                        hoursCraneIntenseIndustry.setBTravel(true);
                        hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneIntenseIndustry);
                    }
                    if (amountForfait > 0) {
                        Product hoursCraneForfaitIndustry = productService.getWorkHoursCraneForfait().get();
                        hoursCraneForfaitIndustry.setSelectedAmount(Double.valueOf(amountForfait));
                        hoursCraneForfaitIndustry.setTotalPrice(amountForfait * hoursCraneForfaitIndustry.getSellPriceIndustry());
                        hoursCraneForfaitIndustry.setTeamNumber(0);
                        hoursCraneForfaitIndustry.setBTravel(true);
                        hoursCraneForfaitIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneForfaitIndustry);
                    }
                } else {
                    if (amountKmRegular > 0.0) {
                        Product regularKm = productService.getRegularKm().get();
                        regularKm.setSelectedAmount(amountKmRegular);
                        regularKm.setTotalPrice(amountKmRegular * regularKm.getSellPrice());
                        regularKm.setTeamNumber(0);
                        regularKm.setBTravel(true);
                        regularKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                        if(amountRidesRegular > 1) {
                            regularKm.setInternalName(regularKm.getInternalName() + "("+ amountRidesRegular + " x heen en terug)");
                        }
                        allProducts.add(regularKm);
                    }
                    if (amountKmTrailer > 0.0) {

                        Product forfaitTrailer = productService.getWorkHoursTrailerForfait().get();
                        forfaitTrailer.setSelectedAmount(amountKmTrailer);
                        forfaitTrailer.setTotalPrice(forfaitTrailer.getSellPrice());
                        forfaitTrailer.setTeamNumber(0);
                        forfaitTrailer.setBTravel(true);
                        forfaitTrailer.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(forfaitTrailer);


                        Product trailerKm = productService.getRegularTrailer().get();
                        trailerKm.setSelectedAmount(amountKmTrailer);
                        trailerKm.setTotalPrice(amountKmTrailer * trailerKm.getSellPrice());
                        trailerKm.setTeamNumber(0);
                        trailerKm.setBTravel(true);
                        trailerKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                        if(amountRidesTrailer > 1) {
                            trailerKm.setInternalName(trailerKm.getInternalName() + "("+ amountRidesTrailer + " x heen en terug)");
                        }
                        allProducts.add(trailerKm);
                    }
                    if (amountKmCrane > 0.0) {
                        Product craneKm = productService.getRegularCrane().get();
                        craneKm.setSelectedAmount(amountKmCrane);
                        craneKm.setTotalPrice(amountKmCrane * craneKm.getSellPrice());
                        craneKm.setTeamNumber(0);
                        craneKm.setBTravel(true);
                        craneKm.setDate(workOrder.getWorkDateTime().toLocalDate());
                        if(amountRidesCrane > 1) {
                            craneKm.setInternalName(craneKm.getInternalName() + "("+ amountRidesCrane + " x heen en terug)");
                        }
                        allProducts.add(craneKm);
                    }
                    if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular >= 3.0)) {
                        Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                        hoursCraneRegularIndustry.setSelectedAmount(amountHoursCraneRegular);
                        hoursCraneRegularIndustry.setTotalPrice(amountHoursCraneRegular * hoursCraneRegularIndustry.getSellPrice());
                        hoursCraneRegularIndustry.setTeamNumber(0);
                        hoursCraneRegularIndustry.setBTravel(true);
                        hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneRegularIndustry);
                    }
                    else if ((amountHoursCraneRegular > 0.0) && (amountHoursCraneRegular < 3.0)) {
                        Product hoursCraneRegularIndustry = productService.getWorkHoursCraneRegular().get();
                        hoursCraneRegularIndustry.setSelectedAmount(3.0);
                        hoursCraneRegularIndustry.setTotalPrice(3.0 * hoursCraneRegularIndustry.getSellPrice());
                        hoursCraneRegularIndustry.setTeamNumber(0);
                        hoursCraneRegularIndustry.setBTravel(true);
                        hoursCraneRegularIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneRegularIndustry);
                    }
                    else if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens >= 4.0)) {
                        Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                        hoursCraneIntenseIndustry.setSelectedAmount(amountHoursCraneIntens);
                        hoursCraneIntenseIndustry.setTotalPrice(amountHoursCraneIntens * hoursCraneIntenseIndustry.getSellPrice());
                        hoursCraneIntenseIndustry.setTeamNumber(0);
                        hoursCraneIntenseIndustry.setBTravel(true);
                        hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneIntenseIndustry);
                    }
                    else if ((amountHoursCraneIntens > 0.0) && (amountHoursCraneIntens < 4.0)) {
                        Product hoursCraneIntenseIndustry = productService.getWorkHoursCraneIntense().get();
                        hoursCraneIntenseIndustry.setSelectedAmount(4.0);
                        hoursCraneIntenseIndustry.setTotalPrice(4.0 * hoursCraneIntenseIndustry.getSellPrice());
                        hoursCraneIntenseIndustry.setTeamNumber(0);
                        hoursCraneIntenseIndustry.setBTravel(true);
                        hoursCraneIntenseIndustry.setDate(workOrder.getWorkDateTime().toLocalDate());
                        allProducts.add(hoursCraneIntenseIndustry);
                    }
                    if (amountForfait > 0) {
                        Product hoursCraneForfaitIndustry = productService.getWorkHoursCraneForfait().get();
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

                invoice.setProductList(allProducts);

                checkIfToolsHoursAreSubtractedFromWorkOrder(invoice);
            }
        }
        return invoice;
    }

    private Double getSellPriceOfWorkHour(List<Customer> customers, WorkType workType, WorkLocation workLocation) {
        if(customers.get(0).getBAgro()){
            if(workType == WorkType.CENTRIFUGE){
                Optional<Product> first = productService.getAllProductsByCategory("Agro", "Werkuren").get().stream().filter(item -> item.getInternalName().contains("Werkuren - centrifuge")).findFirst();
                if(first.isPresent()){
                    return first.get().getSellPrice();
                }
                return 0.0;
            }
            if(workType == WorkType.PROGRAMMATIC){
                Optional<Product> first = productService.getAllProductsByCategory("Agro", "Werkuren").get().stream().filter(item -> item.getInternalName().contains("Werkuren - programmatie")).findFirst();
                if(first.isPresent()){
                    return first.get().getSellPrice();
                }
                return 0.0;
            }
            if(workType == WorkType.GENERAL){
                Optional<Product> first = productService.getAllProductsByCategory("Agro", "Werkuren").get().stream().filter(item -> item.getInternalName().contains("Werkuren - verplaatsing")).findFirst();
                if(first.isPresent()){
                    return first.get().getSellPrice();
                }
                return 0.0;
            }
        }
        if(customers.get(1).getBIndustry()){
            if(workType == WorkType.CENTRIFUGE){
                Optional<Product> first = productService.getAllProductsByCategory("Industrie", "Werkuren").get().stream().filter(item -> item.getInternalName().contains("Werkuren - centrifuge")).findFirst();
                if(first.isPresent()){
                    return first.get().getSellPrice();
                }
                return 0.0;
            }
            if(workType == WorkType.PROGRAMMATIC){
                Optional<Product> first = productService.getAllProductsByCategory("Industrie", "Werkuren").get().stream().filter(item -> item.getInternalName().contains("Werkuren - programmatie")).findFirst();
                if(first.isPresent()){
                    return first.get().getSellPrice();
                }
                return 0.0;
            }
            if(workType == WorkType.GENERAL){
                Optional<Product> first = productService.getAllProductsByCategory("Industrie", "Werkuren").get().stream().filter(item -> item.getInternalName().contains("Werkuren - verplaatsing")).findFirst();
                if(first.isPresent()){
                    return first.get().getSellPrice();
                }
                return 0.0;
            }
        }
        return 0.0;
    }


    public String generateInvoicePDF(Invoice invoice){

        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;

        baosAttachement = new ByteArrayOutputStream();

        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter COMPAREDFORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        parameters.clear();

        try {
            if((invoice.getProjectWorkAddress() != null)){
                parameters.put("werfAdres", "Werfadres : " + "\n" +
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

        List<Product>totalProductList = new ArrayList<>(invoice.getProductList());

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


        //generate pointer for every different attachement (every attachement has a date id!)

        if((attachments != null) && (attachments.size() > 0)){

            List<LocalDate> uniqueDates = attachments.stream()
                    .map(Product::getAttachementNumber)
                    .distinct()
                    .collect(Collectors.toList());

            for(LocalDate uniqueDate : uniqueDates){

                Product pointerProduct = new Product();
                pointerProduct.setTeamNumber(0);
                pointerProduct.setSelectedAmount(1.0);
                pointerProduct.setTotalPrice(attachments.stream()
                                .filter(product -> product.getAttachementNumber().equals(uniqueDate))
                        .map(Product::getTotalPrice)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .sum());
                pointerProduct.setSellPrice(pointerProduct.getTotalPrice());
                pointerProduct.setSellPriceIndustry(pointerProduct.getTotalPrice());
                pointerProduct.setVat(attachments.get(0).getVat());
                pointerProduct.setInternalName("materiaal (zie bijlage)");

                OptionalInt indexWorkHoursOpt =
                        IntStream.range(0, products.size())
                                .filter(i -> products.get(i).getBWorkHour() != null)
                                .filter(i -> (products.get(i).getBWorkHour()) && (products.get(i).getDateToShowOnInvoice() != null) && (products.get(i).getDateToShowOnInvoice().matches(uniqueDate.format(COMPAREDFORMATTER))))
                                .reduce((first, second) -> first);

                OptionalInt indexCommentOpt =
                        IntStream.range(0, products.size())
                            .filter(i -> products.get(i).getBComment() != null)
                            .filter(i -> (products.get(i).getBComment()) && (products.get(i).getDateToShowOnInvoice() != null) && (products.get(i).getDateToShowOnInvoice().matches(uniqueDate.format(COMPAREDFORMATTER))))
                            .reduce((first, second) -> second);

                products.stream().forEach(product -> {
                    System.out.println(product.getDateToShowOnInvoice() + "=" +uniqueDate.format(COMPAREDFORMATTER));
                });


                if (indexWorkHoursOpt.isPresent()) {
                    int index = indexWorkHoursOpt.getAsInt();
                    products.add(index - 0, pointerProduct);
                } else if (indexCommentOpt.isPresent()) {
                    int index = indexCommentOpt.getAsInt();
                    products.add(index + 1, pointerProduct);
                }
                else{
                    products.add( pointerProduct);
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
                .filter(product -> product.getTotalPrice() != null)
                .mapToDouble(Product::getTotalPrice)
                .sum();

        parameters.put("netto", totalPriceInvoice);

        if(invoice.getCustomer().getVatNumber().contains("BE")){
            double btwBedrag = invoice.getProductList().stream()
                    .filter(product -> product.getTotalPrice() != null)
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

        String formatted = String.valueOf(invoice.getInvoiceNumber()).substring(0, 2) + "-" + String.valueOf(invoice.getInvoiceNumber()).substring(2);
        String exportName = rootFolder + ""+ formatted+".pdf";

        try {
            invoiceBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            Files.write(Paths.get(exportName), invoiceBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }


        //now show it in a new tab in the browser
        pdfController.setPdfNaam(""+ formatted+".pdf");

        UI.getCurrent().getPage().open("/pdf", "_blank");

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
        return "invoice_"+ invoice.getInvoiceNumber()+".pdf";
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

    public void checkIfToolsHoursAreSubtractedFromWorkOrder(Invoice selectedInvoice){
        if((selectedInvoice != null) && (selectedInvoice.getProductList() != null)){
            if(selectedInvoice.getProductList().size() > 0){

                Optional<Product> optWorkHoursProduct = selectedInvoice.getProductList().stream().filter(product -> (product.getProductCode().contains("WU")) && (product.getBWorkHour() == true)).findFirst();

                List<Product> laserListToSubtract = selectedInvoice.getProductList().stream().filter(product -> product.getProductCode().matches("OPAT-laser")).collect(Collectors.toList());
                List<Product> bendListToSubtract = selectedInvoice.getProductList().stream().filter(product -> product.getProductCode().matches("OPAT-plooi")).collect(Collectors.toList());
                List<Product> laserbendListToSubtract = selectedInvoice.getProductList().stream().filter(product -> product.getProductCode().matches("OPAT-laser-plooi")).collect(Collectors.toList());
                List<Product> cncToSubtract = selectedInvoice.getProductList().stream().filter(product -> product.getProductCode().matches("OPAT-dr-fr")).collect(Collectors.toList());
                List<Product> cncCuttingListToSubtract = selectedInvoice.getProductList().stream().filter(product -> product.getProductCode().matches("OPAT-cncsn")).collect(Collectors.toList());


                if((laserListToSubtract != null) && (laserListToSubtract.size() > 0)){
                    Double totalLaserMinutesToSubtract = laserListToSubtract.stream()
                            .mapToDouble(item -> item.getSelectedAmount())
                            .sum();

//                    Product productToAdd = productService.findByProductCodeEqualCaseInsensitive("OPAT-laser").get().getFirst();
//                    productToAdd.setSelectedAmount(totalLaserMinutesToSubtract);

//                    productToAdd.setVat(VAT.EENENTWINTIG);
//                    productToAdd.setBWorkHour(true);
//                    selectedInvoice.getProductList().remove(laserListToSubtract);
//                    selectedInvoice.getProductList().add(productToAdd);

                    if((optWorkHoursProduct.isPresent()) && (!optWorkHoursProduct.isEmpty())){
                        Double correctedAmountWorkedHours = optWorkHoursProduct.get().getSelectedAmount() - totalLaserMinutesToSubtract;
                        optWorkHoursProduct.get().setSelectedAmount(correctedAmountWorkedHours);
                        if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
                            if(optWorkHoursProduct.get().getSellPriceIndustry() > 0.0){
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPriceIndustry());
                            }
                            else{
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                            }
                        }
                        else{
                            optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                        }
                    }
                }

                if((bendListToSubtract != null) && (bendListToSubtract.size() > 0)){
                    Double totalBendMinutesToSubtract = laserListToSubtract.stream()
                            .mapToDouble(item -> item.getSelectedAmount())
                            .sum();

//                    Product productToAdd = productService.findByProductCodeEqualCaseInsensitive("OPAT-plooi").get().getFirst();
//                    productToAdd.setSelectedAmount(totalBendMinutesToSubtract);
//                    if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
//                        if(productToAdd.getSellPriceIndustry() > 0.0){
//                            productToAdd.setTotalPrice(totalBendMinutesToSubtract * productToAdd.getSellPriceIndustry());
//                        }
//                        else{
//                            productToAdd.setTotalPrice(totalBendMinutesToSubtract * productToAdd.getSellPrice());
//                        }
//                    }
//                    else{
//                        productToAdd.setTotalPrice(totalBendMinutesToSubtract * productToAdd.getSellPrice());
//                    }
//                    productToAdd.setVat(VAT.EENENTWINTIG);
//                    productToAdd.setBWorkHour(true);
//                    selectedInvoice.getProductList().remove(bendListToSubtract);
//                    selectedInvoice.getProductList().add(productToAdd);

                    if((optWorkHoursProduct.isPresent()) && (!optWorkHoursProduct.isEmpty())){
                        Double correctedAmountWorkedHours = optWorkHoursProduct.get().getSelectedAmount() - totalBendMinutesToSubtract;
                        optWorkHoursProduct.get().setSelectedAmount(correctedAmountWorkedHours);

                        if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
                            if(optWorkHoursProduct.get().getSellPriceIndustry() > 0.0){
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPriceIndustry());
                            }
                            else{
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                            }
                        }
                        else{
                            optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                        }
                    }
                }

                if((laserbendListToSubtract != null) && (laserbendListToSubtract.size() > 0)){
                    Double totalLaserBendMinutesToSubtract = laserListToSubtract.stream()
                            .mapToDouble(item -> item.getSelectedAmount())
                            .sum();

//                    Product productToAdd = productService.findByProductCodeEqualCaseInsensitive("OPAT-laser-plooi").get().getFirst();
//                    productToAdd.setSelectedAmount(totalLaserBendMinutesToSubtract);
//                    if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
//                        if(productToAdd.getSellPriceIndustry() > 0.0){
//                            productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPriceIndustry());
//                        }
//                        else{
//                            productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPrice());
//                        }
//                    }
//                    else{
//                        productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPrice());
//                    }
//                    productToAdd.setVat(VAT.EENENTWINTIG);
//                    productToAdd.setBWorkHour(true);
//                    selectedInvoice.getProductList().remove(laserbendListToSubtract);
//                    selectedInvoice.getProductList().add(productToAdd);

                    if((optWorkHoursProduct.isPresent()) && (!optWorkHoursProduct.isEmpty())){
                        Double correctedAmountWorkedHours = optWorkHoursProduct.get().getSelectedAmount() - totalLaserBendMinutesToSubtract;
                        optWorkHoursProduct.get().setSelectedAmount(correctedAmountWorkedHours);

                        if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
                            if(optWorkHoursProduct.get().getSellPriceIndustry() > 0.0){
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPriceIndustry());
                            }
                            else{
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                            }
                        }
                        else{
                            optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                        }
                    }
                }

                if((cncToSubtract != null) && (cncToSubtract.size() > 0)){
                    Double totalCncMinutesToSubtract = cncToSubtract.stream()
                            .mapToDouble(item -> item.getSelectedAmount())
                            .sum();

//                    Product productToAdd = productService.findByProductCodeEqualCaseInsensitive("OPAT-laser-plooi").get().getFirst();
//                    productToAdd.setSelectedAmount(totalLaserBendMinutesToSubtract);
//                    if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
//                        if(productToAdd.getSellPriceIndustry() > 0.0){
//                            productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPriceIndustry());
//                        }
//                        else{
//                            productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPrice());
//                        }
//                    }
//                    else{
//                        productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPrice());
//                    }
//                    productToAdd.setVat(VAT.EENENTWINTIG);
//                    productToAdd.setBWorkHour(true);
//                    selectedInvoice.getProductList().remove(laserbendListToSubtract);
//                    selectedInvoice.getProductList().add(productToAdd);

                    if((optWorkHoursProduct.isPresent()) && (!optWorkHoursProduct.isEmpty())){
                        Double correctedAmountWorkedHours = optWorkHoursProduct.get().getSelectedAmount() - totalCncMinutesToSubtract;
                        optWorkHoursProduct.get().setSelectedAmount(correctedAmountWorkedHours);

                        if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
                            if(optWorkHoursProduct.get().getSellPriceIndustry() > 0.0){
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPriceIndustry());
                            }
                            else{
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                            }
                        }
                        else{
                            optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                        }

                    }
                }

                if((cncCuttingListToSubtract != null) && (cncCuttingListToSubtract.size() > 0)){
                    Double totalCncCuttingMinutesToSubtract = cncCuttingListToSubtract.stream()
                            .mapToDouble(item -> item.getSelectedAmount())
                            .sum();

//                    Product productToAdd = productService.findByProductCodeEqualCaseInsensitive("OPAT-laser-plooi").get().getFirst();
//                    productToAdd.setSelectedAmount(totalLaserBendMinutesToSubtract);
//                    if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
//                        if(productToAdd.getSellPriceIndustry() > 0.0){
//                            productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPriceIndustry());
//                        }
//                        else{
//                            productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPrice());
//                        }
//                    }
//                    else{
//                        productToAdd.setTotalPrice(totalLaserBendMinutesToSubtract * productToAdd.getSellPrice());
//                    }
//                    productToAdd.setVat(VAT.EENENTWINTIG);
//                    productToAdd.setBWorkHour(true);
//                    selectedInvoice.getProductList().remove(laserbendListToSubtract);
//                    selectedInvoice.getProductList().add(productToAdd);

                    if((optWorkHoursProduct.isPresent()) && (!optWorkHoursProduct.isEmpty())){
                        Double correctedAmountWorkedHours = optWorkHoursProduct.get().getSelectedAmount() - totalCncCuttingMinutesToSubtract;
                        optWorkHoursProduct.get().setSelectedAmount(correctedAmountWorkedHours);

                        if((selectedInvoice.getCustomer().getBIndustry()!= null) && (selectedInvoice.getCustomer().getBIndustry() == true)){
                            if(optWorkHoursProduct.get().getSellPriceIndustry() > 0.0){
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPriceIndustry());
                            }
                            else{
                                optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                            }
                        }
                        else{
                            optWorkHoursProduct.get().setTotalPrice(correctedAmountWorkedHours * optWorkHoursProduct.get().getSellPrice());
                        }
                    }
                }
            }
        }
    }

    public Optional<Double> calcTotalNetFromInvoice(Invoice invoice) {
        if(invoice.getProductList() != null){
            for (Product item : invoice.getProductList()) {
                double roundedTotalPrice = BigDecimal
                        .valueOf(item.getTotalPrice())
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                item.setTotalPrice(roundedTotalPrice);

            }
            Double totalNet = invoice.getProductList().stream()
                    .mapToDouble(item -> item.getTotalPrice())
                    .sum();

            return Optional.of(totalNet);
        }
        return Optional.empty();
    }

    public Optional<Double> calcTotalTaxFromInvoice(Invoice invoice) {
        if(invoice.getProductList() != null){
            for (Product item : invoice.getProductList()) {
                double roundedTotalPrice = BigDecimal
                        .valueOf(item.getTotalPrice())
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                item.setTotalPrice(roundedTotalPrice);

            }
            Double totalTax = invoice.getProductList().stream()
                    .mapToDouble(x -> (x.getTotalPrice() * x.getVat().getValue())/100)
                    .sum();

            double roundedTotalTax = BigDecimal
                    .valueOf(totalTax)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            if(invoice.getCustomer().getVatNumber().contains("BE")){
                return Optional.of(roundedTotalTax);
            }
            else{
                return Optional.of(0.0);
            }
        }
        return Optional.empty();
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
                    totalToBePayed = totalToBePayed + calcTotalNetFromInvoice(invoice).get()+ calcTotalTaxFromInvoice(invoice).get() - payed;
                }
                else{
                    totalToBePayed = totalToBePayed + calcTotalNetFromInvoice(invoice).get()+ calcTotalTaxFromInvoice(invoice).get();
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

    public void checkZeroPositionsAndSaveThemToWorkAddress(Invoice selectedInvoice){
        if(selectedInvoice.getProductList()!= null && selectedInvoice.getProductList().size() > 0){
            selectedInvoice.getProductList().stream().filter(x -> (x.getPositionNumber() != null) && (x.getPositionNumber().matches("0"))).collect(Collectors.toList()).forEach(x -> {
                Optional<List<Customer>> customerByWorkAddress = customerService.getCustomerByWorkAddress(selectedInvoice.getWorkAddress());
                if(customerByWorkAddress.isPresent() && customerByWorkAddress.get().size() > 0){
                    customerByWorkAddress.get().getFirst().getAddresses().stream().filter(address -> (address.getAddressName() != null) && (address.getAddressName().matches(selectedInvoice.getWorkAddress().getAddressName()))).collect(Collectors.toList()).forEach(address -> {
                        for(int i = 0 ; i < x.getSelectedAmount().intValue(); i++){
                            Device device = new Device();
                            device.setDeviceName(x.getInternalName());
                            device.setCode(x.getProductCode());
                            device.setInvoiceNumber(String.valueOf(selectedInvoice.getFinalInvoiceNumber()));
                            device.setDate(selectedInvoice.getInvoiceDate());
                            String level2 = x.getProductLevel2().getName();
                            if(level2.matches("Separatietechnieken")){
                                device.setType(x.getProductLevel3().getName());
                            }
                            else{
                                device.setType(level2);
                            }
                            String id = deviceService.save(device);

                            if (address.getCoupledDeviceList() == null) {
                                address.setCoupledDeviceList(new ArrayList<>());
                            }

                            address.getCoupledDeviceList().add(id);

                            customerService.save(customerByWorkAddress.get().getFirst());
                        }
                    });
                }
                else{
                    Notification.show("Geen klant gevonden met de naam van het werfadres!");
                }
            });
        }
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
