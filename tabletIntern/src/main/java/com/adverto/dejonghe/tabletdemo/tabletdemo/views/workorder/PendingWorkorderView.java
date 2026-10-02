package com.adverto.dejonghe.tabletdemo.tabletdemo.views.workorder;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.tabletdemo.tabletdemo.customEvents.AddProductEventListener;
import com.adverto.dejonghe.tabletdemo.tabletdemo.customEvents.GetSelectedWorkOrderEvent;
import com.adverto.dejonghe.tabletdemo.tabletdemo.customEvents.ReloadProductListEvent;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.subViews.SelectProductSubView;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.subViews.ShowImageSubVieuw;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.Icon;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

@PageTitle("Werkbon")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.WRENCH_SOLID)
@Component
@Scope("prototype")
public class PendingWorkorderView extends VerticalLayout implements BeforeEnterObserver, HasUrlParameter<String> {

    EmployeeService employeeService;
    CustomerService customerService;
    ProductService productService;
    SelectProductSubView selectProductSubView;
    WorkOrderService workOrderService;
    GridFsTemplate gridFsTemplate;
    CurrentWorkOrdersSubView currtentWorkOrdersSubVieuw;
    ShowImageSubVieuw showImageSubVieuw;
    AddProductEventListener listener;
    Optional<List<WorkOrder>> allPendingStarters;
    MenuBar actionBar;

    public PendingWorkorderView(ProductService productService,
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
        allPendingStarters = workOrderService.getAllByStatusAndStarter(WorkOrderStatus.RUNNING, true);
        if(allPendingStarters.isPresent()){
            currtentWorkOrdersSubVieuw.addItemsToPendingWorkOrderGrid(allPendingStarters.get());
            currtentWorkOrdersSubVieuw.setAuthorisation(UserFunction.ADMIN);
        }
        else{
            Notification notification = Notification.show("Geen lopenede werkbonnen gevonden");
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
                //get Starter of that WorkOrder
                String starterId = workOrderService.getStarterByLinkedId(event.getSelectedWorkOrder().getId()).get().getId();
                UI.getCurrent().navigate(WorkorderView.class, starterId);
            }
        }
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(Alignment.CENTER);
        actionBarLayout.setPadding(true);

        //add New Button
        Button newButton = new Button(new Icon(VaadinIcon.PLUS));
        newButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
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
        actionBarLayout.add(newButton);
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

