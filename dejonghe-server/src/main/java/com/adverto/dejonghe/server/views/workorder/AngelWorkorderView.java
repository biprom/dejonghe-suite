package com.adverto.dejonghe.server.views.workorder;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.server.customEvents.AddProductEventListener;
import com.adverto.dejonghe.server.customEvents.GetSelectedWorkOrderEvent;
import com.adverto.dejonghe.server.customEvents.ReloadProductListEvent;
import com.adverto.dejonghe.server.views.subViews.CurrentWorkOrdersSubView;
import com.adverto.dejonghe.server.views.subViews.SelectProductSubView;
import com.adverto.dejonghe.server.views.subViews.ShowImageSubVieuw;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.*;

@PageTitle("Werkbon")
@Route("werkbonnenZwevend")
@Menu(order = 0, icon = LineAwesomeIconUrl.WRENCH_SOLID)
@Component
@Scope("prototype")
public class AngelWorkorderView extends VerticalLayout implements BeforeEnterObserver,HasUrlParameter<String> {

    EmployeeService employeeService;
    CustomerService customerService;
    ProductService productService;
    SelectProductSubView selectProductSubView;
    WorkOrderService workOrderService;
    GridFsTemplate gridFsTemplate;
    CurrentWorkOrdersSubView currtentWorkOrdersSubVieuw;
    ShowImageSubVieuw showImageSubVieuw;
    AddProductEventListener listener;
    List<WorkOrder> exceptions;
    MenuBar actionBar;

    public AngelWorkorderView(ProductService productService,
                              CustomerService customerService,
                              EmployeeService employeeService,
                              SelectProductSubView selectProductSubView,
                              WorkOrderService workOrderService,
                              GridFsTemplate gridFsTemplate,
                              CurrentWorkOrdersSubView currtentWorkOrdersSubVieuw,
                              ShowImageSubVieuw showImageSubVieuw,
                              AddProductEventListener listener) {
        this.productService = productService;
        this.selectProductSubView = selectProductSubView;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.workOrderService = workOrderService;
        this.gridFsTemplate = gridFsTemplate;
        this.currtentWorkOrdersSubVieuw = currtentWorkOrdersSubVieuw;
        this.showImageSubVieuw = showImageSubVieuw;
        this.listener = listener;
    }


    private void loadData(){

        Optional<List<WorkOrder>> allRunning =
                workOrderService.getAllByStatusAndStarter(WorkOrderStatus.RUNNING, true);

        Optional<List<WorkOrder>> allFinished =
                workOrderService.getAllByStatusAndStarter(WorkOrderStatus.FINISHED, true);

        Optional<List<WorkOrder>> allInvoiced =
                workOrderService.getAllByStatusAndStarter(WorkOrderStatus.INVOICED, true);

        Optional<List<WorkOrder>> all =
                workOrderService.getAll();

        if (all.isPresent()) {

            exceptions = new ArrayList<>();

            Set<String> idsToRemove = new HashSet<>();

            allRunning.ifPresent(list ->
                    list.forEach(w -> {
                        idsToRemove.add(w.getId());
                        if(w.getLinkedWorkOrders() != null && w.getLinkedWorkOrders().size() > 0){
                            idsToRemove.addAll(w.getLinkedWorkOrders());
                        }
                    })
            );

            allFinished.ifPresent(list ->
                    list.forEach(w -> {
                        idsToRemove.add(w.getId());
                        if(w.getLinkedWorkOrders() != null && w.getLinkedWorkOrders().size() > 0){
                            idsToRemove.addAll(w.getLinkedWorkOrders());
                        }
                    })
            );

            allInvoiced.ifPresent(list ->
                    list.forEach(w -> {
                        idsToRemove.add(w.getId());
                        if(w.getLinkedWorkOrders() != null && w.getLinkedWorkOrders().size() > 0){
                            idsToRemove.addAll(w.getLinkedWorkOrders());
                        }
                    })
            );

            exceptions = new ArrayList<>(
                    all.orElse(List.of())
                            .stream()
                            .filter(w -> !idsToRemove.contains(w.getId()))
                            .toList()
            );
        }

        if(exceptions.size() > 0){
            currtentWorkOrdersSubVieuw.addItemsToPendingWorkOrderGrid(exceptions);
            currtentWorkOrdersSubVieuw.setAuthorisation(UserFunction.ADMIN);
        }
        else{
            Notification notification = Notification.show("Geen zwevende werkbonnen gevonden");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    private Button getNavigateToSelectedWorkOrder() {
        Button buttonOpenSelectedWorkOrder = new Button("Geselecteerde werkbon Openen");
        buttonOpenSelectedWorkOrder.setWidth("100%");
        buttonOpenSelectedWorkOrder.addClickListener(event -> {

            Optional<Set<WorkOrder>> selectedWorkOrders = currtentWorkOrdersSubVieuw.getSelectedWorkOrders();
            if (selectedWorkOrders.isPresent() && selectedWorkOrders.get().stream().anyMatch(p -> p.getStarter() == true)) {
                UI.getCurrent().navigate(WorkorderView.class, selectedWorkOrders.get().stream().findFirst().get().getId());
            }
            else{
                //get Starter of that WorkOrder
                String starterId = workOrderService.getStarterByLinkedId(selectedWorkOrders.get().stream().findFirst().get().getId()).get().getId();
                UI.getCurrent().navigate(WorkorderView.class, starterId);
            }
        });
        return buttonOpenSelectedWorkOrder;
    }

    @EventListener
    public void handleReloadEvent(ReloadProductListEvent event) {
        System.out.println("GetSelectedWorkOrderEventTest : " + event.getMessage());
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
                try{
                //get Starter of that WorkOrder
                String starterId = workOrderService.getStarterByLinkedId(event.getSelectedWorkOrder().getId()).get().getId();
                UI.getCurrent().navigate(WorkorderView.class, starterId);
                }
                catch (Exception e){
                    //nessecary to open the floating workorders (has no starters)
                    String starterId = event.getSelectedWorkOrder().getId();
                    UI.getCurrent().navigate(WorkorderView.class, starterId);
                }
            }
        }
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
            UI.getCurrent().navigate(WorkorderView.class);
        });


        //add Action MenuBar
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
//        actie.getSubMenu().addItem("Koppel geselecteerde werkbonnen", getCoupleWorkOrderClickEvent());
//        actie.getSubMenu().addItem("Ontkoppel geslecteerde werkbon", getDecoupleWorkOrderClickEvent());
//        actie.getSubMenu().addItem("Zet werkbon terug open", getOpenWorkOrderClickEvent());
//        actie.getSubMenu().addItem("Maak proforma per dag", getMakeProFormaInvoicePerDayClickEvent());
//        actie.getSubMenu().addItem("Maak samengestelde proforma",getMakeProFormaInvoiceClickEvent());
        actie.getSubMenu().addItem("Verwijder",getRemoveOrderClickEvent());
        actionBarLayout.add(backButton,newButton, actionBar);
        return actionBarLayout;
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
        loadData();
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
        this.add(currtentWorkOrdersSubVieuw);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {

    }
}

