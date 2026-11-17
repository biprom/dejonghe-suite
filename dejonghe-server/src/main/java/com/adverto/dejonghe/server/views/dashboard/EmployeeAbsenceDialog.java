package com.adverto.dejonghe.server.views.dashboard;

import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.entities.employee.AbsenceType;
import com.adverto.dejonghe.common.entities.employee.DayPart;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.employee.EmployeeAbsence;
import com.adverto.dejonghe.server.services.employee.EmployeeAbsenceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Add, edit and delete dated absences from the dashboard. */
public class EmployeeAbsenceDialog extends Dialog {

    private final EmployeeService employeeService;
    private final EmployeeAbsenceService absenceService;
    private final Runnable onChanged;

    private final ComboBox<Employee> employeeField = new ComboBox<>("Personeelslid");
    private final Select<AbsenceType> typeField = new Select<>();
    private final DatePicker startDateField = new DatePicker("Van");
    private final DatePicker endDateField = new DatePicker("Tot en met");
    private final Select<DayPart> startPartField = new Select<>();
    private final Select<DayPart> endPartField = new Select<>();
    private final TextArea commentField = new TextArea("Interne opmerking (optioneel)");
    private final Button deleteButton = new Button("Verwijderen");

    private String editingId;

    public EmployeeAbsenceDialog(EmployeeService employeeService,
                                 EmployeeAbsenceService absenceService,
                                 Runnable onChanged) {
        this.employeeService = employeeService;
        this.absenceService = absenceService;
        this.onChanged = onChanged;

        setWidth("480px");
        setMaxWidth("calc(100vw - 24px)");

        employeeField.setItemLabelGenerator(EmployeeAbsenceDialog::displayName);
        employeeField.setWidthFull();
        employeeField.setRequired(true);

        typeField.setLabel("Type afwezigheid");
        typeField.setItems(AbsenceType.values());
        typeField.setItemLabelGenerator(AbsenceType::getLabel);
        typeField.setWidthFull();

        startDateField.setLocale(Locale.forLanguageTag("nl-BE"));
        endDateField.setLocale(Locale.forLanguageTag("nl-BE"));
        startDateField.setWidthFull();
        endDateField.setWidthFull();
        startDateField.addValueChangeListener(e -> endDateField.setMin(e.getValue()));

        startPartField.setLabel("Begint vanaf");
        startPartField.setItems(DayPart.values());
        startPartField.setItemLabelGenerator(DayPart::getLabel);
        startPartField.setWidthFull();

        endPartField.setLabel("Eindigt na");
        endPartField.setItems(DayPart.values());
        endPartField.setItemLabelGenerator(DayPart::getLabel);
        endPartField.setWidthFull();

        commentField.setWidthFull();
        commentField.setMaxLength(500);
        commentField.setHelperText("Geen diagnoses of andere medische details noteren.");

        VerticalLayout content = new VerticalLayout(
                employeeField, typeField, startDateField, endDateField,
                startPartField, endPartField, commentField);
        content.setPadding(false);
        content.setSpacing(false);
        add(content);

        Button cancelButton = new Button("Annuleer", e -> close());
        Button saveButton = new Button("Bewaar", e -> save());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.addClickListener(e -> confirmDelete());
        getFooter().add(deleteButton, cancelButton, saveButton);
    }

    public void openNew(LocalDate date) {
        editingId = null;
        setHeaderTitle("Afwezigheid toevoegen");
        loadEmployees(null);
        typeField.setValue(AbsenceType.VACATION);
        LocalDate defaultDate = date == null ? LocalDate.now() : date;
        startDateField.setValue(defaultDate);
        endDateField.setValue(defaultDate);
        startPartField.setValue(DayPart.MORNING);
        endPartField.setValue(DayPart.AFTERNOON);
        commentField.clear();
        deleteButton.setVisible(false);
        open();
    }

    public void openEdit(EmployeeAbsence absence) {
        if (absence == null) {
            return;
        }
        editingId = absence.getId();
        setHeaderTitle("Afwezigheid aanpassen");
        loadEmployees(absence.getEmployeeId());
        typeField.setValue(absence.getType());
        startDateField.setValue(absence.getStartDate());
        endDateField.setValue(absence.getEndDate());
        startPartField.setValue(absence.getStartPart());
        endPartField.setValue(absence.getEndPart());
        commentField.setValue(absence.getComment() == null ? "" : absence.getComment());
        deleteButton.setVisible(true);
        open();
    }

    private void loadEmployees(String employeeId) {
        List<Employee> employees = employeeService.getAll().orElse(List.of()).stream()
                .filter(e -> e != null && e.getId() != null && e.getPriority() != null
                        && e.getPriority() > 0)
                .sorted(Comparator.comparingInt(Employee::getPriority))
                .toList();
        employeeField.setItems(employees);
        employeeField.setValue(employees.stream()
                .filter(e -> e.getId().equals(employeeId))
                .findFirst().orElse(null));
    }

    private void save() {
        Employee employee = employeeField.getValue();
        if (employee == null) {
            Notification.show("Selecteer een personeelslid.");
            return;
        }
        EmployeeAbsence absence = new EmployeeAbsence();
        absence.setId(editingId);
        absence.setEmployeeId(employee.getId());
        absence.setType(typeField.getValue());
        absence.setStartDate(startDateField.getValue());
        absence.setEndDate(endDateField.getValue());
        absence.setStartPart(startPartField.getValue());
        absence.setEndPart(endPartField.getValue());
        absence.setComment(commentField.getValue());

        try {
            absenceService.save(absence);
            close();
            onChanged.run();
            Notification.show("Afwezigheid bewaard.");
        } catch (IllegalArgumentException ex) {
            Notification.show(ex.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    }

    private void confirmDelete() {
        if (editingId == null) {
            return;
        }
        Dialog confirmation = new Dialog();
        confirmation.setHeaderTitle("Afwezigheid verwijderen?");
        confirmation.add(new Paragraph("Deze registratie wordt definitief verwijderd."));
        Button cancel = new Button("Annuleer", e -> confirmation.close());
        Button confirm = new Button("Verwijderen", e -> {
            absenceService.delete(editingId);
            confirmation.close();
            close();
            onChanged.run();
            Notification.show("Afwezigheid verwijderd.");
        });
        confirm.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        confirmation.getFooter().add(cancel, confirm);
        confirmation.open();
    }

    private static String displayName(Employee employee) {
        String first = employee.getFirstName() == null ? "" : employee.getFirstName().trim();
        String last = employee.getLastName() == null ? "" : employee.getLastName().trim();
        return (first + " " + last).trim();
    }
}
