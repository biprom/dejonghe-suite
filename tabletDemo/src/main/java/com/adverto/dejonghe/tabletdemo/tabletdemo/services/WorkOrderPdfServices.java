package com.adverto.dejonghe.tabletdemo.tabletdemo.services;

import com.adverto.dejonghe.common.entities.WorkOrder.*;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkLocation;
import com.adverto.dejonghe.common.implementations.ProductImplementation;
import com.adverto.dejonghe.common.implementations.TeamImplementation;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WorkOrderPdfServices {

    @Value("${rootTemplateWorkorder}")
    FileSystemResource workOrderResourceJRXML;

    @Value( "${rootFolder}" )
    private String rootFolder;

//    @Autowired
//    PdfController pdfController;

    ByteArrayOutputStream baosAttachement;
    Map<String, Object> parameters = new HashMap<>();

    String technicians1;
    String transport1;
    String roadTax1;
    String comment1;
    List<WorkOrderTimePdfDTO> workHourTime1;

    String technicians2;
    String transport2;
    String roadTax2;
    String comment2;
    List<WorkOrderTimePdfDTO> workHourTime2;

    String technicians3;
    String transport3;
    String roadTax3;
    String comment3;
    List<WorkOrderTimePdfDTO> workHourTime3;

    String technicians4;
    String transport4;
    String roadTax4;
    String comment4;
    List<WorkOrderTimePdfDTO> workHourTime4;

    byte[] invoiceBytes;

    public WorkOrderPdfServices() {

    }

    public String generateWorkOrderPDF(WorkOrder workOrder){

        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;

        baosAttachement = new ByteArrayOutputStream();

        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        parameters.clear();

        try {
            parameters.put("date", workOrder.getWorkDateTime().format(FORMATTER));
        }
        catch (Exception e){
            parameters.put("date", null);
        }

        try {
            if((workOrder.getWorkAddress() != null)){
                parameters.put("workAddress", "Werfadres : " + "\n" +
                        workOrder.getWorkAddress().getStreet() + "\n" +
                        workOrder.getWorkAddress().getZip() + " " +
                        workOrder.getWorkAddress().getCity());
            }
        }
        catch (Exception e){
            parameters.put("workAddress", null);
        }

        try {
            parameters.put("location", workOrder.getWorkLocation().getDiscription());
        }
        catch (Exception e){
            parameters.put("location", null);
        }

        try {
            parameters.put("showWorkHours", true);
        }
        catch (Exception e){
            parameters.put("showWorkHours", true);
        }

        try {
            if(workOrder.getWorkLocation().equals(WorkLocation.WORKPLACE)){
                parameters.put("showTransport", false);
            }
            else{
                parameters.put("showTransport", true);
            }
        }
        catch (Exception e){
            parameters.put("showTransport", false);
        }

        try {
            if((workOrder.getMasterEmployeeTeam1() != null)){
                parameters.put("showTeam1", true);
            }
            else{
                parameters.put("showTeam1", false);
            }
        }
        catch (Exception e){
            parameters.put("showTeam1", false);
        }

        try {
            if((workOrder.getMasterEmployeeTeam2() != null)){
                parameters.put("showTeam2", true);
            }
            else{
                parameters.put("showTeam2", false);
            }
        }
        catch (Exception e){
            parameters.put("showTeam2", false);
        }

        try {
            if((workOrder.getMasterEmployeeTeam3() != null)){
                parameters.put("showTeam3", true);
            }
            else{
                parameters.put("showTeam3", false);
            }
        }
        catch (Exception e){
            parameters.put("showTeam3", false);
        }

        try {
            if((workOrder.getMasterEmployeeTeam4() != null)){
                parameters.put("showTeam4", true);
            }
            else{
                parameters.put("showTeam4", false);
            }
        }
        catch (Exception e){
            parameters.put("showTeam4", false);
        }

        TeamImplementation teamImplementation1 = new TeamImplementation(generateTeam1List(workOrder));
        parameters.put( "TeamDataSource1", teamImplementation1);

        TeamImplementation teamImplementation2 = new TeamImplementation(generateTeam2List(workOrder));
        parameters.put( "TeamDataSource2", teamImplementation2);

        TeamImplementation teamImplementation3 = new TeamImplementation(generateTeam3List(workOrder));
        parameters.put( "TeamDataSource3", teamImplementation3);

        TeamImplementation teamImplementation4 = new TeamImplementation(generateTeam4List(workOrder));
        parameters.put( "TeamDataSource4", teamImplementation4);

        Collections.reverse(workOrder.getProductList());
        ProductImplementation productImplementation = new ProductImplementation(workOrder.getProductList());

        parameters.put( "ItemDataSource", productImplementation);

        try {
            jasperReport = JasperCompileManager.compileReport( workOrderResourceJRXML.getInputStream() );
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {

        }

        try {
            jasperPrint  = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(  ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String formatted = String.valueOf(workOrder.getWorkDateTime().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) );
        String exportName = rootFolder + ""+ formatted+".pdf";

        try {
            invoiceBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            Files.write(Paths.get(exportName), invoiceBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        return ""+ formatted+".pdf";
    }

    private List<Team> generateTeam1List(WorkOrder workOrder) {
        if((workOrder.getMasterEmployeeTeam1() != null)){
            Team team1 = new Team();

            technicians1 = workOrder.getMasterEmployeeTeam1().getAbbreviation();
            if((workOrder.getExtraEmployeesTeam1() != null) && (workOrder.getExtraEmployeesTeam1().size() > 0)){
                workOrder.getExtraEmployeesTeam1().stream().forEach(x -> technicians1 = technicians1 + x.getAbbreviation());
            }

            if(workOrder.getWorkOrderHeaderList().get(0).getDescription() != null){
                comment1 = workOrder.getWorkOrderHeaderList().get(0).getDescription();
            }

            transport1 = workOrder.getWorkOrderHeaderList().get(0).getFleet().getDiscription();
            Double roadTax =
                    Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(0).getRoadTax()).orElse(0.0)
                            + Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(0).getTunnelTax()).orElse(0.0);

            roadTax1 = String.valueOf(roadTax);


            workHourTime1 = getWorkOrderTimeDtoFor(workOrder.getWorkOrderHeaderList().get(0).getWorkOrderTimeList());

            team1.setTechnicians(technicians1);
            team1.setVihicle(transport1);
            team1.setRoadTunnelTax(roadTax1);
            team1.setWorkHours(workHourTime1);
            team1.setComment(comment1);
            return Arrays.asList(team1);
        }
        return Arrays.asList();
    }

    private List<Team> generateTeam2List(WorkOrder workOrder) {
        if((workOrder.getMasterEmployeeTeam2() != null)){
            Team team2 = new Team();

            technicians2 = workOrder.getMasterEmployeeTeam2().getAbbreviation();
            if((workOrder.getExtraEmployeesTeam2() != null) && (workOrder.getExtraEmployeesTeam2().size() > 0)){
                workOrder.getExtraEmployeesTeam2().stream().forEach(x -> technicians2 = technicians2 + x.getAbbreviation());
            }

            if(workOrder.getWorkOrderHeaderList().get(1).getDescription() != null){
                comment2 = workOrder.getWorkOrderHeaderList().get(1).getDescription();
            }

            transport2 = workOrder.getWorkOrderHeaderList().get(1).getFleet().getDiscription();
            Double roadTax =
                    Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(1).getRoadTax()).orElse(0.0)
                            + Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(1).getTunnelTax()).orElse(0.0);

            roadTax2 = String.valueOf(roadTax);

            workHourTime2 = getWorkOrderTimeDtoFor(workOrder.getWorkOrderHeaderList().get(1).getWorkOrderTimeList());

            team2.setTechnicians(technicians2);
            team2.setVihicle(transport2);
            team2.setRoadTunnelTax(roadTax2);
            team2.setWorkHours(workHourTime2);
            team2.setComment(comment2);
            return Arrays.asList(team2);
        }
        return Arrays.asList();
    }

    private List<Team> generateTeam3List(WorkOrder workOrder) {
        if((workOrder.getMasterEmployeeTeam3() != null)){
            Team team3 = new Team();

            technicians3 = workOrder.getMasterEmployeeTeam3().getAbbreviation();
            if((workOrder.getExtraEmployeesTeam3() != null) && (workOrder.getExtraEmployeesTeam3().size() > 0)){
                workOrder.getExtraEmployeesTeam3().stream().forEach(x -> technicians3 = technicians3 + x.getAbbreviation());
            }

            if(workOrder.getWorkOrderHeaderList().get(2).getDescription() != null){
                comment3 = workOrder.getWorkOrderHeaderList().get(2).getDescription();
            }

            transport3 = workOrder.getWorkOrderHeaderList().get(2).getFleet().getDiscription();
            Double roadTax =
                    Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(2).getRoadTax()).orElse(0.0)
                            + Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(2).getTunnelTax()).orElse(0.0);

            roadTax3 = String.valueOf(roadTax);

            workHourTime3 = getWorkOrderTimeDtoFor(workOrder.getWorkOrderHeaderList().get(2).getWorkOrderTimeList());

            team3.setTechnicians(technicians3);
            team3.setVihicle(transport3);
            team3.setRoadTunnelTax(roadTax3);
            team3.setWorkHours(workHourTime3);
            team3.setComment(comment3);
            return Arrays.asList(team3);
        }
        return Arrays.asList();
    }

    private List<Team> generateTeam4List(WorkOrder workOrder) {
        if((workOrder.getMasterEmployeeTeam4() != null)){
            Team team4 = new Team();

            technicians4 = workOrder.getMasterEmployeeTeam4().getAbbreviation();
            if((workOrder.getExtraEmployeesTeam4() != null) && (workOrder.getExtraEmployeesTeam4().size() > 0)){
                workOrder.getExtraEmployeesTeam4().stream().forEach(x -> technicians4 = technicians4 + x.getAbbreviation());
            }

            if(workOrder.getWorkOrderHeaderList().get(3).getDescription() != null){
                comment4 = workOrder.getWorkOrderHeaderList().get(3).getDescription();
            }

            transport4 = workOrder.getWorkOrderHeaderList().get(3).getFleet().getDiscription();
            Double roadTax =
                    Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(3).getRoadTax()).orElse(0.0)
                            + Optional.ofNullable(workOrder.getWorkOrderHeaderList().get(3).getTunnelTax()).orElse(0.0);

            roadTax4 = String.valueOf(roadTax);

            workHourTime4 = getWorkOrderTimeDtoFor(workOrder.getWorkOrderHeaderList().get(3).getWorkOrderTimeList());

            team4.setTechnicians(technicians4);
            team4.setVihicle(transport4);
            team4.setRoadTunnelTax(roadTax4);
            team4.setWorkHours(workHourTime4);
            team4.setComment(comment4);
            return Arrays.asList(team4);
        }
        return Arrays.asList();
    }

    private List<WorkOrderTimePdfDTO> getWorkOrderTimeDtoFor(List<WorkOrderTime> workOrderTimeList) {
        List<WorkOrderTimePdfDTO>workOrderTimePdfDTOList = new ArrayList<>();
        for(int i = 0; i < workOrderTimeList.size(); i++){
            WorkOrderTimePdfDTO workOrderTimePdfDTO = new WorkOrderTimePdfDTO();
            if(workOrderTimeList.get(i).getTimeUp() != null){
                workOrderTimePdfDTO.setStart(workOrderTimeList.get(i).getTimeUp().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            else{
                workOrderTimePdfDTO.setStart(workOrderTimeList.get(i).getTimeStart().format(DateTimeFormatter.ofPattern("HH:mm")));
            }

            if(workOrderTimeList.get(i).getTimeStop() != null){
                workOrderTimePdfDTO.setEnd(workOrderTimeList.get(i).getTimeStop().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            else{
                workOrderTimePdfDTO.setEnd(workOrderTimeList.get(i).getTimeDown().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            if(workOrderTimeList.get(i).getPauze() != null){
                workOrderTimePdfDTO.setPause(String.valueOf(workOrderTimeList.get(i).getPauze()));
            }
            workOrderTimePdfDTOList.add(workOrderTimePdfDTO);
        }
       return workOrderTimePdfDTOList;
    }
}
