package com.adverto.dejonghe.server.views.dashboard;

import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderGantSeriesItem;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderTime;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.server.customEvents.GetSelectedWorkOrderEvent;
import com.adverto.dejonghe.server.views.workorder.WorkorderView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.FontWeight;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.charts.model.style.Style;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@PageTitle("Dashboard")
@Route("dashboard")
public class DashboardView extends VerticalLayout {

    WorkOrderService workOrderService;
    ApplicationEventPublisher eventPublisher;
    EmployeeService employeeService;

    final Chart chart = new Chart(ChartType.GANTT);
    final Configuration configuration = chart.getConfiguration();

    Navigator navigator;
    YAxis navigatorYAxis;

    GanttSeries series;
    GanttSeries overlapSeries;

    List<WorkOrder>workOrderList;
    List<WorkOrderGantSeriesItem>workOrderGantSeriesItems = new ArrayList<>();

    DashboardDateState dashboardDateState;

    private final DatePicker startDatePicker =
            new DatePicker("Van");

    private final DatePicker endDatePicker =
            new DatePicker("Tot");

    private boolean updatingDatePickers;

    private LocalDate selectedDay;
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;

    @Autowired
    public DashboardView(WorkOrderService workOrderService,
                         ApplicationEventPublisher eventPublisher,
                         EmployeeService employeeService,
                         DashboardDateState dashboardDateState) {

        this.eventPublisher = eventPublisher;
        this.workOrderService = workOrderService;
        this.employeeService = employeeService;
        this.dashboardDateState = dashboardDateState;

        selectedStartDate = dashboardDateState.getStartDate();
        selectedEndDate = dashboardDateState.getEndDate();
        selectedDay = selectedStartDate;

        setUpDemoGanttChart();
    }


