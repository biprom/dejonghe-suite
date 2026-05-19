package com.adverto.dejonghe.server.views.workorder;

import com.adverto.dejonghe.common.dbservices.*;
import com.adverto.dejonghe.server.customEvents.AddProductEventListener;
import com.adverto.dejonghe.server.customEvents.GetSelectedWorkOrderEvent;
import com.adverto.dejonghe.server.customEvents.ReloadProductListEvent;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.server.services.invoice.InvoiceServices;
import com.adverto.dejonghe.server.views.subViews.CurrentWorkOrdersSubView;
import com.adverto.dejonghe.server.views.subViews.SelectProductSubView;
import com.adverto.dejonghe.server.views.subViews.ShowImageSubVieuw;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
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
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import java.util.*;
import java.util.stream.Collectors;

@PageTitle("Werkbon")
@Route("werkbonnenAfgewerkt")
@Menu(order = 0, icon = LineAwesomeIconUrl.WRENCH_SOLID)
@Component
@Scope("prototype")
public class FinishedWorkorderView extends VerticalLayout implements BeforeEnterObserver,HasUrlParameter<String> {

    private final InvoiceServices invoiceServices;
    EmployeeService employeeService;
    CustomerService customerService;
    ProductService productService;
    SelectProductSubView selectProductSubView;
    WorkOrderService workOrderService;
    GridFsTemplate gridFsTemplate;
    CurrentWorkOrdersSubView currtentWorkOrdersSubVieuw;
    ShowImageSubVieuw showImageSubVieuw;
    AddProductEventListener listener;
    InvoiceServices createOngoingInvoiceService;
    InvoiceService invoiceService;

    Optional<List<WorkOrder>> allFinishedStarters;
    MenuBar actionBar;

    public FinishedWorkorderView(ProductService productService,
                                 CustomerService customerService,
                                 EmployeeService employeeService,
                                 SelectProductSubView selectProductSubView,
                                 WorkOrderService workOrderService,
                                 GridFsTemplate gridFsTemplate,
                                 CurrentWorkOrdersSubView currtentWorkOrdersSubVieuw,
                                 ShowImageSubVieuw showImageSubVieuw,
                                 AddProductEventListener listener,
                                 InvoiceServices createOngoingInvoiceService, InvoiceServices invoiceServices,
                                 InvoiceService invoiceService) {
        this.productService = productService;
        this.selectProductSubView = selectProductSubView;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.workOrderService = workOrderService;
        this.gridFsTemplate = gridFsTemplate;
        this.currtentWorkOrdersSubVieuw = currtentWorkOrdersSubVieuw;
        this.showImageSubVieuw = showImageSubVieuw;
        this.listener = listener;
        this.createOngoingInvoiceService = createOngoingInvoiceService;
        this.invoiceServices = invoiceServices;
        this.invoiceService = invoiceService;

    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        actionBarLayout.setPadding(true);

        //add New Button
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {
            UI.getCurrent().navigate(WorkorderView.class);
        });

