package com.adverto.dejonghe.server.views.Staff;

import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.employee.WorkDayAvailability;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;

import java.time.DayOfWeek;

public class EmployeeCard extends Card {

    private final EmployeeView employeeView;
    private Employee employee;
    private MenuBar actionBar;

    public EmployeeCard(Employee employee, EmployeeView employeeView) {
        this.employeeView = employeeView;
        this.employee = employee;
        setUpCardLayout(employee);
    }

    private void setUpCardLayout(Employee employee) {
        removeAll();
        getElement().getChildren()
                .filter(element -> "footer".equals(element.getAttribute("slot")))
                .toList()
                .forEach(Element::removeFromParent);

        // The horizontal card variant is intentionally omitted:
        // the new weekday preview needs a full line below the name.
        addThemeVariants(CardVariant.LUMO_OUTLINED, CardVariant.LUMO_ELEVATED);
        getStyle().set("background-color", "#fbf6ea");

        Div title = new Div(value(employee.getFirstName()) + " " + value(employee.getLastName()));
        title.addClassName("card-title");
        add(title);

        Div subtitle = new Div(new Text(value(employee.getPhoneNumber())));
        subtitle.addClassName("card-subtitle");
        add(subtitle);

        add(createWeekPreview(employee));

        Div priority = new Div(new Text(String.valueOf(
                employee.getPriority() == null ? 0 : employee.getPriority())));
        priority.getStyle()
                .set("background-color", "#FFC107")
                .set("color", "#1E2A44")
                .set("border-radius", "50%")
                .set("min-width", "48px")
                .set("min-height", "48px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("font-weight", "700")
                .set("font-size", "20px")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.15)");

        addToFooter(priority);
        addToFooter(getActionMenu());
    }

    private Div createWeekPreview(Employee employee) {
        Div wrapper = new Div();
        wrapper.getStyle().set("width", "100%").set("margin-top", "0.5rem");

        Span label = new Span("Weekplanning");
        label.getStyle().set("display", "block")
                .set("font-size", "var(--lumo-font-size-xs)")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("margin-bottom", "0.3rem");
        wrapper.add(label);

        Div days = new Div();
        days.getStyle().set("display", "flex").set("gap", "5px").set("width", "100%");
        for (WorkDayAvailability availability : WorkDayAvailability.normalize(employee.getWeeklySchedule())) {
            Div column = new Div();
            column.getStyle().set("flex", "1 1 0").set("min-width", "0");

            Span dayLabel = new Span(shortDayName(availability.getDayOfWeek()));
            dayLabel.getStyle().set("display", "block").set("text-align", "center")
                    .set("font-size", "11px").set("margin-bottom", "4px");
            column.add(dayLabel);
            column.add(halfDayBar(availability.isMorning()));
            column.add(halfDayBar(availability.isAfternoon()));
            column.getElement().setAttribute("title", shortDayName(availability.getDayOfWeek())
                    + ": voormiddag " + (availability.isMorning() ? "aanwezig" : "vrij")
                    + ", namiddag " + (availability.isAfternoon() ? "aanwezig" : "vrij"));
            days.add(column);
        }
        wrapper.add(days);
        return wrapper;
    }

    private static Div halfDayBar(boolean available) {
        Div bar = new Div();
        bar.getStyle().set("height", "10px")
                .set("width", "100%")
                .set("margin-bottom", "3px")
                .set("border-radius", "3px")
                .set("background-color", available ? "#299766" : "#d4d7dd");
        return bar;
    }

    private static String shortDayName(DayOfWeek day) {
        return switch (day) {
            case MONDAY -> "Ma";
            case TUESDAY -> "Di";
            case WEDNESDAY -> "Wo";
            case THURSDAY -> "Do";
            case FRIDAY -> "Vr";
            case SATURDAY -> "Za";
            case SUNDAY -> "Zo";
        };
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setWidth("100%");
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Pas aan", event -> employeeView.openEditDialog(employee, this));
        actie.getSubMenu().addItem("Weekplanning", event -> employeeView.openWeeklyScheduleDialog(employee));
        actie.getSubMenu().addItem("Verlofdagen");
        actie.getSubMenu().addItem("Productiviteit");
        actie.getSubMenu().addItem("Verwijderen", event -> employeeView.openRemoveDialog(employee, this));
        actionBarLayout.addToEnd(actionBar);
        return actionBarLayout;
    }

    public void refreshWith(Employee employeeToEdit) {
        this.employee = employeeToEdit;
        setUpCardLayout(employeeToEdit);
    }

    private static String value(String input) {
        return input == null ? "" : input;
    }
}