    private void setUpDemoGanttChart() {



        // =========================================================
        // DATE RANGE PICKER
        // =========================================================

        startDatePicker.setValue(selectedStartDate);
        endDatePicker.setValue(selectedEndDate);
        endDatePicker.setMin(selectedStartDate);


        // Als startdatum verandert:
        // einddatum automatisch gelijk zetten aan startdatum
        startDatePicker.addValueChangeListener(event -> {

            if (updatingDatePickers) {
                return;
            }

            LocalDate startDate = event.getValue();

            if (startDate == null) {
                return;
            }

            showSingleDay(startDate);
        });


        // Als gebruiker de einddatum expliciet verandert:
        // dan maken we er een range van
        endDatePicker.addValueChangeListener(event -> {

            if (updatingDatePickers) {
                return;
            }

            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = event.getValue();

            if (startDate == null || endDate == null) {
                return;
            }

            selectedDay = startDate;
            selectedStartDate = startDate;
            selectedEndDate = endDate;

            dashboardDateState.setRange(
                    startDate,
                    endDate
            );

            updateChartRange(
                    startDate,
                    endDate
            );
        });


        // =========================================================
        // JOUW BESTAANDE CHART CODE
        // =========================================================

        XAxis xAxis = configuration.getxAxis();

        Labels xLabels = new Labels();

        Style xLabelStyle = new Style();
        xLabelStyle.setFontWeight(FontWeight.BOLD);
        xLabelStyle.setFontSize("14px");

        xLabels.setStyle(xLabelStyle);

        xAxis.setLabels(xLabels);

        xAxis.setTickInterval(3600 * 1000);
        xAxis.setStartOnTick(true);

        ZoneId zone = ZoneId.systemDefault();

        long startOfDay = selectedStartDate
                .atTime(5, 0)
                .atZone(zone)
                .toInstant()
                .toEpochMilli();

        long endOfDay = selectedEndDate
                .atTime(22, 0)
                .atZone(zone)
                .toInstant()
                .toEpochMilli();

        xAxis.setMin(startOfDay);
        xAxis.setMax(endOfDay);

        DateTimeLabelFormats formats = new DateTimeLabelFormats();

        formats.setHour("%H:%M");
        formats.setDay("%e %B");
        formats.setWeek("%e %B");
        formats.setMonth("%B %Y");

        xAxis.setDateTimeLabelFormats(formats);


        YAxis yAxis = configuration.getyAxis();
        yAxis.setUniqueNames(true);
        yAxis.setType(AxisType.CATEGORY);

        Labels labels = new Labels();

        Style labelStyle = new Style();
        labelStyle.setFontSize("18px");
        labelStyle.setFontWeight(FontWeight.BOLD);

        labels.setStyle(labelStyle);

        yAxis.setLabels(labels);

        configuration.getScrollbar().setEnabled(true);

        RangeSelector rangeSelector = new RangeSelector();
        rangeSelector.setSelected(0);

        RangeSelectorButton dayButton = new RangeSelectorButton();
        dayButton.setType(RangeSelectorTimespan.DAY);
        dayButton.setCount(1);
        dayButton.setText("d");

        RangeSelectorButton weekButton = new RangeSelectorButton();
        weekButton.setType(RangeSelectorTimespan.WEEK);
        weekButton.setCount(1);
        weekButton.setText("w");

        RangeSelectorButton monthButton = new RangeSelectorButton();
        monthButton.setType(RangeSelectorTimespan.MONTH);
        monthButton.setCount(1);
        monthButton.setText("m");

        RangeSelectorButton allButton = new RangeSelectorButton();
        allButton.setType(RangeSelectorTimespan.ALL);
        allButton.setText("All");

        rangeSelector.setButtons(
                dayButton,
                weekButton,
                monthButton,
                allButton
        );

        configuration.setRangeSelector(rangeSelector);

        configuration.getRangeSelector().setEnabled(false);
        configuration.getRangeSelector().setSelected(0);

        Time time = new Time();
        time.setUseUTC(false);
        configuration.setTime(time);

        navigator = configuration.getNavigator();
        navigator.setEnabled(false);

        long startTime = LocalDate.now()
                .minusDays(30)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        long endTime = LocalDate.now()
                .plusDays(5)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        navigator.getXAxis().setMin(startTime);
        navigator.getXAxis().setMax(endTime);

        AxisGrid grid = new AxisGrid();
        grid.setEnabled(true);

        grid.setColumns(List.of(
                createProjectColumn()
        ));

        yAxis.setGrid(grid);

        PlotOptionsGantt plotOptionsGantt = new PlotOptionsGantt();

        configuration.setPlotOptions(plotOptionsGantt);

        plotOptionsGantt.setPointPadding(0.0);
        plotOptionsGantt.setGroupPadding(0.0);
        plotOptionsGantt.setBorderWidth(1);
        plotOptionsGantt.setBorderColor(
                new SolidColor("#C49000")
        );

        createProjectDevelopmentSeries();

        PlotOptionsGantt seriesPlotOptions =
                new PlotOptionsGantt();

        var dataLabels = new ArrayList<DataLabels>();

        var assigneeLabel = new DataLabels(true);

        Style style = new Style();
        style.setFontSize("16px");
        style.setColor(SolidColor.BLACK);
        style.setFontWeight(FontWeight.BOLD);

        assigneeLabel.setStyle(style);
        assigneeLabel.setAlign(HorizontalAlign.LEFT);
        assigneeLabel.setInside(true);
        assigneeLabel.setAllowOverlap(false);
        assigneeLabel.setFormat("{point.custom.assignee}");

        dataLabels.add(assigneeLabel);

        seriesPlotOptions.setDataLabels(dataLabels);

        series.setPlotOptions(seriesPlotOptions);

        configuration.addSeries(series);

        if (overlapSeries != null) {

            PlotOptionsGantt overlapPlotOptions =
                    new PlotOptionsGantt();

            overlapPlotOptions.setOpacity(0.5);
            overlapPlotOptions.setBorderWidth(0);

            overlapSeries.setPlotOptions(
                    overlapPlotOptions
            );

            configuration.addSeries(overlapSeries);
        }

        chart.setHeight("1200px");


        chart.addPointClickListener(event -> {

            var ganttSeries = (GanttSeries) event.getSeries();

            var customData = (TaskCustomData)
                    ganttSeries.get(event.getItemIndex()).getCustom();

            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.add(new H3(customData.getFullName()));

            verticalLayout.add(
                    new Paragraph(
                            "Start : " + customData.getStart() +
                                    " - Stop : " + customData.getStop()));

            Dialog dialog = new Dialog();
            dialog.setMaxWidth("none");
            dialog.setMaxHeight("none");
            Button openButtonnew = new Button("Open Werkbon");
            openButtonnew.setWidth("100%");
            openButtonnew.addClickListener(e -> {
                Optional<WorkOrder> selectedWorkOrder = workOrderService.getWorkOrderById(customData.getId());
                if(selectedWorkOrder.isPresent()) {
                    eventPublisher.publishEvent(new GetSelectedWorkOrderEvent(this, selectedWorkOrder.get()));
                }
                else{
                    Notification.show("Er is geen werkbon gevonden met de geselecteerde id.");
                }
                dialog.close();
            });
            dialog.add(verticalLayout);
            dialog.add(openButtonnew);

            dialog.open();
        });

        ChartModel chartConf = configuration.getChart();

        Style styleChart = new Style();
        styleChart.setFontWeight(FontWeight.BOLD);
        styleChart.setFontSize("26px");

        chartConf.setStyle(styleChart);


        // =========================================================
        // TOOLBAR
        // =========================================================

        Button previousDay = new Button(
                "◀",
                event -> shiftDay(-1)
        );

        Button todayButton = new Button(
                "Vandaag",
                event -> showSingleDay(LocalDate.now())
        );

        Button nextDay = new Button(
                "▶",
                event -> shiftDay(1)
        );

        HorizontalLayout toolbar = new HorizontalLayout(
                previousDay,
                todayButton,
                nextDay,
                startDatePicker,
                endDatePicker
        );

        toolbar.setDefaultVerticalComponentAlignment(Alignment.END);
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);