        //add Action MenuBar
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Koppel geselecteerde werkbonnen", getCoupleWorkOrderClickEvent());
        actie.getSubMenu().addItem("Ontkoppel geslecteerde werkbon", getDecoupleWorkOrderClickEvent());
        actie.getSubMenu().addItem("Zet werkbon terug open", getOpenWorkOrderClickEvent());
        actie.getSubMenu().addItem("Maak proforma per dag", getMakeProFormaInvoicePerDayClickEvent());
        actie.getSubMenu().addItem("Maak samengestelde proforma",getMakeProFormaInvoiceClickEvent());
        actie.getSubMenu().addItem("Verwijder",getRemoveOrderClickEvent());
        actionBarLayout.add(newButton,actionBar);
        return actionBarLayout;
    }

    @EventListener
    public void handleReloadEvent(ReloadProductListEvent event) {
        if (UI.getCurrent() != null && UI.getCurrent().equals(UI.getCurrent())) {
            loadData();
        }
    }

    @EventListener
    public void handleSelectedWorkOrderEvent(GetSelectedWorkOrderEvent event) {
        if (UI.getCurrent() != null && UI.getCurrent().equals(UI.getCurrent())) {
            //load WorkOrder what is double clicked!
            Optional<WorkOrder> selectedWorkOrder = Optional.of(event.getSelectedWorkOrder());
            if (selectedWorkOrder.isPresent() && selectedWorkOrder.get().getStarter() == true) {
                UI.getCurrent().navigate(WorkorderView.class, event.getSelectedWorkOrder().getId());
            }
            else{
                //get Starter of that WorkOrder
                String starterId = workOrderService.getStarterByLinkedId(event.getSelectedWorkOrder().getId()).get().getId();
                UI.getCurrent().navigate(WorkorderView.class, starterId);
            }
        }
    }

    public void loadInvoiceData(String invoiceId) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(invoiceId);
        if((invoice.isPresent()) && (invoice.get().getWorkOrderList() != null) && (invoice.get().getWorkOrderList().size() > 0)) {
            currtentWorkOrdersSubVieuw.addItemsToPendingWorkOrderGrid(invoice.get().getWorkOrderList().stream().filter(x -> x.getStarter()).collect(Collectors.toList()));
            currtentWorkOrdersSubVieuw.setAuthorisation(UserFunction.ADMIN);
        }
        else{
            Notification notification = Notification.show("Geen starters gevonden in deze factuur");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    public void loadData() {
        allFinishedStarters = workOrderService.getAllByStatusAndStarter(WorkOrderStatus.FINISHED, true);
        if(allFinishedStarters.isPresent()){
            currtentWorkOrdersSubVieuw.addItemsToPendingWorkOrderGrid(allFinishedStarters.get());
            currtentWorkOrdersSubVieuw.setAuthorisation(UserFunction.ADMIN);
            //currtentWorkOrdersSubVieuw.setSizeFull();
        }
        else{
            Notification notification = Notification.show("Geen lopenede werkbonnen gevonden");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getCoupleWorkOrderClickEvent() {
        return event -> {
            Optional<Set<WorkOrder>> selectedWorkOrders = currtentWorkOrdersSubVieuw.getSelectedWorkOrders();

            // All selected WorkOrders need to be Starters to couple!
            if (selectedWorkOrders.isPresent() && selectedWorkOrders.get().stream().allMatch(p -> p.getStarter())) {

                // Find oldest WorkOrder to set as starter, rest is non-starter
                Optional<WorkOrder> oldestWorkOrder = selectedWorkOrders.get().stream()
                        .min(Comparator.comparing(WorkOrder::getWorkDateTime));

                if (oldestWorkOrder.isPresent()) {
                    for (WorkOrder workOrder : selectedWorkOrders.get()) {
                        if (!workOrder.getId().equals(oldestWorkOrder.get().getId())) {
                            workOrder.setStarter(false);
                            workOrderService.save(workOrder);

                            // If oldest WorkOrder does not contain a linkedWorkOrders list, make one
                            if (oldestWorkOrder.get().getLinkedWorkOrders() == null) {
                                oldestWorkOrder.get().setLinkedWorkOrders(new ArrayList<>());
                            }

                            // Add id to oldest WorkOrder's linkedWorkOrders
                            oldestWorkOrder.get().getLinkedWorkOrders().add(workOrder.getId());
                        }
                    }

                    workOrderService.save(oldestWorkOrder.get());
                    loadData();
                }

            } else {
                Notification notification = Notification.show(
                        "Werkbonnen die worden gekoppeld moeten van het type 'starter' zijn");
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getDecoupleWorkOrderClickEvent() {
        return  (event) -> {
            currtentWorkOrdersSubVieuw.showDetachNotification();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getOpenWorkOrderClickEvent() {
        return event -> {
            List<WorkOrder> selectedBundeledWorkOrders = currtentWorkOrdersSubVieuw.getSelectedBundeledWorkOrders();
            if ((selectedBundeledWorkOrders != null) && (!selectedBundeledWorkOrders.isEmpty())) {
                selectedBundeledWorkOrders.forEach(workOrder -> {
                    if(workOrder != null){
                        workOrder.setWorkOrderStatus(WorkOrderStatus.RUNNING);
                        workOrderService.save(workOrder);
                    }
                });
                loadData();
            }
            else{
                Notification.show("Geen geselecteerde werkbonnen!");
            }
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getMakeProFormaInvoicePerDayClickEvent() {
        return event -> {
            Optional<Set<WorkOrder>> selectedWorkOrders = currtentWorkOrdersSubVieuw.getSelectedWorkOrders();
            if (selectedWorkOrders.isPresent()) {
                Invoice invoice = createOngoingInvoiceService.getnerateInvoicePerDay(selectedWorkOrders.get());
                invoiceService.save(invoice);
                workOrderService.deleteAll(selectedWorkOrders.get());
                loadData();
            }
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getMakeProFormaInvoiceClickEvent() {
        return event -> {
            Optional<Set<WorkOrder>> selectedWorkOrders = currtentWorkOrdersSubVieuw.getSelectedWorkOrders();
            if (selectedWorkOrders.isPresent()) {
                Invoice invoice = createOngoingInvoiceService.generateMergedInvoice(selectedWorkOrders.get());
                invoiceService.save(invoice);
                workOrderService.deleteAll(selectedWorkOrders.get());
                loadData();
            }
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getRemoveOrderClickEvent() {
        return  (event) -> {
            currtentWorkOrdersSubVieuw.showRemoveNotification();
        };
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.removeAll();
        this.setSizeFull();
        this.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");
        this.addClassName("view-wrapper");

        currtentWorkOrdersSubVieuw.setWidth("100%");
        currtentWorkOrdersSubVieuw.setHeight("100%");

        Map<String, List<String>> params =
                beforeEnterEvent.getLocation()
                        .getQueryParameters()
                        .getParameters();

        String invoiceId = Optional.ofNullable(params.get("invoiceId"))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .orElse(null);

        if(invoiceId != null){
            loadInvoiceData(invoiceId);
        }
        else{
            loadData();
        }
        currtentWorkOrdersSubVieuw.loadFilters();

        this.setWidth("100%");
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

        this.add(horizontalLayout);
        this.add(currtentWorkOrdersSubVieuw);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {

    }
}

