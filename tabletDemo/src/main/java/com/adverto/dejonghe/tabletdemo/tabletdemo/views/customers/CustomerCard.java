package com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.workorder.PendingWorkorderView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.text.NumberFormat;
import java.util.Locale;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;


public class CustomerCard extends Card {

    Customer customer;
    MenuBar actionBar;

    Span infoSpan;
    InvoiceService invoiceService;
    CustomerService customerService;

    Notification deleteCustomerNotification;
    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));

    public CustomerCard(Customer customer,
                        InvoiceService invoiceService,
                        CustomerService customerService) {

        this.customer = customer;
        this.invoiceService = invoiceService;
        this.customerService = customerService;

        setUpNumberFormat();
        createReportDelete();
        setUpCardLayout();
    }

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    public Notification createReportDelete() {
        deleteCustomerNotification = new Notification();
        deleteCustomerNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteCustomerNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Klant verwijderen?"), retryBtn,
                createCloseBtn(deleteCustomerNotification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        deleteCustomerNotification.add(layout);

        return deleteCustomerNotification;
    }

    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    if(customer != null){
                        customerService.delete(customer);
                        CustomerDashboardView.removeCardFromList(this);
                        //when filter is selected
//                        suppliers.get().remove(customer);
//                        supplierService.delete(customer);
                    }
                    else{
                        Notification.show("Geen werkbonnen te verwijderen");
                    }
                    notification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    private void setUpCardLayout() {

        this.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL
        );

        Div title = new Div(new Text(customer.getName()));
        title.addClassName("card-title");
        title.addSingleClickListener(event -> {
            UI.getCurrent().navigate(
                    CustomerView.class, customer.getId());
        });
        this.setTitle(title);

        Div subtitle = new Div(new Text(customer.getVatNumber()));
        subtitle.addClassName("card-subtitle");
        this.add(subtitle);

        if((customer.getAlertMessage() != null) && (customer.getAlertMessage().length() > 0)) {
            this.getStyle().set("background-color", "#fbf6ea");
            Span alertSpan = new Span(customer.getAlertMessage());
            alertSpan.getStyle().set("color", "red").set("font-weight", "bold").set("font-size", "1.2rem");
            HorizontalLayout layout = new HorizontalLayout(alertSpan);
            layout.setSpacing(true);
            layout.setPadding(false);
            this.setHeaderSuffix(layout);
        }
        else{
            this.getStyle().set("background-color", "#fbf6ea");
        }

        Address addressToShow = customer.getAddresses().stream().filter(x -> (x.getInvoiceAddress() != null) && (x.getInvoiceAddress() == true)).findFirst().get();
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("card-address");
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.add(new Span(addressToShow.getStreet()));
        layout.add(new Span(addressToShow.getZip() + " " + addressToShow.getCity()));
        layout.add(new Span(addressToShow.getCountry()));
        this.add(layout);

        this.addToFooter(getActionMenu());

    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setWidth("100%");
        //actionBarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Open", openClickEvent());
        actie.getSubMenu().addItem("Lopende werkbonnen", getRunningWorkOrderOfCustomerClickEvent());
        actie.getSubMenu().addItem("Verwijder", removeClickEvent());
        actionBarLayout.addToEnd(actionBar);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> removeClickEvent() {
        return event -> {
            deleteCustomerNotification.open();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openClickEvent() {
        return event -> {
            UI.getCurrent().navigate(
                    CustomerView.class, customer.getId());
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getRunningWorkOrderOfCustomerClickEvent() {
        return event -> {
            UI.getCurrent().navigate(
                    PendingWorkorderView.class, customer.getId());
        };
    }

    private void addToCard(VerticalLayout verticalLayout) {
        this.add(verticalLayout);
    }

}
