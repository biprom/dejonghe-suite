package com.adverto.dejonghe.server.views.Staff;

import com.adverto.dejonghe.common.entities.employee.Employee;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;


public class EmployeeCard extends Card {

    EmployeeView employeeView;
    Employee employee;
    MenuBar actionBar;


    public EmployeeCard(Employee employee, EmployeeView employeeView) {
        this.employeeView = employeeView;
        this.employee = employee;
        setUpCardLayout(employee);
    }


    private void setUpCardLayout(Employee employee) {

        this.removeAll();
        this.getElement()
                .getChildren() // Stream<Element>
                .filter(e -> "footer".equals(e.getAttribute("slot")))
                .forEach(Element::removeFromParent);

        this.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL
        );

        this.getStyle().set("background-color", "#fbf6ea");

        Div title = new Div(employee.getFirstName() + " " + employee.getLastName());
        title.addClassName("card-title");
        this.add(title);

        Div subtitle = new Div(new Text(employee.getPhoneNumber()));
        subtitle.addClassName("card-subtitle");
        this.add(subtitle);

        Div prio = new Div(new Text(String.valueOf(employee.getPriority())));

        prio.getStyle()
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

        this.addToFooter(prio);

        this.addToFooter(getActionMenu());
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setWidth("100%");
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Pas aan" , editEployee());
        actie.getSubMenu().addItem("Verlofdagen");
        actie.getSubMenu().addItem("Productiviteit");
        actie.getSubMenu().addItem("Verwijderen", removeEmployee());
        actionBarLayout.addToEnd(actionBar);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> removeEmployee() {
        return event -> {
            employeeView.openRemoveDialog(employee, this);
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> editEployee() {
        return event -> {
            employeeView.openEditDialog(employee, this);
        };
    }

    public void refreshWith(Employee employeeToEdit) {
        setUpCardLayout(employeeToEdit);
    }
}
