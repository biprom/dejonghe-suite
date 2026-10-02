package com.adverto.dejonghe.server.views.customers.workAddress.device;

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

    private Customer customer;
    private Address workAddress;

    private MenuBar actionBar;

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

        /*
         * TITLE
         */
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Div title = new Div(
                new Text("Toestellen")
        );

        title.addClassName("card-title");

        // Subtiel aangeven dat de titel klikbaar is
        title.getStyle()
                .set("cursor", "pointer");

        title.addSingleClickListener(event ->
                openDeviceView()
        );

        horizontalLayout.add(title);

        this.setTitle(horizontalLayout);

        /*
         * SUBTITLE
         */
        this.add(checkIfThereAreDevices());

        /*
         * ACTION MENU
         */
        this.add(getActionMenu());
    }

    private Div checkIfThereAreDevices() {

        Div subtitle;

        if (workAddress.getCoupledDeviceList() != null
                && !workAddress.getCoupledDeviceList().isEmpty()) {

            subtitle = new Div(
                    new Text(
                            workAddress.getCoupledDeviceList().size()
                                    + " toestel(len) op dit werfadres"
                    )
            );

        } else {

            subtitle = new Div(
                    new Text(
                            "Geen toestellen op dit werfadres gevonden"
                    )
            );
        }

        subtitle.getStyle()
                .set("font-size", "20px")
                .set("cursor", "pointer")
                .set("text-decoration", "underline");

        subtitle.addSingleClickListener(event ->
                openDeviceView()
        );

        return subtitle;
    }

    /**
     * Opent de DeviceView voor het huidige werfadres.
     */
    private void openDeviceView() {

        if (workAddress == null) {
            return;
        }

        Map<String, List<String>> parameters = new HashMap<>();

        parameters.put(
                "workAddressName",
                List.of(workAddress.getAddressName())
        );

        UI.getCurrent().navigate(
                DeviceView.class,
                new QueryParameters(parameters)
        );
    }

    private HorizontalLayout getActionMenu() {

        HorizontalLayout actionBarLayout =
                new HorizontalLayout();

        actionBarLayout.setWidthFull();

        VerticalLayout actionVLayout =
                new VerticalLayout();

        actionVLayout.setWidth("30%");

        actionVLayout.setAlignItems(
                FlexComponent.Alignment.CENTER
        );

        actionBar = new MenuBar();

        actionBar.addClassName("large-menubar");

        actionBar.addThemeVariants(
                MenuBarVariant.LUMO_DROPDOWN_INDICATORS
        );

        MenuItem actie =
                actionBar.addItem("Actie");

        actie.getElement()
                .getClassList()
                .add("menu-as-button");

        actie.getSubMenu()
                .addItem(
                        "Open toestellen",
                        openDevices()
                );

        actionVLayout.add(actionBar);

        VerticalLayout layout =
                new VerticalLayout();

        actionBarLayout.addToStart(layout);
        actionBarLayout.addToEnd(actionVLayout);

        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openDevices() {
        return event -> openDeviceView();
    }

    public void setDeviceId(
            List<String> deviceIds,
            Address workAddress,
            Customer customer
    ) {

        this.customer = customer;
        this.workAddress = workAddress;

        setUpCardLayout();
    }
}