package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.server.customEvents.GetSelectedWorkOrderEvent;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.server.services.workorder.WorkorderViewState;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Component
@Scope("prototype")
public class CurrentWorkOrdersSubView extends VerticalLayout {

    WorkOrderService workOrderService;
    ApplicationEventPublisher eventPublisher;
    WorkorderViewState workorderViewState;

    TreeGrid<WorkOrder> pendingWorkOrdersGrid;
    List<WorkOrder>selectedWorkOrders;
    TreeData<WorkOrder> treeData;
    HeaderRow headerRow;

    TextField filterSubject;
    TextField filterName;
    TextField filterResponsible;

    WorkOrder selectedWorkOrder;
    List<WorkOrder>workOrderBundleList = new ArrayList<>();
    Notification deleteWorkorderNotification;
    Notification detachWorkorderNotification;

    UserFunction userFunction = UserFunction.TECHNICIAN;


    @Autowired
    public CurrentWorkOrdersSubView(WorkOrderService workOrderService,
                                    ApplicationEventPublisher eventPublisher,
                                    WorkorderViewState workorderViewState) {
        this.workOrderService = workOrderService;
        this.eventPublisher = eventPublisher;
        this.workorderViewState = workorderViewState;

        setUpfilters();
        createReportDelete();
        createReportDetach();
        this.add(setUpGrid());
        this.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
    }

    private void setUpfilters() {
        filterSubject = new TextField();
        filterSubject.setPlaceholder("Commentaar");
        filterSubject.setWidth("100%");

        filterResponsible = new TextField();
        filterResponsible.setPlaceholder("Verantwoordelijk");
        filterResponsible.setWidth("100%");

        filterName = new TextField();
        filterName.setPlaceholder("Klantnaam,Werfadres,Stad,Straat");
        filterName.setWidth("100%");

        filterSubject.addValueChangeListener(event -> {
            workorderViewState.setComment(filterSubject.getValue());
            addItemsToPendingWorkOrderGridFromFilter(
                    selectedWorkOrders.stream()
                            .filter(workOrder ->
                                    workOrder.getWorkOrderHeaderList().stream()
                                            .anyMatch(header ->
                                                    header.getDescription() != null &&
                                                            header.getDescription().toLowerCase().contains(event.getValue().toLowerCase())
                                            )
                            )
                            .collect(Collectors.toList())
            );
            pendingWorkOrdersGrid.getDataProvider().refreshAll();
        });

        filterResponsible.addValueChangeListener(event -> {
            workorderViewState.setResponsibility(filterResponsible.getValue());
            addItemsToPendingWorkOrderGridFromFilter(
                    selectedWorkOrders.stream()
                            .filter(workOrder -> getActiveMasterEmployees(workOrder).toLowerCase().contains(event.getValue().toLowerCase())).collect(Collectors.toList())
            );
            pendingWorkOrdersGrid.getDataProvider().refreshAll();
        });

        filterName.addValueChangeListener(event -> {
            String search = event.getValue();

            if (!search.isBlank()) {
                String searchLower = search.toLowerCase();

                List<WorkOrder> collect = selectedWorkOrders.stream()
                        .filter(workOrder -> {
                            Address address = workOrder.getWorkAddress();

                            return address != null &&
                                    (containsIgnoreCase(address.getAddressName(), searchLower)
                                            || containsIgnoreCase(address.getCity(), searchLower)
                                            || containsIgnoreCase(address.getStreet(), searchLower)
                                            || containsIgnoreCase(address.getCustomerName(), searchLower));
                        })
                        .toList();

                addItemsToPendingWorkOrderGridFromFilter(collect);
            } else {
                addItemsToPendingWorkOrderGrid(selectedWorkOrders);
            }

            pendingWorkOrdersGrid.getDataProvider().refreshAll();
        });
    }

    private boolean containsIgnoreCase(String value, String search) {
        return value != null && value.toLowerCase().contains(search);
    }

