package com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.QueryParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeviceCard extends Card {

    Customer customer;
    Address workAddress;

    CustomerService customerService;
    MenuBar actionBar;

    public DeviceCard() {
        this.getStyle().set("background-color", "#fbf6ea");
    }


    private void setUpCardLayout() {

        this.removeAll();

        this.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL
        );

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        if((workAddress.getCoupledDeviceList() != null) && (workAddress.getCoupledDeviceList().size() > 0)) {
            Div title = new Div(new Text(workAddress.getCoupledDeviceList().size() + " toestell(en) op dit werfadres"));
            title.addClassName("card-title");
            title.addSingleClickListener(event->{
                Map<String, List<String>> parameters = new HashMap<>();
                parameters.put(
                        "workAddressName",List.of(workAddress.getAddressName()));
                UI.getCurrent().navigate(
                        DeviceView.class,
                        new QueryParameters(parameters)
                );
            });
            horizontalLayout.add(title);
            this.setTitle(horizontalLayout);
        }
        else {
            Div title = new Div(new Text("geen toestellen op dit werfadres gevonden"));
            title.addClassName("card-title");
            title.addSingleClickListener(event -> {
                Map<String, List<String>> parameters = new HashMap<>();
                parameters.put(
                        "workAddressName",List.of(workAddress.getAddressName()));
                UI.getCurrent().navigate(
                        DeviceView.class,
                        new QueryParameters(parameters)
                );
            });
            horizontalLayout.add(title);
            this.setTitle(horizontalLayout);
        }

        this.add(getActionMenu());

    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setWidth("100%");

        VerticalLayout actionVLayout = new VerticalLayout();
        actionVLayout.setWidth("30%");
        actionVLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Open toestellen", openDevices());

        actionVLayout.add(actionBar);

        VerticalLayout layout = new VerticalLayout();
        actionBarLayout.addToStart(layout);
        actionBarLayout.addToEnd(actionVLayout);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openDevices() {
        return event -> {
            Map<String, List<String>> parameters = new HashMap<>();
            parameters.put(
                    "workAddressName",List.of(workAddress.getAddressName()));
            UI.getCurrent().navigate(
                    DeviceView.class,
                    new QueryParameters(parameters)
            );
        };
    }


    public void setDeviceId(List<String> deviceIds, Address workAddress, Customer customer) {
        this.customer = customer;
        this.workAddress = workAddress;
        setUpCardLayout();
    }
}
