package com.adverto.dejonghe.server.views.Staff;

import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.employee.WorkDayAvailability;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/** Edit the fixed weekly schedule, without modifying Employee until Save. */
public class EmployeeWeeklyScheduleDialog extends Dialog {

    private final Map<DayOfWeek, Checkbox> morningFields = new EnumMap<>(DayOfWeek.class);
    private final Map<DayOfWeek, Checkbox> afternoonFields = new EnumMap<>(DayOfWeek.class);

    public EmployeeWeeklyScheduleDialog(Employee employee,
                                        Consumer<List<WorkDayAvailability>> onSave) {
        String name = ((employee.getFirstName() == null ? "" : employee.getFirstName())
                + " " + (employee.getLastName() == null ? "" : employee.getLastName())).trim();
        setHeaderTitle("Weekplanning - " + name);
        setWidth("min(95vw, 32rem)");

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);
        content.setWidthFull();

        Span explanation = new Span("Vink aan wanneer deze medewerker beschikbaar is. "
                + "Deze planning herhaalt zich iedere week.");
        explanation.getStyle().set("color", "var(--lumo-secondary-text-color)")
                .set("font-size", "var(--lumo-font-size-s)")
                .set("margin-bottom", "var(--lumo-space-m)");
        content.add(explanation);

        HorizontalLayout heading = createRow();
        heading.add(cell(new Span("Dag"), "40%", false),
                cell(new Span("Voormiddag"), "30%", true),
                cell(new Span("Namiddag"), "30%", true));
        heading.getStyle().set("font-weight", "600");
        content.add(heading);

        List<WorkDayAvailability> values = WorkDayAvailability.normalize(employee.getWeeklySchedule());
        for (WorkDayAvailability day : values) {
            Checkbox morning = new Checkbox();
            Checkbox afternoon = new Checkbox();
            morning.setValue(day.isMorning());
            afternoon.setValue(day.isAfternoon());
            morning.getElement().setAttribute("aria-label", dayName(day.getDayOfWeek()) + " voormiddag");
            afternoon.getElement().setAttribute("aria-label", dayName(day.getDayOfWeek()) + " namiddag");
            morningFields.put(day.getDayOfWeek(), morning);
            afternoonFields.put(day.getDayOfWeek(), afternoon);

            HorizontalLayout row = createRow();
            row.add(cell(new Span(dayName(day.getDayOfWeek())), "40%", false),
                    cell(morning, "30%", true),
                    cell(afternoon, "30%", true));
            row.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
            content.add(row);
        }

        Button resetButton = new Button("Ma-vr volledig", event -> {
            for (DayOfWeek day : DayOfWeek.values()) {
                boolean available = day.getValue() <= DayOfWeek.FRIDAY.getValue();
                morningFields.get(day).setValue(available);
                afternoonFields.get(day).setValue(available);
            }
        });
        resetButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        resetButton.getStyle().set("margin-top", "var(--lumo-space-s)");
        content.add(resetButton);

        add(content);

        Button cancelButton = new Button("Annuleer", event -> close());
        Button saveButton = new Button("Bewaar", event -> {
            List<WorkDayAvailability> newSchedule = new ArrayList<>();
            for (DayOfWeek day : DayOfWeek.values()) {
                newSchedule.add(new WorkDayAvailability(day,
                        morningFields.get(day).getValue(),
                        afternoonFields.get(day).getValue()));
            }
            try {
                onSave.accept(newSchedule);
                Notification.show("Weekplanning bewaard.");
                close();
            } catch (RuntimeException exception) {
                Notification error = Notification.show("Weekplanning kon niet worden bewaard.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(cancelButton, saveButton);
    }

    private static HorizontalLayout createRow() {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setSpacing(false);
        row.setPadding(false);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.getStyle().set("padding", "0.35rem 0");
        return row;
    }

    private static Div cell(Component component, String width, boolean center) {
        Div cell = new Div(component);
        cell.getStyle().set("width", width).set("box-sizing", "border-box");
        if (center) {
            cell.getStyle().set("display", "flex").set("justify-content", "center");
        }
        return cell;
    }

    private static String dayName(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> "Maandag";
            case TUESDAY -> "Dinsdag";
            case WEDNESDAY -> "Woensdag";
            case THURSDAY -> "Donderdag";
            case FRIDAY -> "Vrijdag";
            case SATURDAY -> "Zaterdag";
            case SUNDAY -> "Zondag";
        };
    }
}
