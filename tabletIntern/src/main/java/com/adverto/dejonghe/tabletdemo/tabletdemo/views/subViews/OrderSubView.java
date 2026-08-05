package com.adverto.dejonghe.tabletdemo.tabletdemo.views.subViews;

import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.dbservices.OrderService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.enums.product.ORDER_PRODUCT_STATUS;
import com.adverto.dejonghe.common.entities.product.product.Order;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Component
@Scope("prototype")
public class OrderSubView extends VerticalLayout {

    private Runnable closeAction;


    ProductService productService;
    EmployeeService employeeService;
    OrderService orderService;

    Binder<Order>orderBinder;

    H2 titleSpan = new H2("");

    DatePicker datePicker;
    ComboBox<ORDER_PRODUCT_STATUS> orderComboBox;
    TextField tfSelectedAmount;
    ComboBox<Employee>cbEmployee;

    Button saveButton;

    Product selectedProductToOrder;

    public OrderSubView(ProductService productService,
                        EmployeeService employeeService,
                        OrderService orderService) {

        this.productService = productService;
        this.employeeService = employeeService;
        this.orderService = orderService;

        setUpSaveButton();
        setUpEditComponents();
        this.add(setUpLayout());
        setUpBinder();
    }

    private void setUpSaveButton() {
        saveButton = new Button("Bestel artikel!");
        saveButton.setWidthFull();
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(e -> {
            if (orderBinder.isValid()) {
                Order order = new Order();
                order.setDate(datePicker.getValue());
                order.setSelectedAmount(Double.valueOf(tfSelectedAmount.getValue()));
                order.setStatus(orderComboBox.getValue());
                order.setEmployee(cbEmployee.getValue());
                order.setComment(selectedProductToOrder.getComment());
                order.setInternalName(selectedProductToOrder.getInternalName());
                order.setAbbreviation(selectedProductToOrder.getAbbreviation());
                order.setPositionNumber(selectedProductToOrder.getPositionNumber());
                order.setOrderCode(selectedProductToOrder.getProductCode());
                order.setProductLevel1(selectedProductToOrder.getProductLevel1());
                order.setProductLevel2(selectedProductToOrder.getProductLevel2());
                order.setProductLevel3(selectedProductToOrder.getProductLevel3());
                order.setProductLevel4(selectedProductToOrder.getProductLevel4());
                order.setProductLevel5(selectedProductToOrder.getProductLevel5());
                order.setProductLevel6(selectedProductToOrder.getProductLevel6());
                order.setProductLevel7(selectedProductToOrder.getProductLevel7());
                order.setUnit(selectedProductToOrder.getUnit());
                orderService.save(order);
                Notification.show("Bestelling is bewaard.").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                if (closeAction != null) {
                    closeAction.run();
                }

            } else {
                orderBinder.validate(); // toont validatiefouten in de UI
                Notification.show("Controleer de ingevulde gegevens.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    private void setUpEditComponents() {

        datePicker = new DatePicker();
        orderComboBox = new ComboBox<>();
        tfSelectedAmount = new TextField();
        cbEmployee = new ComboBox<>();

        datePicker.setAriaLabel("Besteldatum");
        datePicker.setValue(LocalDate.now());
        datePicker.setWidthFull();
        orderComboBox.setWidthFull();
        tfSelectedAmount.setLabel("Selecteer hoeveelheid:");
        tfSelectedAmount.setValue("1");
        tfSelectedAmount.setWidthFull();
        cbEmployee.setWidthFull();

        orderComboBox.setEnabled(false);
        orderComboBox.setItems(ORDER_PRODUCT_STATUS.values());
        orderComboBox.setItemLabelGenerator(x -> x.getDiscription());
        orderComboBox.setValue(ORDER_PRODUCT_STATUS.TO_ORDER);

        Optional<List<Employee>> employees = employeeService.getAll();
        if (employees.isPresent()) {

            List<Employee> sortedEmployees = employees.get().stream()
                    .sorted(
                            Comparator.comparingInt(
                                            (Employee e) -> e.getPriority() == 0
                                                    ? Integer.MAX_VALUE
                                                    : e.getPriority()
                                    )
                                    .thenComparing(
                                            Employee::getFirstName,
                                            String.CASE_INSENSITIVE_ORDER
                                    )
                    )
                    .toList();

            cbEmployee.setLabel("Kies een medewerker:");
            cbEmployee.setPlaceholder("Selecteer naam");
            cbEmployee.setClearButtonVisible(true);
            cbEmployee.setItems(sortedEmployees);
            cbEmployee.setItemLabelGenerator(item -> item.getFirstName() + " " + item.getLastName());
        }
    }

    private void setUpBinder() {
        orderBinder = new Binder<>();

        orderBinder.forField(datePicker)
                .bind(Order::getDate, Order::setDate);

        orderBinder.forField(orderComboBox)
                .bind(Order::getStatus, Order::setStatus);

        orderBinder.forField(tfSelectedAmount)
                .asRequired("Mag niet leeg zijn")
                .withNullRepresentation("0.0")
                .withConverter(new StringToDoubleConverter("Gelieve een geldig positief decimaal getal in te vullen aub."))
                .bind(Order::getSelectedAmount, Order::setSelectedAmount);

        orderBinder.forField(cbEmployee)
                .asRequired("Mag niet leeg zijn")
                .bind(Order::getEmployee, Order::setEmployee);
    }

    private VerticalLayout setUpLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setMargin(false);
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        verticalLayout.setHeight("100%");
        verticalLayout.setWidth("100%");

        verticalLayout.add(titleSpan);
        verticalLayout.add(datePicker);
        verticalLayout.add(orderComboBox);
        verticalLayout.add(tfSelectedAmount);
        verticalLayout.add(cbEmployee);
        verticalLayout.add(saveButton);

        return verticalLayout;
    }


    public void setSelectedProdcut(Product selectedProduct) {
        if(selectedProduct != null) {
            selectedProductToOrder = selectedProduct;
            titleSpan.setText("Bestelling voor :  " + selectedProduct.getInternalName());
        }
    }

    public void setCloseAction(Runnable closeAction) {
        this.closeAction = closeAction;
    }
}
