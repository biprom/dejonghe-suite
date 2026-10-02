package com.adverto.dejonghe.server.services.quote;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.dbservices.QuoteService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.entities.quote.Quote;
import com.adverto.dejonghe.common.implementations.ProductImplementation;
import com.adverto.dejonghe.server.Controllers.PdfController;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuoteServices {

    @Autowired
    QuoteService quoteService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;
    @Autowired
    PdfController pdfController;

    @Value("${rootTemplateQuote}")
    FileSystemResource quoteResourceJRXML;
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

    public Integer getNewQuoteNumber() {
        Optional<Quote> optionalQuote = quoteService.getLastQuote();
        if(!optionalQuote.isEmpty()){
            return Integer.valueOf(optionalQuote.get().getQuoteNumber()+1);
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



    public String generateInvoicePDF(Quote quote){

        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;

        baosAttachement = new ByteArrayOutputStream();

        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        parameters.clear();

        try {
            Address address = quote.getWorkAddress();

            if (address != null
                    && address.getStreet() != null
                    && address.getZip() != null
                    && address.getCity() != null) {

                parameters.put("werfAdres",
                        "Werfadres :\n"
                                + address.getStreet() + "\n"
                                + address.getZip() + " "
                                + address.getCity());
            } else {
                parameters.put("werfAdres", "");
            }
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige servicelocatie te zorgen aub");
        }

        try {
            Address invoiceAddress = quote.getCustomer().getAddresses().stream().filter(x -> (x.getInvoiceAddress() != null) && (x.getInvoiceAddress() == true)).findFirst().get();
            String facturatieAdres = quote.getCustomer().getName();

            if (invoiceAddress != null
                    && invoiceAddress.getStreet() != null
                    && invoiceAddress.getZip() != null
                    && invoiceAddress.getCity() != null) {

                facturatieAdres += "\n"
                        + invoiceAddress.getStreet() + "\n"
                        + invoiceAddress.getZip() + " "
                        + invoiceAddress.getCity();
            }

            parameters.put("facturatieAdres", facturatieAdres);
        }
        catch (Exception e){
            //get first Address in list
            Address invoiceAddress = quote.getCustomer().getAddresses().stream().findFirst().get();
            parameters.put("facturatieAdres", quote.getCustomer().getName() + "\n" +
                    invoiceAddress.getStreet() + "\n" +
                    invoiceAddress.getZip() + " " +
                    invoiceAddress.getCity());
        }

        try {
            parameters.put("btwNummer", vatFormat(quote.getCustomer().getVatNumber()));
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        try {
            parameters.put("factuurNummer", quote.getQuoteNumber().toString());
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige offerte nummer te zorgen aub");
        }

        try {
            parameters.put("datum", quote.getQuoteDate().format(FORMATTER));
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        try {
            parameters.put("vervalDatum", quote.getExpiryDate().format(FORMATTER));
        }
        catch (Exception e){
            Notification.show("Gelieve voor een volledige BTW nummer te zorgen aub");
        }

        //only show dates at the beginning / rest of block set as null
        //round total amount
        LocalDate vorigeDatum = null;

        for (Product item : quote.getProductList()) {
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

        List<Product> totalProductList = quote.getProductList().stream()
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
                                .filter(i -> (products.get(i).getBWorkHour()) && (products.get(i).getDate() != null) && (products.get(i).getDate().equals(uniqueDate)))
                                .reduce((first, second) -> first);

                OptionalInt indexCommentOpt =
                        IntStream.range(0, products.size())
                            .filter(i -> products.get(i).getBComment() != null)
                            .filter(i -> (products.get(i).getBComment()) && (products.get(i).getDate() != null) && (products.get(i).getDate().equals(uniqueDate)))
                            .reduce((first, second) -> second);


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

        Collections.reverse(products);
        ProductImplementation productImplementation = new ProductImplementation(products,quote.getCustomer());

        parameters.put( "ItemDataSource", productImplementation);

        Double totalPriceInvoice = quote.getProductList().stream()
                .filter(product -> product.getTotalPrice() != null)
                .mapToDouble(Product::getTotalPrice)
                .sum();

        parameters.put("netto", totalPriceInvoice);

        if((quote.getCustomer().getVatNumber() != null) && (quote.getCustomer().getVatNumber().contains("BE"))) {
            parameters.put("btwBedrag", quote.getProductList().stream()
                    .filter(product -> product.getTotalPrice() != null)
                    .mapToDouble(x -> (x.getTotalPrice() * x.getVat().getValue()) / 100)
                    .sum());
        }
        else{
            parameters.put("btwBedrag",0.0);
        }

        try {
            jasperReport = JasperCompileManager.compileReport( quoteResourceJRXML.getInputStream() );
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            Notification.show("Kan de JRXML template niet vinden op de server!");
        }

        try {
            jasperPrint  = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(  ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String formatted = String.valueOf(quote.getQuoteNumber()).substring(0, 2) + "-" + String.valueOf(quote.getQuoteNumber()).substring(2);
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

        UI.getCurrent().getPage().open(
                "/pdf/invoice/" + quote.getId(),
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
                generateAttachement(date, quote,attachments.stream().filter(product -> product.getAttachementNumber().equals(date)).collect(Collectors.toList()));
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
        return "quote"+ quote.getQuoteNumber()+".pdf";
    }

    private void generateAttachement(LocalDate datum, Quote quote, List<Product> attachments) {
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
            parameters.put("factuurNummer", quote.getQuoteNumber().toString());
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
        ProductImplementation productImplementation = new ProductImplementation(attachments,quote.getCustomer());
        parameters.put( "ItemDataSource", productImplementation);

        try {
            jasperPrintAttachement  = JasperFillManager.fillReport(jasperReportAttachement, parameters, new JREmptyDataSource(  ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        prints.add(jasperPrintAttachement);
    }


    public Optional<Double> calcTotalNetFromQuote(Quote quote) {
        if(quote.getProductList() != null){
            for (Product item : quote.getProductList()) {
                double roundedTotalPrice = BigDecimal
                        .valueOf(item.getTotalPrice())
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                item.setTotalPrice(roundedTotalPrice);

            }
            Double totalNet = quote.getProductList().stream()
                    .mapToDouble(item -> item.getTotalPrice())
                    .sum();

            return Optional.of(totalNet);
        }
        return Optional.empty();
    }

    public Optional<Double> calcTotalTaxFromQuote(Quote quote) {
        if(quote.getProductList() != null){
            for (Product item : quote.getProductList()) {
                double roundedTotalPrice = BigDecimal
                        .valueOf(item.getTotalPrice())
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                item.setTotalPrice(roundedTotalPrice);

            }
            Double totalTax = quote.getProductList().stream()
                    .mapToDouble(x -> (x.getTotalPrice() * x.getVat().getValue())/100)
                    .sum();

            double roundedTotalTax = BigDecimal
                    .valueOf(totalTax)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            return Optional.of(roundedTotalTax);
        }
        return Optional.empty();
    }

    public String getBase64PdfInvoice() {
        return base64PdfInvoice;
    }

    public String getBase64PdfAttachement() {
        return base64PdfAttachement;
    }
}
