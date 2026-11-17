package com.adverto.dejonghe.server.views.dashboard;

import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.entities.employee.DayPart;
import com.adverto.dejonghe.common.entities.employee.EmployeeAbsence;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderGantSeriesItem;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderTime;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.employee.WorkDayAvailability;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.server.customEvents.GetSelectedWorkOrderEvent;
import com.adverto.dejonghe.server.services.employee.EmployeeAbsenceService;
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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
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
    private final EmployeeAbsenceService absenceService;
    private final EmployeeAbsenceDialog absenceDialog;

    final Chart chart = new Chart(ChartType.GANTT);
    final Configuration configuration = chart.getConfiguration();

    Navigator navigator;
    YAxis navigatorYAxis;

    GanttSeries series;
    GanttSeries overlapSeries;
    GanttSeries unavailableSeries;
    GanttSeries absenceSeries;
    private XAxis employeeNameColumn;

    // Grenzen van de zichtbare werkdag in dit dashboard.
    // Voormiddag = 05:00-12:00, namiddag = 12:00-22:00.
    private static final LocalTime VISIBLE_DAY_START = LocalTime.of(5, 0);
    private static final LocalTime AFTERNOON_START = LocalTime.NOON;
    private static final LocalTime VISIBLE_DAY_END = LocalTime.of(22, 0);
    private static final SolidColor UNAVAILABLE_COLOR = new SolidColor("#CDD2D8");
    private static final SolidColor VACATION_COLOR = new SolidColor("#B6A1DF");
    private static final SolidColor SICK_COLOR = new SolidColor("#E7A4A0");
    private static final SolidColor OTHER_ABSENCE_COLOR = new SolidColor("#9DBDD9");

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
                         DashboardDateState dashboardDateState,
                         EmployeeAbsenceService absenceService) {

        this.eventPublisher = eventPublisher;
        this.workOrderService = workOrderService;
        this.employeeService = employeeService;
        this.dashboardDateState = dashboardDateState;
        this.absenceService = absenceService;
        this.absenceDialog = new EmployeeAbsenceDialog(
                employeeService, absenceService, this::refreshAbsenceChart);

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
        xLabels.setAlign(HorizontalAlign.LEFT);
        xLabels.setX(3);
        xAxis.setLabels(xLabels);

        xAxis.setTickInterval(3600 * 1000);
        xAxis.setStartOnTick(true);

        ZoneId zone = ZoneId.systemDefault();

        long startOfDay = selectedStartDate
                .atTime(VISIBLE_DAY_START)
                .atZone(zone)
                .toInstant()
                .toEpochMilli();

        long endOfDay = selectedEndDate
                .atTime(VISIBLE_DAY_END)
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

        employeeNameColumn = createProjectColumn();
        grid.setColumns(List.of(employeeNameColumn));

        yAxis.setGrid(grid);

        PlotOptionsGantt plotOptionsGantt = new PlotOptionsGantt();

        configuration.setPlotOptions(plotOptionsGantt);

        // Alle series gebruiken dezelfde volledige personeelrij.
        plotOptionsGantt.setGrouping(false);
        plotOptionsGantt.setPointPadding(0.0);
        plotOptionsGantt.setGroupPadding(0.0);
        plotOptionsGantt.setBorderWidth(1);
        plotOptionsGantt.setBorderColor(
                new SolidColor("#C49000")
        );

        createProjectDevelopmentSeries();

        // Eerste series = achtergrondblokken voor vaste vrije dagen/dagdelen.
        // Werkbonnen en overlappingskleuren worden er daarna bovenop getekend.
        unavailableSeries = new GanttSeries("Niet aanwezig");
        PlotOptionsGantt unavailableOptions = new PlotOptionsGantt();
        unavailableOptions.setGrouping(false);
        unavailableOptions.setBorderWidth(0);
        unavailableOptions.setPointPadding(0.0);
        unavailableOptions.setGroupPadding(0.0);
        unavailableOptions.setEnableMouseTracking(false);
        unavailableSeries.setPlotOptions(unavailableOptions);

        refreshUnavailablePeriods(selectedStartDate, selectedEndDate);
        configuration.addSeries(unavailableSeries);

        // Gekleurde, datumgebonden afwezigheden komen boven grijs,
        // maar onder de bestaande werkbonnen en oranje overlappingen.
        absenceSeries = new GanttSeries("Verlof en ziekte");
        PlotOptionsGantt absenceOptions = new PlotOptionsGantt();
        absenceOptions.setGrouping(false);
        absenceOptions.setPointPadding(0.0);
        absenceOptions.setGroupPadding(0.0);
        absenceOptions.setBorderWidth(0);
        DataLabels absenceLabels = new DataLabels(true);
        absenceLabels.setFormat("{point.custom.assignee}");
        absenceLabels.setInside(true);
        absenceLabels.setAlign(HorizontalAlign.CENTER);
        Style absenceLabelStyle = new Style();
        absenceLabelStyle.setColor(SolidColor.BLACK);
        absenceLabelStyle.setFontSize("13px");
        absenceLabels.setStyle(absenceLabelStyle);
        ArrayList<DataLabels> absenceDataLabels = new ArrayList<>();
        absenceDataLabels.add(absenceLabels);
        absenceOptions.setDataLabels(absenceDataLabels);
        absenceSeries.setPlotOptions(absenceOptions);
        refreshAbsences(selectedStartDate, selectedEndDate);
        configuration.addSeries(absenceSeries);

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

            // Verlof/ziekte is klikbaar om te bewerken of te verwijderen.
            if (event.getSeries() == absenceSeries) {
                if (event.getItemIndex() >= 0
                        && event.getItemIndex() < absenceSeries.size()) {
                    Object custom = absenceSeries.get(event.getItemIndex()).getCustom();
                    if (custom instanceof AbsenceChartData absenceData) {
                        absenceService.findById(absenceData.getId())
                                .ifPresent(absenceDialog::openEdit);
                    }
                }
                return;
            }

            // Een grijs vak is geen werkbon. Bescherm ook tegen clicks op
            // andere series zonder geldig workOrderId (zoals overlappingen).
            if (!(event.getSeries() instanceof GanttSeries ganttSeries)
                    || ganttSeries == unavailableSeries
                    || event.getItemIndex() < 0
                    || event.getItemIndex() >= ganttSeries.size()) {
                return;
            }

            Object custom = ganttSeries.get(event.getItemIndex()).getCustom();
            if (!(custom instanceof TaskCustomData customData)
                    || customData.getId() == null
                    || customData.getId().isBlank()) {
                return;
            }

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

        Button addAbsenceButton = new Button(
                "+ Afwezigheid", event -> absenceDialog.openNew(selectedStartDate));

        HorizontalLayout toolbar = new HorizontalLayout(
                previousDay,
                todayButton,
                nextDay,
                addAbsenceButton,
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


        HorizontalLayout legend = new HorizontalLayout(
                createLegendBadge("Vaste vrije dag", "#CDD2D8"),
                createLegendBadge("Verlof", "#B6A1DF"),
                createLegendBadge("Ziekte", "#E7A4A0"),
                createLegendBadge("Andere afwezigheid", "#9DBDD9"),
                createLegendBadge("Werkbon", "#FFEC99"),
                createLegendBadge("Overlapping", "#FFA94D")
        );
        legend.setAlignItems(Alignment.CENTER);
        legend.getStyle().set("flex-wrap", "wrap");

        VerticalLayout layout = new VerticalLayout(
                toolbar,
                legend,
                chart
        );

        layout.setWidthFull();

        add(layout);

    }

    private Span createLegendBadge(String title, String color) {
        Span badge = new Span(title);
        badge.getStyle()
                .set("background-color", color)
                .set("color", "#252525")
                .set("padding", "5px 10px")
                .set("border-radius", "6px")
                .set("font-size", "12px")
                .set("font-weight", "600");
        return badge;
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
                .atTime(VISIBLE_DAY_START)
                .atZone(zone)
                .toInstant()
                .toEpochMilli();

        long end = endDate
                .atTime(VISIBLE_DAY_END)
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

        // De weekplanning volgt altijd de datum- of periodekeuze.
        refreshUnavailablePeriods(startDate, endDate);
        refreshAbsences(startDate, endDate);
        chart.drawChart();
    }

    private String formatTime(Instant instant) {
        return instant
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Tekent de terugkerende afwezigheden op de juiste Gantt-rij.
     * De vaste planning blijft read-only: hier wordt niets in MongoDB bewaard.
     * De geplande werkbonnen blijven zichtbaar boven eventuele grijze blokken.
     */
    private void refreshUnavailablePeriods(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isAfter(to) || unavailableSeries == null) {
            return;
        }

        List<Employee> all = employeeService.getAll().orElse(List.of());
        List<Employee> prioritized = all.stream()
                .filter(employee -> employee != null && employee.getPriority() != null
                        && employee.getPriority() > 0)
                .sorted(Comparator.comparingInt(Employee::getPriority))
                .toList();

        // De bestaande werkbonnen gebruiken priority - 1 als y-coordinaat.
        // Maak diezelfde rijen expliciet aan, ook als er geen werkbon staat.
        int maxPriority = prioritized.stream()
                .mapToInt(Employee::getPriority)
                .max().orElse(0);
        String[] employeeNames = new String[maxPriority];
        Arrays.fill(employeeNames, "");
        Set<Integer> usedPriorities = new HashSet<>();
        for (Employee employee : prioritized) {
            int priority = employee.getPriority();
            if (!usedPriorities.add(priority)) {
                // Gedeelde prioriteiten gebruiken dezelfde dashboardrij,
                // net zoals bij de oorspronkelijke werkbonnen.
                continue;
            }
            employeeNames[priority - 1] = employeeDisplayName(employee);
        }
        configuration.getyAxis().setCategories(employeeNames);
        if (employeeNameColumn != null) {
            employeeNameColumn.setCategories(employeeNames);
        }

        List<GanttSeriesItem> unavailableItems = new ArrayList<>();
        ZoneId zone = ZoneId.systemDefault();
        for (Employee employee : prioritized) {
            int yPosition = employee.getPriority() - 1;
            Map<DayOfWeek, WorkDayAvailability> byWeekday = new EnumMap<>(DayOfWeek.class);
            for (WorkDayAvailability day : WorkDayAvailability.normalize(employee.getWeeklySchedule())) {
                byWeekday.put(day.getDayOfWeek(), day);
            }

            for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
                WorkDayAvailability availability = byWeekday.get(date.getDayOfWeek());
                if (availability == null) {
                    continue;
                }

                if (!availability.isMorning()) {
                    unavailableItems.add(createUnavailableItem(employee, yPosition, date,
                            VISIBLE_DAY_START, AFTERNOON_START, zone));
                }
                if (!availability.isAfternoon()) {
                    unavailableItems.add(createUnavailableItem(employee, yPosition, date,
                            AFTERNOON_START, VISIBLE_DAY_END, zone));
                }
            }
        }
        unavailableSeries.setData(unavailableItems);
    }

    private GanttSeriesItem createUnavailableItem(Employee employee, int yPosition,
                                                  LocalDate day, LocalTime start, LocalTime end,
                                                  ZoneId zone) {
        GanttSeriesItem item = new GanttSeriesItem(
                employeeDisplayName(employee),
                day.atTime(start).atZone(zone).toInstant(),
                day.atTime(end).atZone(zone).toInstant());
        item.setY(yPosition);
        item.setColor(UNAVAILABLE_COLOR);
        return item;
    }

    /** Reload persisted absences after a save/delete, without rebuilding work orders. */
    private void refreshAbsenceChart() {
        refreshAbsences(selectedStartDate, selectedEndDate);
        chart.drawChart();
    }

    /** Dated absences override recurring free-day colors only on scheduled working halves. */
    private void refreshAbsences(LocalDate from, LocalDate to) {
        if (absenceSeries == null || from == null || to == null || from.isAfter(to)) {
            return;
        }

        Map<String, Employee> employeesById = employeeService.getAll().orElse(List.of())
                .stream()
                .filter(e -> e != null && e.getId() != null)
                .collect(Collectors.toMap(Employee::getId, e -> e, (a, b) -> a));

        List<GanttSeriesItem> absenceItems = new ArrayList<>();
        ZoneId zone = ZoneId.systemDefault();
        for (EmployeeAbsence absence : absenceService.findInRange(from, to)) {
            if (absence.getType() == null || absence.getStartDate() == null
                    || absence.getEndDate() == null || absence.getStartPart() == null
                    || absence.getEndPart() == null) {
                continue;
            }
            Employee employee = employeesById.get(absence.getEmployeeId());
            if (employee == null || employee.getPriority() == null
                    || employee.getPriority() <= 0) {
                continue;
            }

            int yPosition = employee.getPriority() - 1;
            Map<DayOfWeek, WorkDayAvailability> byDay = new EnumMap<>(DayOfWeek.class);
            for (WorkDayAvailability availability
                    : WorkDayAvailability.normalize(employee.getWeeklySchedule())) {
                byDay.put(availability.getDayOfWeek(), availability);
            }

            LocalDate first = absence.getStartDate().isAfter(from)
                    ? absence.getStartDate() : from;
            LocalDate last = absence.getEndDate().isBefore(to)
                    ? absence.getEndDate() : to;

            for (LocalDate date = first; !date.isAfter(last); date = date.plusDays(1)) {
                WorkDayAvailability regular = byDay.get(date.getDayOfWeek());
                if (regular == null) {
                    continue;
                }

                // Eerste dag: respecteer de gekozen start-helft.
                // Laatste dag: respecteer de gekozen eind-helft.
                boolean includeMorning = !date.equals(absence.getStartDate())
                        || absence.getStartPart() == DayPart.MORNING;
                boolean includeAfternoon = !date.equals(absence.getEndDate())
                        || absence.getEndPart() == DayPart.AFTERNOON;
                // Voor een registratie van 1 dag gelden beide grenzen.
                if (date.equals(absence.getEndDate()) && absence.getEndPart() == DayPart.MORNING) {
                    includeAfternoon = false;
                }

                if (includeMorning && regular.isMorning()) {
                    absenceItems.add(createAbsenceItem(absence, yPosition, date,
                            VISIBLE_DAY_START, AFTERNOON_START, zone));
                }
                if (includeAfternoon && regular.isAfternoon()) {
                    absenceItems.add(createAbsenceItem(absence, yPosition, date,
                            AFTERNOON_START, VISIBLE_DAY_END, zone));
                }
            }
        }
        absenceSeries.setData(absenceItems);
    }

    private GanttSeriesItem createAbsenceItem(EmployeeAbsence absence, int yPosition,
                                               LocalDate date, LocalTime start,
                                               LocalTime end, ZoneId zone) {
        GanttSeriesItem item = new GanttSeriesItem(absence.getType().getLabel(),
                date.atTime(start).atZone(zone).toInstant(),
                date.atTime(end).atZone(zone).toInstant());
        item.setY(yPosition);
        item.setCustom(new AbsenceChartData(absence.getId(), absence.getType().getLabel()));
        item.setColor(switch (absence.getType()) {
            case VACATION -> VACATION_COLOR;
            case SICK -> SICK_COLOR;
            case OTHER -> OTHER_ABSENCE_COLOR;
        });
        return item;
    }

    private String employeeDisplayName(Employee employee) {
        String firstName = employee.getFirstName() == null ? "" : employee.getFirstName().trim();
        String lastName = employee.getLastName() == null ? "" : employee.getLastName().trim();
        return (firstName + " " + lastName).trim();
    }

    private XAxis createProjectColumn() {
        XAxis column = new XAxis();
        column.setTitle("");
        final Labels label = new Labels();
        // Ook bij technici zonder werkbon de naam blijven tonen.
        label.setFormat("{value}");
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

                    Instant overlapStart =
                            first.getStart().isAfter(second.getStart())
                                    ? first.getStart()
                                    : second.getStart();

                    Instant overlapEnd =
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
        private final Instant start;
        private final Instant end;
        private final Integer yPosition;

        private OverlapSegment(
                String customerNames,
                Instant start,
                Instant end,
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



    /** These points refer to employeeAbsences, not to work order IDs. */
    @SuppressWarnings("unused")
    static class AbsenceChartData extends AbstractConfigurationObject {
        private String id;
        private String assignee;

        public AbsenceChartData(String id, String assignee) {
            this.id = id;
            this.assignee = assignee;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getAssignee() { return assignee; }
        public void setAssignee(String assignee) { this.assignee = assignee; }
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

