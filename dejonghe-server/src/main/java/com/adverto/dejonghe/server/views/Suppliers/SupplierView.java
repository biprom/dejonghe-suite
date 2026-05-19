package com.adverto.dejonghe.server.views.Suppliers;

import com.adverto.dejonghe.common.dbservices.SupplierService;
import com.adverto.dejonghe.common.entities.product.product.Supplier;
import com.adverto.dejonghe.common.repos.SupplierRepo;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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

@PageTitle("Leveranciers")
@Route("suppliers")
@Menu(order = 0, icon = LineAwesomeIconUrl.TRUCK_LOADING_SOLID)
public class SupplierView extends VerticalLayout implements BeforeEnterObserver {

    SupplierRepo supplierRepo;
    SupplierService supplierService;

    VirtualList<List<Supplier>> supplierVirtialList;
    Optional<List<Supplier>> suppliers;
    TextField searchField;

    Dialog editDialog;
    Notification deleteWorkorderNotification;

    Binder<Supplier> supplierBinder;

    TextField nameField;
    TextField streetField;
    TextField zipField;
    TextField cityField;
    TextField countryField;
    TextField vatNumberField;
    TextArea commentField;
    TextArea alertMessage;
    Checkbox alertCheckBox;

    Supplier supplierToEdit;
    SupplierCard supplierCardToEdit;

    public SupplierView(SupplierService supplierService,
                        SupplierRepo supplierRepo) {

        this.supplierService = supplierService;
        this.supplierRepo = supplierRepo;

        getAllSuppliers();
        createReportDelete();
        setUpEditDialog();
        setUpBinder();

        this.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");
        this.addClassName("view-wrapper");
        this.getStyle().set("background-color", "#e9ebef");

        setUpVirtualList();

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
        layout.setHeight("92%");
        layout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        layout.add(supplierVirtialList);
        layout.expand(supplierVirtialList);
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
                    if(supplierToEdit != null){
                        //when filter is selected
                        suppliers.get().remove(supplierToEdit);
                        supplierService.delete(supplierToEdit);
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

    private void setDataToList() {
        List<List<Supplier>> rows = chunk(suppliers.get(), 3);
        supplierVirtialList.setItems(rows);
    }

    private void setUpBinder() {
        supplierBinder = new Binder<>();
        supplierBinder.forField(nameField)
                .asRequired("Naam is verplicht")
                .bind(Supplier::getName, Supplier::setName);
        supplierBinder.forField(streetField)
                .asRequired("Straat is verplicht")
                .bind(Supplier::getStreet, Supplier::setStreet);
        supplierBinder.forField(zipField)
                .asRequired("Postcode is verplicht")
                .bind(Supplier::getZipCode, Supplier::setZipCode);
        supplierBinder.forField(cityField)
                .asRequired("Stad is verplicht")
                .bind(Supplier::getCity, Supplier::setCity);
        supplierBinder.forField(countryField)
                .bind(Supplier::getCountry, Supplier::setCountry);
        supplierBinder.forField(vatNumberField)
                .bind(Supplier::getVatNumber, Supplier::setVatNumber);
        supplierBinder.forField(commentField)
                .bind(Supplier::getComment, Supplier::setComment);
        supplierBinder.forField(alertMessage)
                .bind(Supplier::getAlertMessage, Supplier::setAlertMessage);
        supplierBinder.forField(alertCheckBox)
                .bind(Supplier::getAlert, Supplier::setAlert);
    }

    private void setUpEditDialog() {
        editDialog = new Dialog();
        editDialog.setHeaderTitle("Gegevens leverancier");

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
                supplierBinder.writeBean(supplierToEdit);
                supplierRepo.save(supplierToEdit);
                supplierCardToEdit.refreshWith(supplierToEdit);
                Notification.show("Gegevens leverancier zijn aangepast.");
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

        nameField = new TextField("naam");
        streetField = new TextField("straat + nr");
        zipField = new TextField("postcode");
        cityField = new TextField("stad");
        countryField = new TextField("land");
        vatNumberField = new TextField("btw-nummer");
        commentField = new TextArea("commentaar");
        alertMessage = new TextArea("alarm");
        alertCheckBox = new Checkbox("commentaar alarm");

        VerticalLayout dialogLayout = new VerticalLayout(nameField,
                streetField,zipField,cityField,countryField,vatNumberField,commentField,alertMessage,alertCheckBox);
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
        searchField.setWidth("100%");
        searchField.setPlaceholder("Zoek op naam");
        searchField.addValueChangeListener(event -> {

            //virtualList.removeAll();

            Optional<List<Supplier>> optSupplier =
                    supplierService.getSupplierByName(event.getValue())
                            .map(list -> {
                                list.sort(Comparator.comparing(x -> x.getName().replaceFirst("^[’']", ""), String.CASE_INSENSITIVE_ORDER));
                                return list;
                            });
            if((optSupplier.isPresent()) && (optSupplier.get().size() > 0)) {
                List<List<Supplier>> rows = chunk(optSupplier.get(), 3);
                setDataToList();
            }
            else{
                Notification.show("Geen leveranciers gevonden");
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

    private void getAllSuppliers() {
        suppliers = supplierService.getAllSuppliers()
                .map(list -> list.stream()
                        .sorted(Comparator.comparing(
                                c -> c.getName().replaceFirst("^[’']", ""),
                                String.CASE_INSENSITIVE_ORDER
                        ))
                        .collect(Collectors.toList())
                );
    }

    private void setUpVirtualList() {
        supplierVirtialList = new VirtualList();
        supplierVirtialList.setWidth("100%");
        supplierVirtialList.setHeight("100%");
        supplierVirtialList.setRenderer(new ComponentRenderer<>(row -> {
            HorizontalLayout rowLayout = new HorizontalLayout();
            rowLayout.setWidthFull();
            rowLayout.setSpacing(true);

            row.forEach(supplier -> {
                SupplierCard card = new SupplierCard(supplier,this);
                card.addClassName("virtual-item");
                card.setWidth("33%");  // 3 kolommen
                rowLayout.add(card);
            });

            return rowLayout;
        }));
        setDataToList();
    }

    public void openEditDialog(Supplier employee, SupplierCard card) {
        supplierToEdit = employee;
        supplierCardToEdit = card;
        supplierBinder.readBean(supplierToEdit);
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
            Supplier supplierToAdd = new Supplier();
            supplierToAdd.setName("");
            SupplierCard employeeCard = new SupplierCard(supplierToAdd,this);
            suppliers.get().add(supplierToAdd);
            openEditDialog(supplierToAdd,employeeCard);
        });
        actionBarLayout.add(newButton);
        return actionBarLayout;
    }

    public void openRemoveDialog(Supplier employee, SupplierCard card) {
        supplierToEdit = employee;
        supplierCardToEdit = card;
        deleteWorkorderNotification.open();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
