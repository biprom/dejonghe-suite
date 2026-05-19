package com.adverto.dejonghe.server.views.Staff;

import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.repos.EmployeeRepo;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@PageTitle("Techniekers")
@Route("employee")
@Menu(order = 0, icon = LineAwesomeIconUrl.WRENCH_SOLID)
public class EmployeeView extends VerticalLayout implements BeforeEnterObserver {

    EmployeeRepo employeeRepo;
    EmployeeService employeeService;

    VirtualList<List<Employee>> employeeVirtualList;
    Optional<List<Employee>> employees;

    TextField searchField;
    Dialog editDialog;
    Notification deleteWorkorderNotification;

    Binder<Employee>employeeBinder;

    TextField firstNameField;
    TextField lastNameField;
    TextField abbriviationField;
    TextField phoneNumberField;
    Checkbox technicianCheckBox;
    DatePicker birthDayPicker;
    DatePicker startDayPicker;

    Employee employeeToEdit;
    EmployeeCard employeeCardToEdit;

    MenuBar actionBar;

    public EmployeeView(EmployeeService employeeService,
                        EmployeeRepo employeeRepo) {

        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;

        getAllEmployees();
        createReportDelete();
        setUpEditDialog();
        setUpBinder();
        setUpVirtualList();

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
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.add(getActionMenu(),getSearchBar());
        this.add(horizontalLayout);


        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        layout.add(employeeVirtualList);
        layout.expand(employeeVirtualList);
        this.add(layout);
    }

    public Notification createReportDelete() {
        deleteWorkorderNotification = new Notification();
        deleteWorkorderNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteWorkorderNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je deze persoon wil wissen?"), retryBtn,
                createCloseBtn(deleteWorkorderNotification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        deleteWorkorderNotification.add(layout);

        return deleteWorkorderNotification;
    }

    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    //employeeToEdit = employee;
                    //employeeCardToEdit = card;
                    if(employeeToEdit != null){
                        //when filter is selected
                        employees.get().remove(employeeToEdit);
                        employeeService.delete(employeeToEdit);
                        setDataToList();
                    }
                    else{
                        Notification.show("Geen werkbonnen te verwijderen");
                    }
                    notification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    private void setUpBinder() {
        employeeBinder = new Binder<>();
        employeeBinder.forField(firstNameField)
                .asRequired("Voornaam is verplicht")
                .bind(Employee::getFirstName, Employee::setFirstName);
        employeeBinder.forField(lastNameField)
                .asRequired("Naam is verplicht")
                .bind(Employee::getLastName, Employee::setLastName);
        employeeBinder.forField(abbriviationField)
                .asRequired("Afkorting is verplicht")
                .bind(Employee::getAbbreviation, Employee::setAbbreviation);
        employeeBinder.forField(phoneNumberField)
                .bind(Employee::getPhoneNumber, Employee::setPhoneNumber);
        employeeBinder.forField(technicianCheckBox)
                .bind(Employee::getTechnician, Employee::setTechnician);
        employeeBinder.forField(birthDayPicker)
                .bind(Employee::getBirthDate, Employee::setBirthDate);
        employeeBinder.forField(startDayPicker)
                .bind(Employee::getDateOfService, Employee::setDateOfService);
    }

    private void setUpEditDialog() {
        editDialog = new Dialog();
        editDialog.setHeaderTitle("Personeelgegevens");

        VerticalLayout dialogLayout = createDialogLayout();
        editDialog.add(dialogLayout);

        Button saveButton = createSaveButton(editDialog);
        Button cancelButton = new Button("Annuleer", e -> editDialog.close());
        editDialog.getFooter().add(cancelButton);
        editDialog.getFooter().add(saveButton);
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Bewaar", e -> {
            try {
                employeeBinder.writeBean(employeeToEdit);
                employeeRepo.save(employeeToEdit);
                employeeCardToEdit.refreshWith(employeeToEdit);
                Notification.show("Personeelgegevens zijn aangepast.");
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            dialog.close();
            setDataToList();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }


    private VerticalLayout createDialogLayout() {

        firstNameField = new TextField("voornaam");
        lastNameField = new TextField("familienaam");
        abbriviationField = new TextField("afkorting");
        phoneNumberField = new TextField("telefoonnummer");
        technicianCheckBox = new Checkbox("technieker");
        birthDayPicker = new DatePicker("geboortedatum");
        startDayPicker = new DatePicker("gestart op");

        VerticalLayout dialogLayout = new VerticalLayout(firstNameField,
                lastNameField,abbriviationField,phoneNumberField,birthDayPicker,startDayPicker,technicianCheckBox);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }


    private HorizontalLayout getSearchBar() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.setWidth("100%");
        searchField = new TextField();
        searchField.setSizeFull();
        searchField.setPlaceholder("Zoek op naam");
        searchField.addValueChangeListener(event -> {
            Optional<List<Employee>> optEmployee =
                    employeeService.getEmployeeByFirstName(event.getValue())
                            .map(list -> {
                                list.sort(Comparator.comparing(x -> x.getFirstName().replaceFirst("^[’']", ""), String.CASE_INSENSITIVE_ORDER));
                                return list;
                            });
            if((optEmployee.isPresent()) && (optEmployee.get().size() > 0)) {
                List<List<Employee>> rows = chunk(optEmployee.get(), 3);
                employeeVirtualList.setItems(rows);
            }
            else{
                Notification.show("Geen personeel gevonden");
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

    private void getAllEmployees() {
        employees = employeeService.getAll()
                .map(list -> list.stream()
                        .sorted(Comparator.comparing(
                                c -> c.getFirstName().replaceFirst("^[’']", ""),
                                String.CASE_INSENSITIVE_ORDER
                        ))
                        .collect(Collectors.toList())
                );
    }

    private void setUpVirtualList() {

        employeeVirtualList = new VirtualList();
        employeeVirtualList.setHeight("100%");
        employeeVirtualList.setRenderer(new ComponentRenderer<>(row -> {
            HorizontalLayout rowLayout = new HorizontalLayout();
            //rowLayout.setWidthFull();
            rowLayout.setSpacing(true);

            row.forEach(customer -> {
                EmployeeCard card = new EmployeeCard(customer,this);
                card.addClassName("virtual-item");
                card.setWidth("33%");  // 3 kolommen
                rowLayout.add(card);
            });

            return rowLayout;
        }));
        setDataToList();
    }

    private void setDataToList() {
        List<List<Employee>> rows = chunk(employees.get(), 3);
        employeeVirtualList.setItems(rows);
    }

    public void openRemoveDialog(Employee employee, EmployeeCard card) {
        employeeToEdit = employee;
        employeeCardToEdit = card;
        deleteWorkorderNotification.open();
    }

    public void openEditDialog(Employee employee, EmployeeCard card) {
        employeeToEdit = employee;
        employeeCardToEdit = card;
        employeeBinder.readBean(employeeToEdit);
        editDialog.open();
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        actionBarLayout.setPadding(true);

        //add New Button
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {
            Employee employeeToAdd = new Employee();
            employeeToAdd.setFirstName("");
            employeeToAdd.setLastName("");
            employeeToAdd.setAbbreviation("");
            EmployeeCard employeeCard = new EmployeeCard(employeeToAdd,this);
            employees.get().add(employeeToAdd);
            openEditDialog(employeeToAdd,employeeCard);
        });
        actionBarLayout.add(newButton);
        return actionBarLayout;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