        toolbar.setPadding(true);
        toolbar.setSpacing(true);

        toolbar.getStyle()
                .set("background", "#FFEC99")
                .set("border", "1px solid #C49000")
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 18px rgba(0,0,0,0.08)")
                .set("padding", "14px 20px")
                .set("margin", "8px auto 18px")
                .set("width", "fit-content")
                .set("max-width", "calc(100% - 32px)");


        VerticalLayout layout = new VerticalLayout(
                toolbar,
                chart
        );

        layout.setWidthFull();

        add(layout);

    }

    private void showSingleDay(LocalDate day) {

        if (day == null) {
            return;
        }

        selectedDay = day;
        selectedStartDate = day;
        selectedEndDate = day;

        dashboardDateState.setRange(
                day,
                day
        );

        /*
         * Voorkomt dat setValue() opnieuw de listeners uitvoert.
         */
        updatingDatePickers = true;

        updatingDatePickers = true;

        try {
            startDatePicker.setValue(selectedStartDate);
            endDatePicker.setMin(selectedStartDate);
            endDatePicker.setValue(selectedEndDate);
        } finally {
            updatingDatePickers = false;
        }

        updateChartRange(
                day,
                day
        );
    }

    private void updateSelectedRange(
            LocalDate startDate,
            LocalDate endDate) {

        if (startDate == null) {
            return;
        }

        if (endDate == null) {
            endDate = startDate;
        }

        selectedStartDate = startDate;
        selectedEndDate = endDate;

        selectedDay = startDate;

        updateChartRange(
                startDate,
                endDate
        );
    }

    private void updateChartRange(
            LocalDate startDate,
            LocalDate endDate) {

        ZoneId zone = ZoneId.systemDefault();

        long start = startDate
                .atTime(5, 0)
                .atZone(zone)
                .toInstant()
                .toEpochMilli();

        long end = endDate
                .atTime(22, 0)
                .atZone(zone)
                .toInstant()
                .toEpochMilli();

        XAxis xAxis = configuration.getxAxis();

        xAxis.setTickInterval(60 * 60 * 1000);
        xAxis.setStartOnTick(true);

        xAxis.setGridLineWidth(1);
        xAxis.setGridLineColor(
                new SolidColor("#D6D6D6")
        );

        xAxis.setMin(start);
        xAxis.setMax(end);

        chart.drawChart();
    }

    private String formatTime(Instant instant) {
        return instant
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private XAxis createProjectColumn() {
        XAxis column = new XAxis();
        column.setTitle("");
        final Labels label = new Labels();
        label.setFormat("{point.name}");
        column.setLabels(label);
        return column;
    }

    private GanttSeries createProjectDevelopmentSeries() {

        series = new GanttSeries();
        series.setName("Project 1");

        GanttSeriesItem item;

        workOrderList = workOrderService.getAll().get().stream().filter(workOrder -> ((workOrder.getWorkOrderStatus() == WorkOrderStatus.RUNNING) ||
                (workOrder.getWorkOrderStatus() == WorkOrderStatus.FINISHED)
                || (workOrder.getWorkOrderStatus() == WorkOrderStatus.INVOICED))).toList();
        if(workOrderList != null && workOrderList.size() > 0) {
            for(WorkOrder workOrder : workOrderList) {
                if(workOrder.getMasterEmployeeTeam1() != null){
                    if(workOrder.getWorkOrderHeaderList() != null && workOrder.getWorkOrderHeaderList().size() > 0){
                        if((workOrder.getWorkOrderHeaderList().get(0) != null)){
                            if((workOrder.getWorkOrderHeaderList().get(0).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(0).getWorkOrderTimeList().size() > 0)){
                                for(WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(0).getWorkOrderTimeList()) {
                                    generateWorkOrderGantSeriesItem(workOrder.getId(),
                                            workOrder.getWorkOrderStatus(),
                                            workOrder.getMasterEmployeeTeam1().getFirstName() + " " + workOrder.getMasterEmployeeTeam1().getLastName(),
                                            workOrder.getMasterEmployeeTeam1().getId(),
                                            workOrder.getWorkAddress().getAddressName(),
                                            workOrder.getWorkDateTime().toLocalDate(),
                                            workOrderTime.getTimeUp() != null
                                                    ? workOrderTime.getTimeUp()
                                                    : workOrderTime.getTimeStart(),
                                            workOrderTime.getTimeDown() != null
                                                    ? workOrderTime.getTimeDown()
                                                    : workOrderTime.getTimeStop());
                                }
                            }
                        }
                    }

                    if((workOrder.getExtraEmployeesTeam1() != null) && (workOrder.getExtraEmployeesTeam1().size() > 0)){
                        for(Employee employee : workOrder.getExtraEmployeesTeam1()) {
                            if((workOrder.getWorkOrderHeaderList().get(0).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(0).getWorkOrderTimeList().size() > 0)){
                                for(WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(0).getWorkOrderTimeList()) {
                                    generateWorkOrderGantSeriesItem(workOrder.getId(),
                                            workOrder.getWorkOrderStatus(),
                                            employee.getFirstName() + " " + employee.getLastName(),
                                            employee.getId(),
                                            workOrder.getWorkAddress().getAddressName(),
                                            workOrder.getWorkDateTime().toLocalDate(),
                                            workOrderTime.getTimeUp() != null
                                                    ? workOrderTime.getTimeUp()
                                                    : workOrderTime.getTimeStart(),
                                            workOrderTime.getTimeDown() != null
                                                    ? workOrderTime.getTimeDown()
                                                    : workOrderTime.getTimeStop());
                                }
                            }
                        }
                    }

                }

                if(workOrder.getMasterEmployeeTeam2() != null){
                    if(workOrder.getWorkOrderHeaderList() != null && workOrder.getWorkOrderHeaderList().size() > 0){
                        if((workOrder.getWorkOrderHeaderList().get(1) != null)){
                            if((workOrder.getWorkOrderHeaderList().get(1).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(1).getWorkOrderTimeList().size() > 0)){
                                for(WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(1).getWorkOrderTimeList()) {
                                    generateWorkOrderGantSeriesItem(workOrder.getId(),
                                            workOrder.getWorkOrderStatus(),
                                            workOrder.getMasterEmployeeTeam2().getFirstName() + " " + workOrder.getMasterEmployeeTeam2().getLastName(),
                                            workOrder.getMasterEmployeeTeam2().getId(),
                                            workOrder.getWorkAddress().getAddressName(),
                                            workOrder.getWorkDateTime().toLocalDate(),
                                            workOrderTime.getTimeUp() != null
                                                    ? workOrderTime.getTimeUp()
                                                    : workOrderTime.getTimeStart(),
                                            workOrderTime.getTimeDown() != null
                                                    ? workOrderTime.getTimeDown()
                                                    : workOrderTime.getTimeStop());
                                }
                            }
                        }
                    }

                    if((workOrder.getExtraEmployeesTeam2() != null) && (workOrder.getExtraEmployeesTeam2().size() > 0)){
                        for(Employee employee : workOrder.getExtraEmployeesTeam2()) {
                            if((workOrder.getWorkOrderHeaderList().get(1).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(1).getWorkOrderTimeList().size() > 0)){
                                for(WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(1).getWorkOrderTimeList()) {
                                    generateWorkOrderGantSeriesItem(workOrder.getId(),
                                            workOrder.getWorkOrderStatus(),
                                            employee.getFirstName() + " " + employee.getLastName(),
                                            employee.getId(),
                                            workOrder.getWorkAddress().getAddressName(),
                                            workOrder.getWorkDateTime().toLocalDate(),
                                            workOrderTime.getTimeUp() != null
                                                    ? workOrderTime.getTimeUp()
                                                    : workOrderTime.getTimeStart(),
                                            workOrderTime.getTimeDown() != null
                                                    ? workOrderTime.getTimeDown()
                                                    : workOrderTime.getTimeStop());
                                }
                            }
                        }
                    }

                    if(workOrder.getMasterEmployeeTeam3() != null) {
                        if (workOrder.getWorkOrderHeaderList() != null && workOrder.getWorkOrderHeaderList().size() > 0) {
                            if ((workOrder.getWorkOrderHeaderList().get(2) != null)) {
                                if ((workOrder.getWorkOrderHeaderList().get(2).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(2).getWorkOrderTimeList().size() > 0)) {
                                    for (WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(2).getWorkOrderTimeList()) {
                                        generateWorkOrderGantSeriesItem(workOrder.getId(),
                                                workOrder.getWorkOrderStatus(),
                                                workOrder.getMasterEmployeeTeam3().getFirstName() + " " + workOrder.getMasterEmployeeTeam3().getLastName(),
                                                workOrder.getMasterEmployeeTeam3().getId(),
                                                workOrder.getWorkAddress().getAddressName(),
                                                workOrder.getWorkDateTime().toLocalDate(),
                                                workOrderTime.getTimeUp() != null
                                                        ? workOrderTime.getTimeUp()
                                                        : workOrderTime.getTimeStart(),
                                                workOrderTime.getTimeDown() != null
                                                        ? workOrderTime.getTimeDown()
                                                        : workOrderTime.getTimeStop());
                                    }
                                }
                            }
                        }

                        if ((workOrder.getExtraEmployeesTeam3() != null) && (workOrder.getExtraEmployeesTeam3().size() > 0)) {
                            for (Employee employee : workOrder.getExtraEmployeesTeam3()) {
                                if ((workOrder.getWorkOrderHeaderList().get(2).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(2).getWorkOrderTimeList().size() > 0)) {
                                    for (WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(2).getWorkOrderTimeList()) {
                                        generateWorkOrderGantSeriesItem(workOrder.getId(),
                                                workOrder.getWorkOrderStatus(),
                                                employee.getFirstName() + " " + employee.getLastName(),
                                                employee.getId(),
                                                workOrder.getWorkAddress().getAddressName(),
                                                workOrder.getWorkDateTime().toLocalDate(),
                                                workOrderTime.getTimeUp() != null
                                                        ? workOrderTime.getTimeUp()
                                                        : workOrderTime.getTimeStart(),
                                                workOrderTime.getTimeDown() != null
                                                        ? workOrderTime.getTimeDown()
                                                        : workOrderTime.getTimeStop());
                                    }
                                }
                            }
                        }
                    }

                    if(workOrder.getMasterEmployeeTeam4() != null) {
                        if (workOrder.getWorkOrderHeaderList() != null && workOrder.getWorkOrderHeaderList().size() > 0) {
                            if ((workOrder.getWorkOrderHeaderList().get(3) != null)) {
                                if ((workOrder.getWorkOrderHeaderList().get(3).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(3).getWorkOrderTimeList().size() > 0)) {
                                    for (WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(3).getWorkOrderTimeList()) {
                                        generateWorkOrderGantSeriesItem(workOrder.getId(),
                                                workOrder.getWorkOrderStatus(),
                                                workOrder.getMasterEmployeeTeam4().getFirstName() + " " + workOrder.getMasterEmployeeTeam4().getLastName(),
                                                workOrder.getMasterEmployeeTeam4().getId(),
                                                workOrder.getWorkAddress().getAddressName(),
                                                workOrder.getWorkDateTime().toLocalDate(),
                                                workOrderTime.getTimeUp() != null
                                                        ? workOrderTime.getTimeUp()
                                                        : workOrderTime.getTimeStart(),
                                                workOrderTime.getTimeDown() != null
                                                        ? workOrderTime.getTimeDown()
                                                        : workOrderTime.getTimeStop());
                                    }
                                }
                            }
                        }

                        if ((workOrder.getExtraEmployeesTeam4() != null) && (workOrder.getExtraEmployeesTeam4().size() > 0)) {
                            for (Employee employee : workOrder.getExtraEmployeesTeam4()) {
                                if ((workOrder.getWorkOrderHeaderList().get(3).getWorkOrderTimeList() != null) && (workOrder.getWorkOrderHeaderList().get(3).getWorkOrderTimeList().size() > 0)) {
                                    for (WorkOrderTime workOrderTime : workOrder.getWorkOrderHeaderList().get(3).getWorkOrderTimeList()) {
                                        generateWorkOrderGantSeriesItem(workOrder.getId(),
                                                workOrder.getWorkOrderStatus(),
                                                employee.getFirstName() + " " + employee.getLastName(),
                                                employee.getId(),
                                                workOrder.getWorkAddress().getAddressName(),
                                                workOrder.getWorkDateTime().toLocalDate(),
                                                workOrderTime.getTimeUp() != null
                                                        ? workOrderTime.getTimeUp()
                                                        : workOrderTime.getTimeStart(),
                                                workOrderTime.getTimeDown() != null
                                                        ? workOrderTime.getTimeDown()
                                                        : workOrderTime.getTimeStop());
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }

        workOrderGantSeriesItems = workOrderGantSeriesItems.stream()
                .filter(x -> x.getPrio() > 0)
                .toList();

        workOrderGantSeriesItems.forEach(x ->
                x.setYPosition(x.getPrio() - 1)
        );


        for (WorkOrderGantSeriesItem workOrderGantSeriesItem : workOrderGantSeriesItems) {

            item = new GanttSeriesItem(
                    workOrderGantSeriesItem.getAbbreviationName(),
                    workOrderGantSeriesItem.getStart(),
                    workOrderGantSeriesItem.getEnd());

            item.setY(workOrderGantSeriesItem.getYPosition());

            item.setCustom(
                    new TaskCustomData(
                            workOrderGantSeriesItem.getWorkOrderId(),
                            fitTextToDuration(
                                    workOrderGantSeriesItem.getWorkAddressName(),
                                    workOrderGantSeriesItem),
                            workOrderGantSeriesItem.getStatus() + workOrderGantSeriesItem.getWorkAddressName(),
                            formatTime(workOrderGantSeriesItem.getStart()),
                            formatTime(workOrderGantSeriesItem.getEnd())));

            item.setColor(new SolidColor("#FFEC99"));

            series.add(item);
        }

        overlapSeries = createOverlapOverlaySeries();

        return series;

    }

    private String fitTextToDuration(
            String text,
            WorkOrderGantSeriesItem item) {

        long minutes =
                java.time.Duration.between(
                                item.getStart(),
                                item.getEnd())
                        .toMinutes();

        int maxChars = (int) (minutes / 10);

        maxChars = Math.max(4, Math.min(maxChars, 30));

        if (text.length() <= maxChars) {
            return text;
        }

        return text.substring(0, maxChars - 3) + "...";
    }

    private GanttSeries createOverlapOverlaySeries() {

        GanttSeries overlapSeries = new GanttSeries();
        overlapSeries.setName("Overlappingen");

        for (OverlapSegment segment : calculateOverlapSegments()) {

            GanttSeriesItem item = new GanttSeriesItem(
                    segment.customerNames,
                    segment.start,
                    segment.end);

            item.setY(segment.yPosition);
            item.setColor(new SolidColor("#FFA94D"));
            item.setCustom(new TaskCustomData("", segment.customerNames,"", "", ""));

            overlapSeries.add(item);
        }

        return overlapSeries;
    }

    private List<OverlapSegment> calculateOverlapSegments() {

        List<OverlapSegment> result = new ArrayList<>();

        Map<String, List<WorkOrderGantSeriesItem>> itemsByEmployee =
                workOrderGantSeriesItems.stream()
                        .collect(Collectors.groupingBy(
                                WorkOrderGantSeriesItem::getAbbreviationName));

        for (Map.Entry<String, List<WorkOrderGantSeriesItem>> entry : itemsByEmployee.entrySet()) {

            String employeeName = entry.getKey();
            List<WorkOrderGantSeriesItem> items = entry.getValue();

            if (items.size() < 2) {
                continue;
            }

            Integer yPosition = items.get(0).getYPosition();
            List<OverlapSegment> intersections = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {
                WorkOrderGantSeriesItem first = items.get(i);

                for (int j = i + 1; j < items.size(); j++) {
                    WorkOrderGantSeriesItem second = items.get(j);

                    boolean overlaps =
                            first.getStart().isBefore(second.getEnd())
                                    && first.getEnd().isAfter(second.getStart());

                    if (!overlaps) {
                        continue;
                    }

                    java.time.Instant overlapStart =
                            first.getStart().isAfter(second.getStart())
                                    ? first.getStart()
                                    : second.getStart();

                    java.time.Instant overlapEnd =
                            first.getEnd().isBefore(second.getEnd())
                                    ? first.getEnd()
                                    : second.getEnd();

                    if (overlapStart.isBefore(overlapEnd)) {
                        intersections.add(
                                new OverlapSegment(
                                        first.getWorkAddressName() + " - " + second.getWorkAddressName(),
                                        overlapStart,
                                        overlapEnd,
                                        yPosition));
                    }
                }
            }

            result.addAll(intersections);
        }

        return result;
    }


    private static class OverlapSegment {
        private final String customerNames;
        private final java.time.Instant start;
        private final java.time.Instant end;
        private final Integer yPosition;

        private OverlapSegment(
                String customerNames,
                java.time.Instant start,
                java.time.Instant end,
                Integer yPosition) {
            this.customerNames = customerNames;
            this.start = start;
            this.end = end;
            this.yPosition = yPosition;
        }
    }

    private void generateWorkOrderGantSeriesItem(String id, WorkOrderStatus status, String abbreviation, String employeeId, String addressName,LocalDate date, LocalTime timeUp, LocalTime timeDown) {
        try{
            if(timeUp != null && timeDown != null){
                WorkOrderGantSeriesItem workOrderGantSeriesItem = new WorkOrderGantSeriesItem();
                workOrderGantSeriesItem.setWorkOrderId(id);
                Optional<Employee> optEmployee = employeeService.findById(employeeId);
                if (optEmployee.isPresent()) {
                    Employee employee = optEmployee.get();
                    workOrderGantSeriesItem.setPrio(employee.getPriority());
                }
                else{
                    workOrderGantSeriesItem.setPrio(0);
                }
                workOrderGantSeriesItem.setAbbreviationName(abbreviation);
                workOrderGantSeriesItem.setStatus(status.getAbbr());
                workOrderGantSeriesItem.setWorkAddressName(addressName);
                workOrderGantSeriesItem.setStart(timeUp
                        .atDate(date)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                workOrderGantSeriesItem.setEnd(timeDown
                        .atDate(date)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                workOrderGantSeriesItems.add(workOrderGantSeriesItem);
            }
        }
        catch (Exception e) {
            System.out.println("Volgende werkbon kon niet worden getoond : " + e.getMessage());
        }
    }

    private void shiftDay(int days) {

        LocalDate currentStartDate =
                selectedStartDate != null
                        ? selectedStartDate
                        : LocalDate.now();

        LocalDate newDate =
                currentStartDate.plusDays(days);

        showSingleDay(newDate);
    }



    @SuppressWarnings("unused")
    static class TaskCustomData extends AbstractConfigurationObject {

        private String id;
        private String assignee;
        private String fullName;
        private String start;
        private String stop;

        public TaskCustomData(String id,
                              String assignee,
                              String fullName,
                              String start,
                              String stop) {
            this.id = id;
            this.assignee = assignee;
            this.fullName = fullName;
            this.start = start;
            this.stop = stop;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getStop() {
            return stop;
        }

        public void setStop(String stop) {
            this.stop = stop;
        }
    }
}
