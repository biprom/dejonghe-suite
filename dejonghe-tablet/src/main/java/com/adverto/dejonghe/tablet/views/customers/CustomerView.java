package com.adverto.dejonghe.tablet.views.customers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Contact;
import com.adverto.dejonghe.common.entities.customers.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("Klanten")
@Route("customers")
public class CustomerView extends VerticalLayout implements BeforeEnterObserver, HasUrlParameter<String> {

    CustomerService customerService;
    InvoiceService invoiceService;

    Customer selectedCustomer;
    InvoiceCard invoiceCard;

    VerticalLayout workAddressCardVerticalLayout;

    HorizontalLayout layout;


    public CustomerView(CustomerService customerService,
                        InvoiceService invoiceService) {
        this.customerService = customerService;
        this.invoiceService = invoiceService;

        this.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");
        this.addClassName("view-wrapper");
        this.getStyle().set("background-color", "#e9ebef");

        this.setPadding(true);
        this.setSpacing(true);
        this.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        horizontalLayout.setSpacing(false);
        //horizontalLayout.setPadding(true);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.add(getActionMenu());
        this.add(horizontalLayout);

        layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setHeight("92%");
        layout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        layout.add(setUpInvoiceCard());
        this.add(layout);

    }


    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(Alignment.CENTER);
        actionBarLayout.setPadding(true);

        //add Back button
        Button backButton = new Button(VaadinIcon.ARROW_LEFT.create());
        backButton.addClassName("subNav-new");
        backButton.addClickListener(e -> {
            UI.getCurrent().getPage().getHistory().back();
        });

        //add New Button
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {
            Address newWorkAddress = new Address();
            newWorkAddress.setInvoiceAddress(false);
            Contact newWorkContact = new Contact();
            newWorkContact.setActive(true);
            List<Contact>contactList = new ArrayList<>();
            contactList.add(newWorkContact);
            newWorkAddress.setContactList(contactList);
            selectedCustomer.getAddresses().add(newWorkAddress);
            customerService.save(selectedCustomer);
            Optional<Customer> optional = customerService.findCustomerById(selectedCustomer.getId());
            if (optional.isPresent()) {
                selectedCustomer = optional.get();
                setSelectedCustomer();
            }
        });
        actionBarLayout.add(backButton,newButton);
        return actionBarLayout;
    }

    private VerticalLayout setUpWorkAddressCard() {
        workAddressCardVerticalLayout = new VerticalLayout();
        workAddressCardVerticalLayout.setPadding(false);
        workAddressCardVerticalLayout.setSpacing(true);
        workAddressCardVerticalLayout.setWidth("50%");
        selectedCustomer.getAddresses().stream().filter(address -> (address.getInvoiceAddress() == null) || (address.getInvoiceAddress() == false)).forEach(address -> {
            WorkAddressCard workAddressCard = new WorkAddressCard();
            workAddressCard.setWidth("100%");
            workAddressCard.setWorkAddress(selectedCustomer,address);
            workAddressCard.setCustomerService(customerService);
            workAddressCardVerticalLayout.add(workAddressCard);
        });
        return workAddressCardVerticalLayout;
    }

    private VerticalLayout setUpInvoiceCard() {
        VerticalLayout invoiceCardVerticalLayout = new VerticalLayout();
        invoiceCardVerticalLayout.setPadding(false);
        invoiceCardVerticalLayout.setSpacing(true);
        invoiceCardVerticalLayout.setWidth("50%");
        invoiceCard = new InvoiceCard();
        invoiceCard.setCustomerService(customerService);
        invoiceCard.setWidthFull();
        invoiceCardVerticalLayout.add(invoiceCard);
        return invoiceCardVerticalLayout;
    }

    private void fillInvoiceCard(Customer selectedCustomer) {
        invoiceCard.setCustomer(selectedCustomer);
    }

    public void setSelectedCustomer() {
        fillInvoiceCard(selectedCustomer);
        if(workAddressCardVerticalLayout != null) {
            layout.remove(workAddressCardVerticalLayout);
        }
        layout.add(setUpWorkAddressCard());
        layout.getStyle().set("overflow", "auto");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
         Optional<Customer> optional = customerService.findCustomerById(s);
         if (optional.isPresent()) {
             selectedCustomer = optional.get();
             setSelectedCustomer();
         }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
