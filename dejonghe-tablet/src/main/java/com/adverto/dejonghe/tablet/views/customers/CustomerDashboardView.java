package com.adverto.dejonghe.tablet.views.customers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Contact;
import com.adverto.dejonghe.common.entities.customers.Customer;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@PageTitle("Klanten")
@Route("customers-dashboard")
@Menu(order = 0, icon = LineAwesomeIconUrl.CLIPBOARD_SOLID)
public class CustomerDashboardView extends VerticalLayout implements BeforeEnterObserver {

    CustomerService customerService;
    InvoiceService invoiceService;

    TextField searchField;
    Optional<List<Customer>> customers;
    VirtualList<List<Customer>> virtualList;


    public CustomerDashboardView(CustomerService customerService,
                                 InvoiceService invoiceService) {

        this.customerService = customerService;
        this.invoiceService = invoiceService;

        getAllCustomers();

        this.setSizeFull();
        this.setPadding(true);
        this.setSpacing(true);
        this.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");
        this.addClassName("view-wrapper");
        this.getStyle().set("background-color", "#e9ebef");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.add(getActionMenu(),getSearchBar());

        this.add(horizontalLayout);

        this.setGridLayout();

        VerticalLayout layout = new VerticalLayout();
        layout.setHeight("92%");
        layout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        layout.add(virtualList);
        layout.expand(virtualList);
        this.add(layout);
    }

    public static void removeCardFromList(CustomerCard card) {
        UI.getCurrent().getPage().reload();
    }

    private void setGridLayout() {
        List<List<Customer>> rows = chunk(customers.get(), 3);
        virtualList = new VirtualList();
        virtualList.setWidth("100%");
        virtualList.setHeight("100%");
        virtualList.setItems(rows);
        virtualList.addClassName("my-virtual-list");
        virtualList.setRenderer(new ComponentRenderer<>(row -> {
            HorizontalLayout rowLayout = new HorizontalLayout();
            rowLayout.setWidthFull();
            rowLayout.setSpacing(true);

            row.forEach(customer -> {
                CustomerCard card = new CustomerCard(customer, invoiceService, customerService);
                card.addClassName("virtual-item");
                card.setWidth("33%");  // 3 kolommen
                rowLayout.add(card);
            });

            return rowLayout;
        }));
    }

    private void getAllCustomers() {
        customers = customerService.getAllCustomers()
                .map(list -> list.stream()
                        .sorted(Comparator.comparing(
                                c -> c.getName().replaceFirst("^[’']", ""),
                                String.CASE_INSENSITIVE_ORDER
                        ))
                        .toList()
                );
    }

    private HorizontalLayout getSearchBar() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.setWidth("100%");
        searchField = new TextField();
        searchField.setWidth("100%");
        searchField.setPlaceholder("Zoek op naam,btw-nr,...");
        searchField.addValueChangeListener(event -> {

            Optional<List<Customer>> optCustomer =
                    customerService.getCustomerByNameOrVat(event.getValue())
                            .map(list -> {
                                list.sort(Comparator.comparing(x -> x.getName().replaceFirst("^[’']", ""), String.CASE_INSENSITIVE_ORDER));
                                return list;
                            });
            if((optCustomer.isPresent()) && (optCustomer.get().size() > 0)) {
                List<List<Customer>> rows = chunk(optCustomer.get(), 3);
                virtualList.setItems(rows);
            }
            else{
                Notification.show("Geen klanten gevonden");
            }
        });
        searchLayout.add(searchField);
        return searchLayout;
    }

    public static <T> List<List<T>> chunk(List<T> list, int size) {
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            result.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return result;
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(Alignment.CENTER);
        actionBarLayout.setPadding(true);

        //add New Button
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {
            Customer customer = new Customer();
            customer.setName("");
            customer.setVatNumber("");
            customer.setComment("");
            customer.setAlertMessage("");
            List<Address>addressList = new ArrayList<>();
            Address invoiceAddress = new Address();
            List<Contact>contactList = new ArrayList<>();
            contactList.add(new Contact());
            invoiceAddress.setContactList(contactList);
            addressList.add(invoiceAddress);
            invoiceAddress.setInvoiceAddress(true);
            Address workAddress = new Address();
            List<Contact>contactList2 = new ArrayList<>();
            contactList2.add(new Contact());
            workAddress.setContactList(contactList2);
            addressList.add(workAddress);
            customer.setAddresses(addressList);
            String newId = customerService.save(customer);
            UI.getCurrent().navigate(
                    CustomerView.class, newId);
        });
        actionBarLayout.add(newButton);
        return actionBarLayout;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
