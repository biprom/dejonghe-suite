package com.adverto.dejonghe.server.views.dashboard;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.FontWeight;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.charts.model.style.Style;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Dashboard")
@Route("dashboard")
public class DashboardView extends VerticalLayout {

    final Chart chart = new Chart(ChartType.GANTT);
    final Configuration configuration = chart.getConfiguration();

    Navigator navigator;
    YAxis navigatorYAxis;

    GanttSeries series;

    private static final Instant TODAY = Instant.now()
            .truncatedTo(ChronoUnit.DAYS);

    public DashboardView() {
        setUpDemoGanttChart();
    }


    private void setUpDemoGanttChart() {

        Button previousDay = new Button("Vorige dag", e -> shiftDay(chart, -1));
        Button nextDay = new Button("Volgende dag", e -> shiftDay(chart, 1));

        XAxis xAxis = configuration.getxAxis();
        xAxis.setTickInterval(3600 * 1000);
        xAxis.setStartOnTick(true);

        YAxis yAxis = configuration.getyAxis();
        yAxis.setUniqueNames(true);
        yAxis.setType(AxisType.CATEGORY);

        configuration.getScrollbar().setEnabled(true);

        RangeSelector rangeSelector = new RangeSelector();
        rangeSelector.setSelected(0);

        RangeSelectorButton dayButton = new RangeSelectorButton();
        dayButton.setType(RangeSelectorTimespan.DAY);
        dayButton.setCount(1);
        dayButton.setText("1d");


        RangeSelectorButton weekButton = new RangeSelectorButton();
        weekButton.setType(RangeSelectorTimespan.WEEK);
        weekButton.setCount(1);
        weekButton.setText("1w");

        RangeSelectorButton monthButton = new RangeSelectorButton();
        monthButton.setType(RangeSelectorTimespan.MONTH);
        monthButton.setCount(1);
        monthButton.setText("1m");

        RangeSelectorButton allButton = new RangeSelectorButton();
        allButton.setType(RangeSelectorTimespan.ALL);
        allButton.setText("All");

        rangeSelector.setButtons(dayButton, weekButton, monthButton, allButton);

        configuration.setRangeSelector(rangeSelector);

        configuration.getRangeSelector().setEnabled(true);
        configuration.getRangeSelector().setSelected(0);

        navigator = configuration.getNavigator();
        navigator.setEnabled(true);

        long startTime = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        navigator.getXAxis().setMin(startTime);
        navigator.getXAxis().setMax(endTime);

        navigatorYAxis = navigator.getYAxis();
        navigatorYAxis.setMin(0);
        navigatorYAxis.setMax(3);
        navigatorYAxis.setReversed(true);

        AxisGrid grid = new AxisGrid();
        grid.setEnabled(true);
        grid.setColumns(List.of(
                createProjectColumn(),
                createStartDateColumn(),
                createEndDateColumn()));
        yAxis.setGrid(grid);

        PlotOptionsGantt plotOptionsGantt = new PlotOptionsGantt();
        plotOptionsGantt.setPointPadding(0.02);
        plotOptionsGantt.setGroupPadding(0.02);
        plotOptionsGantt.setBorderWidth(2);
        plotOptionsGantt.setBorderColor(SolidColor.BLACK);
        configuration.setPlotOptions(plotOptionsGantt);


        final GanttSeries projectDevelopmentSeries = createProjectDevelopmentSeries();
        // Configure Labels
        PlotOptionsGantt seriesPlotOptions = new PlotOptionsGantt();
        var dataLabels = new ArrayList<DataLabels>();

        var assigneeLabel = new DataLabels(true);
        assigneeLabel.setAlign(HorizontalAlign.CENTER);
        assigneeLabel.setFormat("{point.custom.assignee}");
        dataLabels.add(assigneeLabel);

        seriesPlotOptions.setDataLabels(dataLabels);
        series.setPlotOptions(seriesPlotOptions);
        configuration.addSeries(series);

        // Configure click callback
        chart.addPointClickListener(event -> {
            var ganttSeries = ((GanttSeries) event.getSeries());
            var customData = (TaskCustomData) ganttSeries
                    .get(event.getItemIndex()).getCustom();
            System.out.println(
                    "Clicked on task assigned to " + customData.assignee);
        });

        ChartModel chartConf = configuration.getChart();

        Style style = new Style();
        style.setFontWeight(FontWeight.BOLD);
        style.setFontSize("13px");
        style.setColor(new SolidColor(0, 255, 0));

        chartConf.setStyle(style);

        HorizontalLayout toolbar =
                new HorizontalLayout(previousDay, nextDay, chart);
        VerticalLayout layout = new VerticalLayout(toolbar, chart);
        add(layout);
    }

    private XAxis createProjectColumn() {
        XAxis column = new XAxis();
        column.setTitle("Project1");
        final Labels label = new Labels();
        label.setFormat("{point.name}");
        column.setLabels(label);
        return column;
    }

    private XAxis createStartDateColumn() {
        XAxis column = new XAxis();
        column.setTitle("Dagtijd");
        final Labels label = new Labels(true);
        label.setFormat("Koekoe");
        column.setLabels(label);
        return column;
    }

    private XAxis createEndDateColumn() {
        XAxis column = new XAxis();
        column.setTitle("Te factureren");
        column.setOffset(30);
        final Labels label = new Labels(true);
        label.setFormat("Hallo");
        column.setLabels(label);
        return column;
    }

    private GanttSeries createProjectDevelopmentSeries() {
        series = new GanttSeries();
        series.setName("Project 1");

        GanttSeriesItem item;

        item = new GanttSeriesItem("Kristof Dejonghe", Instant.parse("2026-03-12T00:00:00Z"),
                Instant.parse("2026-03-12T12:00:00Z"));
        item.setY(0);
        item.setCustom(new TaskCustomData("Werken Biprom"));
        item.setColor(new SolidColor(0, 255, 0,0.5));
        series.add(item);

        item = new GanttSeriesItem("Kristof Dejonghe", Instant.parse("2026-03-12T10:00:00Z"),
                Instant.parse("2026-03-12T19:00:00Z"));
        item.setY(0);
        item.setCustom(new TaskCustomData("Werken Biprom"));
        item.setColor(new SolidColor(0, 255, 0,0.5));
        series.add(item);

        item = new GanttSeriesItem("Kristof Hoedt", Instant.parse("2026-03-12T00:00:00Z"),
                Instant.parse("2026-03-12T12:00:00Z"));
        item.setY(1);
        item.setCustom(new TaskCustomData("Werkt nooit"));
        series.add(item);

        item = new GanttSeriesItem("Bartek", Instant.parse("2026-03-12T00:00:00Z"),
                Instant.parse("2026-03-12T12:00:00Z"));
        item.setY(2);
        item.setCustom(new TaskCustomData("Koekoe"));
        series.add(item);

        return series;
    }

    private void shiftDay(Chart chart, int days) {

        Configuration conf = chart.getConfiguration();
        XAxis xAxis = conf.getxAxis();

        Number min = xAxis.getMin();
        Number max = xAxis.getMax();

        long oneDay = 24L * 60 * 60 * 1000;

        double newMin = min.doubleValue() + (days * oneDay);
        double newMax = max.doubleValue() + (days * oneDay);

        xAxis.setMin(newMin);
        xAxis.setMax(newMax);

        chart.drawChart();
    }


    @SuppressWarnings("unused")
    static class TaskCustomData extends AbstractConfigurationObject {
        private String assignee;

        public TaskCustomData(String assignee) {
            this.assignee = assignee;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }
}