    private Grid<WorkOrder> setUpGrid() {
        pendingWorkOrdersGrid = new TreeGrid<>();
        treeData = new TreeData<>();
        pendingWorkOrdersGrid.addClassName("rounded-tree");

        pendingWorkOrdersGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        pendingWorkOrdersGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        pendingWorkOrdersGrid.setSelectionMode(TreeGrid.SelectionMode.MULTI);
        Grid.Column<WorkOrder> columnAddress = pendingWorkOrdersGrid.addHierarchyColumn(workOrder -> workOrder.getWorkAddress().getAddressName()).setHeader("Naam").setFlexGrow(2);
        Grid.Column<WorkOrder> dateColumn = pendingWorkOrdersGrid
                .addColumn(workorder -> workorder.getWorkDateTime()
                        .toLocalDate()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .setHeader("Datum")
                .setSortable(true)
                .setComparator(workorder -> workorder.getWorkDateTime().toLocalDate())
                .setFlexGrow(1);
        Grid.Column<WorkOrder> columnSubject = pendingWorkOrdersGrid.addColumn(workOrder -> {
            return getWorkOrderDiscriptions(workOrder);
        }).setHeader("Omschrijving").setFlexGrow(7);
        Grid.Column<WorkOrder> columnResponsible = pendingWorkOrdersGrid.addColumn(workOrder -> getActiveMasterEmployees(workOrder)).setHeader("Verantwoordelijke").setFlexGrow(1);


        pendingWorkOrdersGrid.addItemClickListener(event -> {

            //if selectionColumn is aangeklikt -> don't trigger event!
            if(event.getColumn() == null){
                return;
            }

            selectedWorkOrder = treeData.getParent(event.getItem());
            if (selectedWorkOrder == null) {
                //if no parent then selected WorkOrder is the parent!
                selectedWorkOrder = event.getItem();
                //UI.getCurrent().getPage().executeJs("window.open($0, '_blank')", "/werkbon/"+event.getItem().getId());
            }
            eventPublisher.publishEvent(new GetSelectedWorkOrderEvent(this, selectedWorkOrder));
        });

        pendingWorkOrdersGrid.asMultiSelect().addValueChangeListener(event -> {
            if(event.isFromClient()){
                Set<WorkOrder> oldSelection = event.getOldValue();
                Set<WorkOrder> newSelection = event.getValue();

                Set<WorkOrder> added = new HashSet<>(newSelection);
                added.removeAll(oldSelection);

                Set<WorkOrder> removed = new HashSet<>(oldSelection);
                removed.removeAll(newSelection);

                added.forEach(selected -> {
                    List<WorkOrder> children = treeData.getChildren(selected);
                    children.forEach(child -> pendingWorkOrdersGrid.select(child));
                });

                removed.forEach(deselected -> {
                    List<WorkOrder> children = treeData.getChildren(deselected);
                    children.forEach(child -> pendingWorkOrdersGrid.deselect(child));
                });
            }
        });

        pendingWorkOrdersGrid.sort(GridSortOrder.desc(dateColumn).build());
        headerRow = pendingWorkOrdersGrid.appendHeaderRow();
        headerRow.getCell(columnAddress).setComponent(filterName);
        headerRow.getCell(columnSubject).setComponent(filterSubject);
        headerRow.getCell(columnResponsible).setComponent(filterResponsible);

        return pendingWorkOrdersGrid;
    }

    private String getWorkOrderDiscriptions(WorkOrder workOrder) {
        String discription = "";

        if(workOrder.getWorkOrderHeaderList().get(0).getDescription() != null){
            discription = discription + workOrder.getWorkOrderHeaderList().get(0).getDescription() + "\n";
        }
        if(workOrder.getWorkOrderHeaderList().get(1).getDescription() != null){
            discription = discription + workOrder.getWorkOrderHeaderList().get(1).getDescription() + "\n";
        }
        if(workOrder.getWorkOrderHeaderList().get(2).getDescription() != null){
            discription = discription + workOrder.getWorkOrderHeaderList().get(2).getDescription() + "\n";
        }
        if(workOrder.getWorkOrderHeaderList().get(3).getDescription() != null){
            discription = discription + workOrder.getWorkOrderHeaderList().get(3).getDescription() + "\n";
        }
        return discription;
    }

    private String getActiveMasterEmployees(WorkOrder workOrder) {
        String masterEmployees = "";

        if(workOrder.getMasterEmployeeTeam1() != null){
            masterEmployees = masterEmployees + workOrder.getMasterEmployeeTeam1().getFirstName() + " " + workOrder.getMasterEmployeeTeam1().getLastName().substring(0,1) + ", ";
        }
        if(workOrder.getMasterEmployeeTeam2() != null){
            masterEmployees = masterEmployees + workOrder.getMasterEmployeeTeam2().getFirstName() + " " + workOrder.getMasterEmployeeTeam2().getLastName().substring(0,1) + ", ";
        }
        if(workOrder.getMasterEmployeeTeam3() != null){
            masterEmployees = masterEmployees + workOrder.getMasterEmployeeTeam3().getFirstName() + " " + workOrder.getMasterEmployeeTeam3().getLastName().substring(0,1) + ", ";
        }
        if(workOrder.getMasterEmployeeTeam4() != null){
            masterEmployees = masterEmployees + workOrder.getMasterEmployeeTeam4().getFirstName() + " " + workOrder.getMasterEmployeeTeam4().getLastName().substring(0,1) + ", ";
        }
        return masterEmployees;
    }

    public void addItemsToPendingWorkOrderGrid(List<WorkOrder>workOrderList){
        if((workOrderList != null) && (workOrderList.size() > 0)){
            pendingWorkOrdersGrid.setVisible(true);
            selectedWorkOrders = workOrderList;
            treeData.clear();
            for(WorkOrder parent : workOrderList){
                treeData.addItem(null, parent);
                Optional<List<WorkOrder>> optChildren = getCoupledWorkOrders(parent);
                if(!optChildren.isEmpty()){
                    for (WorkOrder child : optChildren.get()) {
                        treeData.addItem(parent, child);
                    }
                }
            }
            TreeDataProvider<WorkOrder> dataProvider = new TreeDataProvider<>(treeData);
            pendingWorkOrdersGrid.setDataProvider(dataProvider);
        }
        else{
            pendingWorkOrdersGrid.setVisible(false);
            Notification notification = Notification.show("Geen lopende Werkbonnen");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    public void addItemsToPendingWorkOrderGridFromFilter(List<WorkOrder>filteredWorkOrderList){
        if((filteredWorkOrderList != null) && (filteredWorkOrderList.size() > 0)){
            pendingWorkOrdersGrid.setVisible(true);
            treeData.clear();
            for(WorkOrder parent : filteredWorkOrderList){
                treeData.addItem(null, parent);
                Optional<List<WorkOrder>> optChildren = getCoupledWorkOrders(parent);
                if(!optChildren.isEmpty()){
                    for (WorkOrder child : optChildren.get()) {
                        treeData.addItem(parent, child);
                    }
                }
            }
            TreeDataProvider<WorkOrder> dataProvider = new TreeDataProvider<>(treeData);
            pendingWorkOrdersGrid.setDataProvider(dataProvider);

        }
        else{
            treeData.clear();
            TreeDataProvider<WorkOrder> dataProvider = new TreeDataProvider<>(treeData);
            pendingWorkOrdersGrid.setDataProvider(dataProvider);
            Notification notification = Notification.show("Geen lopende Werkbonnen");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    public Optional<List<WorkOrder>> getCoupledWorkOrders(WorkOrder starterWorkOrder) {
        return workOrderService.getCoupledWorkOrders(starterWorkOrder.getLinkedWorkOrders());
    }

    public Notification createReportDelete() {
        deleteWorkorderNotification = new Notification();
        deleteWorkorderNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteWorkorderNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je deze werkbon wil verwijderen? Als je een hoofdwerkbon selecteerd worden gekoppelde werkbonnen ook verwijderd!!!"), retryBtn,
                createCloseBtn(deleteWorkorderNotification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        deleteWorkorderNotification.add(layout);

        return deleteWorkorderNotification;
    }

    public Notification createReportDetach() {
        detachWorkorderNotification = new Notification();
        detachWorkorderNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> detachWorkorderNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je deze werkbon wil ontkoppelen?"), retryBtn,
                createDetachBtn(detachWorkorderNotification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        detachWorkorderNotification.add(layout);

        return detachWorkorderNotification;
    }

    public Button createDetachBtn(Notification notification) {
        Button connectBtn = new Button(VaadinIcon.CONNECT.create(),
                clickEvent -> {
                    if(!pendingWorkOrdersGrid.getSelectedItems().isEmpty()){
                        for(WorkOrder workOrder : pendingWorkOrdersGrid.getSelectedItems()){
                            List<String> copyList = new ArrayList<>(workOrder.getLinkedWorkOrders()); // Kopie voor veilige verwijdering

                            //if there are linked workorders remove them from list and set them as starter.
                            for (String linkedWorkOrderId : copyList) {
                                Optional<WorkOrder> optLinkedWorkOrder = workOrderService.getWorkOrderById(linkedWorkOrderId);
                                if (optLinkedWorkOrder.isPresent()) {
                                    optLinkedWorkOrder.get().setStarter(true);
                                    workOrderService.save(optLinkedWorkOrder.get());
                                }

                                workOrder.getLinkedWorkOrders().remove(linkedWorkOrderId);
                            }
                            workOrder.setStarter(true);
                            workOrderService.save(workOrder);


                            Optional<WorkOrder> starterByLinkedId = workOrderService.getStarterByLinkedId(workOrder.getId());
                            if (starterByLinkedId.isPresent()) {
                                WorkOrder starter = starterByLinkedId.get();
                                if (starter.getLinkedWorkOrders() != null && !starter.getLinkedWorkOrders().isEmpty()) {
                                    starter.getLinkedWorkOrders().remove(workOrder.getId());
                                    workOrderService.save(starter);
                                }
                            }
                        }
                        notification.close();
                        Optional<List<WorkOrder>>allFinishedStarters = workOrderService.getAllByStatusAndStarter(WorkOrderStatus.FINISHED, true);
                        if(allFinishedStarters.isPresent()){
                            addItemsToPendingWorkOrderGrid(allFinishedStarters.get());
                        }
                    }
                });
        connectBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return connectBtn;
    }

    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    if(selectedWorkOrders != null){
                        //when filter is selected
                        selectedWorkOrders.remove(selectedWorkOrder);
                        workOrderService.delete(selectedWorkOrder);
                        //now delete if workorder to delete is in a parent
                        try{
                            Optional<WorkOrder> optParent = workOrderService.getStarterByLinkedId(selectedWorkOrder.getId());
                            if(optParent.isPresent()){
                                optParent.get().getLinkedWorkOrders().remove(selectedWorkOrder.getId());
                            }
                            workOrderService.save(optParent.get());
                        }
                        catch(Exception e){

                        }
                        addItemsToPendingWorkOrderGrid(selectedWorkOrders);
                        pendingWorkOrdersGrid.getDataProvider().refreshAll();
                        Notification.show("Werkbon is verwijderd");
                    }
                    else{
                        Notification.show("Geen werkbonnen te verwijderen");
                    }
                    notification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }


    public List<WorkOrder> getSelectedBundeledWorkOrders() {
        workOrderBundleList.clear();
        for(WorkOrder workOrder : pendingWorkOrdersGrid.getSelectedItems()){
            List<WorkOrder>selectedChildren = treeData.getChildren(workOrder);
            if(selectedChildren != null){
                workOrderBundleList.add(workOrder);
                workOrderBundleList.addAll(selectedChildren);
                return workOrderBundleList;
            }
            else{
                workOrderBundleList.add(workOrder);
                return (workOrderBundleList);
            }
        }
        return workOrderBundleList;
    }

    public Optional<Set<WorkOrder>> getSelectedWorkOrders(){
        Set<WorkOrder> selectedItems = pendingWorkOrdersGrid.getSelectedItems();
        return Optional.of(selectedItems);
    }


    public void loadFilters(){
        if((workorderViewState.getCustomer() != null) && (workorderViewState.getCustomer().length() > 0)){
            filterName.setValue(workorderViewState.getCustomer());
        }
        if((workorderViewState.getComment() != null) && (workorderViewState.getComment().length() > 0)){
            filterSubject.setValue(workorderViewState.getComment());
        }
        if((workorderViewState.getResponsibility() != null) && (workorderViewState.getResponsibility().length() > 0)){
            filterResponsible.setValue(workorderViewState.getResponsibility());
        }
    }

    public void setAuthorisation(UserFunction userFunction) {
        this.userFunction = userFunction;
        if(this.userFunction.compareTo(UserFunction.ADMIN)==0 ){
            //do nothing because grid is allready MULTI Select
        }
        else{
            pendingWorkOrdersGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        }
    }

    public void showDetachNotification() {
        selectedWorkOrder = pendingWorkOrdersGrid.getSelectedItems().stream().findFirst().get();
        detachWorkorderNotification.open();
    }

    public void showRemoveNotification() {
        selectedWorkOrder = pendingWorkOrdersGrid.getSelectedItems().stream().findFirst().get();
        deleteWorkorderNotification.open();
    }
}
