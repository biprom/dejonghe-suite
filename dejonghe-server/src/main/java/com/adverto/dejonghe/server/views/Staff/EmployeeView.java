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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@PageTitle("Techniekers")
@Route("employee")
@Menu(order = 0, icon = LineAwesomeIconUrl.WRENCH_SOLID)
public class EmployeeView extends VerticalLayout implements BeforeEnterObserver {

    private final EmployeeRepo employeeRepo;
    private final EmployeeService employeeService;

    private VirtualList<List<Employee>> employeeVirtualList;
    private List<Employee> employees = new ArrayList<>();

    private TextField searchField;
    private Button clearFilterButton;
    private Dialog editDialog;
    private Notification deleteWorkorderNotification;

    private Binder<Employee> employeeBinder;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField abbriviationField;
    private TextField phoneNumberField;
    private Checkbox technicianCheckBox;
    private DatePicker birthDayPicker;
    private DatePicker startDayPicker;
    private IntegerField prioField;

    private Employee employeeToEdit;

    public EmployeeView(EmployeeService employeeService, EmployeeRepo employeeRepo) {
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;

        getAllEmployees();
        createReportDelete();
        setUpEditDialog();
        setUpBinder();
        setUpVirtualList();

        getStyle().set("display", "flex").set("flex-direction", "column");
        addClassName("view-wrapper");
        getStyle().set("background-color", "#e9ebef");
        setPadding(true);
        setSpacing(true);
        setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.add(getActionMenu(), getSearchBar());
        add(horizontalLayout);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        layout.add(employeeVirtualList);
        layout.expand(employeeVirtualList);
        add(layout);
    }

    public Notification createReportDelete() {
        deleteWorkorderNotification = new Notification();
        deleteWorkorderNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button cancelButton = new Button("Annuleer", event -> deleteWorkorderNotification.close());
        cancelButton.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        HorizontalLayout layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je deze persoon wil wissen?"),
                cancelButton, createCloseBtn(deleteWorkorderNotification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        deleteWorkorderNotification.add(layout);
        return deleteWorkorderNotification;
    }

    public Button createCloseBtn(Notification notification) {
        Button removeButton = new Button(VaadinIcon.TRASH.create(), event -> {
            if (employeeToEdit != null && employeeToEdit.getId() != null) {
                employeeService.delete(employeeToEdit);
                employeeToEdit = null;
                getAllEmployees();
                setDataToList();
                Notification.show("Personeelslid verwijderd.");
            } else {
                Notification.show("Geen personeelslid geselecteerd.");
            }
            notification.close();
        });
        removeButton.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeButton;
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
        employeeBinder.forField(prioField)
                .asRequired("Prioriteit is verplicht")
                .withValidator(value -> value != null, "Prioriteit is verplicht")
                .bind(Employee::getPriority, Employee::setPriority);
    }

    private void setUpEditDialog() {
        editDialog = new Dialog();
        editDialog.setHeaderTitle("Personeelgegevens");
        editDialog.add(createDialogLayout());
        editDialog.getFooter().add(
                new Button("Annuleer", event -> editDialog.close()),
                createSaveButton(editDialog));
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Bewaar", event -> {
            if (employeeToEdit == null) {
                return;
            }
            try {
                employeeBinder.writeBean(employeeToEdit);
                employeeRepo.save(employeeToEdit);
                getAllEmployees();
                setDataToList();
                dialog.close();
                Notification.show("Personeelgegevens zijn bewaard.");
            } catch (ValidationException exception) {
                Notification message = Notification.show("Controleer de verplichte velden.");
                message.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (RuntimeException exception) {
                Notification message = Notification.show("Personeelgegevens konden niet worden bewaard.");
                message.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
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
        prioField = new IntegerField("prioriteit");

        VerticalLayout dialogLayout = new VerticalLayout(firstNameField,
                lastNameField, abbriviationField, phoneNumberField, birthDayPicker,
                startDayPicker, technicianCheckBox, prioField);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        return dialogLayout;
    }

    private HorizontalLayout getSearchBar() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        clearFilterButton = new Button(VaadinIcon.CLOSE.create());
        searchLayout.setWidth("100%");
        searchField = new TextField();
        searchField.setSizeFull();
        searchField.setPlaceholder("Zoek op naam");
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> setDataToList());
        clearFilterButton.addClickListener(event -> searchField.setValue(""));
        searchLayout.add(clearFilterButton, searchField);
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
        employees = employeeService.getAll().orElseGet(ArrayList::new).stream()
                .sorted(Comparator
                        .comparingInt((Employee employee) -> {
                            Integer priority = employee.getPriority();
                            return (priority == null || priority <= 0)
                                    ? Integer.MAX_VALUE : priority;
                        })
                        .thenComparing(employee -> safe(employee.getFirstName()),
                                String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    private void setUpVirtualList() {
        employeeVirtualList = new VirtualList<>();
        employeeVirtualList.setHeight("100%");
        employeeVirtualList.setRenderer(new ComponentRenderer<>(row -> {
            HorizontalLayout rowLayout = new HorizontalLayout();
            rowLayout.setWidthFull();
            rowLayout.setSpacing(true);
            for (Employee employee : row) {
                EmployeeCard card = new EmployeeCard(employee, this);
                card.addClassName("virtual-item");
                card.setWidth("33%");
                rowLayout.add(card);
            }
            return rowLayout;
        }));
        setDataToList();
    }

    private void setDataToList() {
        if (employeeVirtualList == null) {
            return;
        }
        String query = searchField == null ? "" : searchField.getValue().trim().toLowerCase(Locale.ROOT);
        List<Employee> shown = employees;
        if (!query.isEmpty()) {
            shown = employees.stream()
                    .filter(employee -> (safe(employee.getFirstName()) + " "
                            + safe(employee.getLastName()) + " "
                            + safe(employee.getAbbreviation()))
                            .toLowerCase(Locale.ROOT).contains(query))
                    .collect(Collectors.toList());
        }
        employeeVirtualList.setItems(chunk(shown, 3));
    }

    public void openRemoveDialog(Employee employee, EmployeeCard card) {
        employeeToEdit = employee;
        deleteWorkorderNotification.open();
    }

    public void openEditDialog(Employee employee, EmployeeCard card) {
        employeeToEdit = employee;
        employeeBinder.readBean(employeeToEdit);
        editDialog.open();
    }

    public void openWeeklyScheduleDialog(Employee employee) {
        if (employee == null || employee.getId() == null) {
            Notification.show("Bewaar eerst het personeelslid.");
            return;
        }
        new EmployeeWeeklyScheduleDialog(employee, schedule -> {
            // Load the latest record so this operation only changes the week schedule.
            Employee current = employeeRepo.findById(employee.getId()).orElse(employee);
            current.setWeeklySchedule(schedule);
            employeeRepo.save(current);
            getAllEmployees();
            setDataToList();
        }).open();
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        actionBarLayout.setPadding(true);
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {
            Employee employeeToAdd = new Employee();
            employeeToAdd.setFirstName("");
            employeeToAdd.setLastName("");
            employeeToAdd.setPriority(0);
            employeeToAdd.setAbbreviation("");
            // Only add to MongoDB/the list after Save. Cancel creates no empty employee.
            openEditDialog(employeeToAdd, null);
        });
        actionBarLayout.add(newButton);
        return actionBarLayout;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // No additional navigation logic needed.
    }
}
