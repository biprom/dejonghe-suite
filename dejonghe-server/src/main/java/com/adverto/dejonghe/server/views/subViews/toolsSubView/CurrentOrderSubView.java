package com.adverto.dejonghe.server.views.subViews.toolsSubView;

import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.dbservices.OrderService;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.enums.product.ORDER_PRODUCT_STATUS;
import com.adverto.dejonghe.common.entities.product.product.Order;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Component
@Scope("prototype")
public class CurrentOrderSubView extends VerticalLayout {

    OrderService orderService;
    EmployeeService employeeService;

    Grid<Order> orderGrid;
    HeaderRow headerRow;

    ComboBox<ORDER_PRODUCT_STATUS> orderStatusFilter;
    TextField filterComment;
    TextField filterNumber;
    TextField filterName;
    ComboBox<Employee> filterTechnician;
    Button clearFilterButton;

    Order selectedOrder;
    Set<Order> orderListToRemove;
    Notification deleteOrderNotification;

    Grid.Column<Order> columnOrderStatus;
    Grid.Column<Order> actionOrderColumn;

    ListDataProvider<Order> dataProvider;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));


    @Autowired
    public CurrentOrderSubView(OrderService orderService, EmployeeService employeeService) {
        this.orderService = orderService;
        this.employeeService = employeeService;
        setUpNumberFormat();
        setUpfilters();
        createReportDelete();
        this.add(setUpGrid());
        this.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
    }

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    private void setUpfilters() {

        clearFilterButton = new Button(VaadinIcon.CLOSE.create());

        orderStatusFilter = new ComboBox<>();

        filterComment = new TextField();
        filterComment.setWidth("100%");
        filterComment.setPlaceholder("Commentaar");

        filterNumber = new TextField();
        filterNumber.setWidth("100%");
        filterNumber.setPlaceholder("Nummer");

        filterName = new TextField();
        filterName.setWidth("100%");
        filterName.setPlaceholder("Naam,BTW-nr,Werfadres,Stad,Straat,artikelen");

        filterTechnician = new ComboBox<>();
        filterTechnician.setWidth("100%");
        filterTechnician.setPlaceholder("Technieker");
        filterTechnician.setItems(employeeService.getAll().get());
        filterTechnician.setItemLabelGenerator(x -> x.getAbbreviation());

        orderStatusFilter.setItems(ORDER_PRODUCT_STATUS.values());
        orderStatusFilter.setItemLabelGenerator(ORDER_PRODUCT_STATUS::getDiscription);
        orderStatusFilter.setWidth("100%");

        orderStatusFilter.addValueChangeListener(event -> {
            addItemsToPendingOrderGridFromFilter();
        });

        filterComment.addValueChangeListener(event -> {
            addItemsToPendingOrderGridFromFilter();
        });

        filterNumber.addValueChangeListener(event -> {
            addItemsToPendingOrderGridFromFilter();
        });


        filterName.addValueChangeListener(event -> {
            addItemsToPendingOrderGridFromFilter();
        });

        filterTechnician.addValueChangeListener(event -> {
            addItemsToPendingOrderGridFromFilter();
        });

        clearFilterButton.addClickListener(e -> {
            filterTechnician.setValue(filterTechnician.getEmptyValue());
            filterName.setValue("");
            filterNumber.setValue("");
            filterComment.setValue("");
            orderStatusFilter.setValue(orderStatusFilter.getEmptyValue());
        });
    }


    private Grid<Order> setUpGrid() {
        orderGrid = new Grid<>();
        orderGrid.addClassName("rounded-tree");
        orderGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        orderGrid.addClassName("my-bold-footer");
        orderGrid.appendFooterRow();

        orderGrid.addColumn(order -> {
            if(order.getOrderDate() != null){
                return order.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
            else{
                return "";
            }
        }).setHeader("Besteld op").setFlexGrow(1);
        Grid.Column<Order> amountColumn = orderGrid.addColumn(order -> {
            return order.getSelectedAmount();
        }).setHeader("Aantal").setFlexGrow(1).setSortable(true);
        Grid.Column<Order> columnName = orderGrid.addColumn(order -> {
            return order.getInternalName();
        }).setHeader("Naam").setFlexGrow(2).setSortable(true);
        Grid.Column<Order> columnOrderNumber = orderGrid.addColumn(order -> {
            return order.getOrderCode();
        }).setHeader("Code").setFlexGrow(1).setSortable(true);
        Grid.Column<Order> columnComment = orderGrid.addColumn(order -> order.getComment()).setHeader("Commentaar").setFlexGrow(2);
        Grid.Column<Order> columnOrderDate = orderGrid.addColumn(invoice -> invoice.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setHeader("Aanvraag").setFlexGrow(1);
        Grid.Column<Order> nameColumn = orderGrid.addColumn(order -> {
            if(order.getEmployee().getAbbreviation() != null){
                return order.getEmployee().getAbbreviation();
            }
            else{
                return "";
            }
        }).setHeader("Technieker").setFlexGrow(1);

        columnOrderStatus = orderGrid.addComponentColumn(item -> {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setSpacing(false);
            horizontalLayout.setPadding(false);
            horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

            if((item.getStatus() != null) && (item.getStatus() == ORDER_PRODUCT_STATUS.TO_ORDER) ){

                Span badge = new Span("TE BESTELLEN");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(160,160,160,0.9), rgba(100,100,100,0.9))"
                );

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("font-weight", "bold");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(120,120,120,0.45)");

                horizontalLayout.add(badge);
                return horizontalLayout;
            }
            else if((item.getStatus() != null) && (item.getStatus() == ORDER_PRODUCT_STATUS.ORDERED)){

                Span badge = new Span("BESTELD");

                badge.getStyle().set("background",
                        "linear-gradient(135deg, rgba(0,140,255,0.85), rgba(0,80,220,0.85))");
                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set("font-weight", "600");
                badge.getStyle().set("box-shadow", "0 8px 24px rgba(0,80,220,0.85)");

                horizontalLayout.add(badge);
            }
            else if((item.getStatus() != null) && (item.getStatus() == ORDER_PRODUCT_STATUS.DELIVERED)){
                Span badge = new Span("GELEVERD");
                badge.getElement().getThemeList().add("badge success");
                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");
                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");
                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(40,200,120,0.9), rgba(20,150,90,0.85))"
                );

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(40,200,120,0.9)");
                badge.getStyle().set("font-weight", "600");

                horizontalLayout.add(badge);

            }
            else{
                Span badge = new Span(" ");
                badge.getStyle().set("background-color", "transparent");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "18px");
                badge.getStyle().set("border-radius", "35px");
                return badge;
            }
            return horizontalLayout;
        }).setHeader("Status");


         actionOrderColumn = orderGrid.addComponentColumn(item -> {
             return getActionInvoiceButton(item);
         }).setHeader("Actie").setFlexGrow(2);

        orderGrid.sort(GridSortOrder.desc(columnOrderNumber).build());

        orderGrid.addItemClickListener(event -> {

            //if selectionColumn is aangeklikt -> don't trigger event!
            if(event.getColumn() == null){
                return;
            }
            //generate event so the invoice can be opened from motherView (only if invoice is not send to Billit.
            selectedOrder = event.getItem();
        });

        headerRow = orderGrid.appendHeaderRow();
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(clearFilterButton,filterName);
        headerRow.getCell(columnName).setComponent(headerLayout);
        headerRow.getCell(columnOrderNumber).setComponent(filterNumber);
        headerRow.getCell(columnComment).setComponent(filterComment);
        headerRow.getCell(columnOrderStatus).setComponent(orderStatusFilter);
        headerRow.getCell(nameColumn).setComponent(filterTechnician);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        headerRow.getCell(columnOrderDate).setComponent(horizontalLayout);

        orderGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        orderGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        orderGrid.addThemeVariants(GridVariant.LUMO_COMPACT);

        return orderGrid;
    }



    private com.vaadin.flow.component.Component getActionInvoiceButton(Order item) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        MenuBar actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Te bestellen",setToOrder(item));
        actie.getSubMenu().addItem("Besteld",setOrdered(item));
        actie.getSubMenu().addItem("Geleverd",setDelivered(item));
        horizontalLayout.add(actionBar);
        return horizontalLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> setDelivered(Order item) {
        return event -> {
            item.setStatus(ORDER_PRODUCT_STATUS.DELIVERED);
            orderGrid.getDataProvider().refreshItem(item);
            orderService.save(item);
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> setOrdered(Order item) {
        return event -> {
            item.setStatus(ORDER_PRODUCT_STATUS.ORDERED);
            item.setOrderDate(LocalDate.now());
            orderGrid.getDataProvider().refreshItem(item);
            orderService.save(item);
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> setToOrder(Order item) {
        return event -> {
            item.setStatus(ORDER_PRODUCT_STATUS.TO_ORDER);
            item.setOrderDate(null);
            orderGrid.getDataProvider().refreshItem(item);
            orderService.save(item);
        };
    }

    public void addItemsToOrderGrid(List<Order>orderList){
        if((orderList != null) && (orderList.size() > 0)){
            orderGrid.setVisible(true);
            dataProvider = new ListDataProvider<>(orderList);
            orderGrid.setDataProvider(dataProvider);

        }
        else{
            orderGrid.setVisible(false);
            Notification notification = Notification.show("Geen Orders");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    public void addItemsToPendingOrderGridFromFilter(){
        dataProvider.clearFilters();
        dataProvider.addFilter(item -> {

            boolean nameOk = true;
            boolean numberOk = true;
            boolean commentOK = true;
            boolean technicianOK = true;
            boolean statusOk = true;
            boolean internalStatus = true;


            if((!filterName.getValue().isEmpty()) && (item.getInternalName() != null)) {
                nameOk = item.getInternalName().toString().toLowerCase().contains(filterName.getValue().toLowerCase());
            }

            if((!filterNumber.getValue().isEmpty()) && (item.getOrderCode() != null)) {
                numberOk = item.getOrderCode().toString().toLowerCase().contains(filterNumber.getValue().toLowerCase());
            }

            if((!filterComment.getValue().isEmpty()) && (item.getComment() != null)) {
                commentOK = item.getComment().toString().toLowerCase().contains(filterComment.getValue().toLowerCase());
            }

            if((filterTechnician.getValue() != null) && (item.getEmployee() != null) && (item.getEmployee().getTechnician() != null)) {
                technicianOK = item.getEmployee().getAbbreviation().toLowerCase().contains(filterTechnician.getValue().getAbbreviation().toLowerCase());
            }

            if(orderStatusFilter.getValue() != null){
                internalStatus = item.getStatus().equals(orderStatusFilter.getValue());
            }

            return numberOk && nameOk && commentOK && statusOk && internalStatus && technicianOK;
        });

        dataProvider.refreshAll();


    }


    public Notification createReportDelete() {
        deleteOrderNotification = new Notification();
        deleteOrderNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteOrderNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je deze order wil wissen?"), retryBtn,
                createCloseBtn(deleteOrderNotification));
        layout.setAlignItems(Alignment.CENTER);

        deleteOrderNotification.add(layout);

        return deleteOrderNotification;
    }



    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    if((orderListToRemove != null) && (!orderListToRemove.isEmpty())){
                        try{
                            for(Order order: orderListToRemove){
                                dataProvider.getItems()
                                        .removeIf(i -> i.getId().equals(order.getId()));
                            }
                            orderGrid.getDataProvider().refreshAll();
                        }
                        catch(Exception e){
                        }
                        for(Order order: orderListToRemove){
                            orderService.delete(order);
                        }
                        Notification.show("Geselecteerde orders's zijn verwijderd");
                    }
                    else{
                        Notification.show("Geen order's te verwijderen");
                    }
                    notification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    public void showRemoveNotification() {
        if(orderGrid.getSelectedItems().size() >= 0){
            orderListToRemove = orderGrid.getSelectedItems();
            deleteOrderNotification.open();
        }
        else{
            Notification.show("Gelieve minimum 1 factuur te selecteren om te verwijderen");
        }
    }
}
