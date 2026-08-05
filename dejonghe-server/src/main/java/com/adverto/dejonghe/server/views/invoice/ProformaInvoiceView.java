package com.adverto.dejonghe.server.views.invoice;

import com.adverto.dejonghe.server.customEvents.GetSelectedInvoiceEvent;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.server.views.subViews.CurrentInvoiceSubView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@PageTitle("Proforma")
@Route("proforma_facturatie")
@Menu(order = 0, icon = LineAwesomeIconUrl.EURO_SIGN_SOLID)
@Component
@Scope("prototype")
public class ProformaInvoiceView extends VerticalLayout implements BeforeEnterObserver {

    CurrentInvoiceSubView currentInvoiceSubView;
    InvoiceService invoiceService;
    CustomerService customerService;

    MenuBar actionBar;

    List<Invoice>allProformaInvoices;

    public ProformaInvoiceView(CurrentInvoiceSubView currentInvoiceSubView,
                               InvoiceService invoiceService,
                               CustomerService customerService) {
        this.currentInvoiceSubView = currentInvoiceSubView;
        this.invoiceService = invoiceService;
        this.customerService = customerService;
    }

    private void loadData(){
        Optional<List<Invoice>> allInvoicesByStatus = invoiceService.getAllInvoicesByFinalInvoice(false);
        if(allInvoicesByStatus.isPresent()){
            currentInvoiceSubView.addItemsToProformaGrid(allInvoicesByStatus.get());
            currentInvoiceSubView.viewAsProformaInvoices();
            currentInvoiceSubView.setSizeFull();
            currentInvoiceSubView.tryToSetPreviousFilters();
            this.removeAll();
            this.setSizeFull();
            this.getStyle()
                    .set("display", "flex")
                    .set("flex-direction", "column");
            this.addClassName("view-wrapper");
            this.getStyle().set("background-color", "#e9ebef");
            this.setPadding(true);
            this.setSpacing(true);

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setWidth("100%");
            horizontalLayout.getStyle()
                    .set("border-radius", "16px")
                    .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
            horizontalLayout.setSpacing(false);
            horizontalLayout.setPadding(false);
            horizontalLayout.add(getActionMenu());
            this.add(horizontalLayout);

            this.add(currentInvoiceSubView);
        }
        else{
            Notification notification = Notification.show("Geen proforma facturen gevonden");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
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
            Map<String, String> params = Map.of(
                    "proforma", "true",
                    "workAddressStreet", ""
            );
            UI.getCurrent().navigate(
                    NewInvoiceView.class,
                    new RouteParameters("id", "none"),
                    QueryParameters.simple(params)
            );
        });


        //add Action MenuBar
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Verwijder geselecteerde proforma",getRemoveOrderClickEvent());
        actionBarLayout.add(backButton,newButton,actionBar);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getRemoveOrderClickEvent() {
        return  (event) -> {
            currentInvoiceSubView.showRemoveNotification();
        };
    }

    private void loadCustomerData(List<Invoice> invoiceList){
        currentInvoiceSubView.addItemsToProformaGrid(invoiceList);
        currentInvoiceSubView.viewAsProformaInvoices();
        currentInvoiceSubView.setSizeFull();
        this.setSizeFull();
        this.add(currentInvoiceSubView);
    }

    @EventListener
    public void handleSelectedInvoiceEvent(GetSelectedInvoiceEvent event) {
        if (UI.getCurrent() != null && UI.getCurrent().equals(UI.getCurrent())) {
            Optional<Invoice> selectedInvoice = Optional.of(event.getSelectedInvoice());
            if(selectedInvoice.get().getBFinalInvoice() == false){
                Map<String, String> params = Map.of(
                        "proforma", "true",
                        "workAddressStreet", ""
                );
                UI.getCurrent().navigate(
                        NewInvoiceView.class,
                        new RouteParameters("id", selectedInvoice.get().getId()),
                        QueryParameters.simple(params)
                );
            }
        }
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Map<String, List<String>> params =
                beforeEnterEvent.getLocation()
                        .getQueryParameters()
                        .getParameters();

        String customerId = Optional.ofNullable(params.get("customerId"))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .orElse(null);


        if(customerId == null){
            loadData();
        }
        else if ((customerId != null) ) {
            List<Invoice> invoicesForCustomer = invoiceService.getInvoicesForCustomer(customerId);
            if((invoicesForCustomer != null) && (!invoicesForCustomer.isEmpty())){
                loadCustomerData(invoicesForCustomer.stream().filter(invoice -> invoice.getBFinalInvoice() == false).collect(Collectors.toList()));
            }
            else{
                Notification notification = Notification.show("Geen facturen gevonden");
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }
        }
    }
}
