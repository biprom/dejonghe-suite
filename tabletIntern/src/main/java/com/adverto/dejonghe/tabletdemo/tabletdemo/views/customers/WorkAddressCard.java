package com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Contact;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers.installation.InstallationView;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.workorder.WorkorderView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.router.QueryParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;


public class WorkAddressCard extends Card {

    Customer customer;
    Address workAddress;

    CustomerService customerService;
    MenuBar actionBar;

    private Grid<Contact> contactGrid = new Grid<>();
    private List<Contact> selectedContactList;

    Dialog editWorkAddressDialog;
    Dialog editWorkAddressContactsDialog;

    Binder<Address> addressBinder;

    TextField nameField;
    TextField streetField;
    TextField zipField;
    TextField cityField;
    TextField countryField;

    TextField tfCoordinates;
    TextField tfDistance;

//    TextField tfRoadTaxAtego;
//    TextField tfRoadTaxActros;
//    TextField tfRoadArocs;

    public WorkAddressCard() {

        this.getStyle().set("background-color", "#fbf6ea");
        setUpContactGrid();
        setUpEditInvoiceDialog();
        setUpEditContactsDialog();
        setUpBinders();
    }


    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    if((customer != null) && (workAddress != null)) {
                        customer.getAddresses().remove(workAddress);
                        customerService.save(customer);
                        actionBar.removeAll();
                        UI.getCurrent().navigate(
                                CustomerView.class, customer.getId());
                        Notification.show("Werfadres is verwijderd.");
                    }
                    else{
                        Notification.show("Geen werfadressen te verwijderen");
                    }
                    notification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Bewaar", e -> {
            try {
                addressBinder.writeBean(workAddress);
                customerService.save(customer);
                actionBar.removeAll();
                setWorkAddress(workAddress);
                Notification.show("Gegevens klant zijn aangepast.");
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;

        setUpCardLayout();

    }

    private void setUpEditInvoiceDialog() {
        editWorkAddressDialog = new Dialog();
        editWorkAddressDialog.setHeaderTitle("Gegevens klant");

        VerticalLayout dialogLayout = createDialogLayout();
        editWorkAddressDialog.add(dialogLayout);

        Button saveButton = createSaveButton(editWorkAddressDialog);
        Button cancelButton = new Button("Annuleer", e -> editWorkAddressDialog.close());
        editWorkAddressDialog.getFooter().add(cancelButton);
        editWorkAddressDialog.getFooter().add(saveButton);
    }

    private VerticalLayout createDialogLayout() {

        nameField = new TextField("naam adres");
        streetField = new TextField("straat + nr");
        zipField = new TextField("postcode");
        cityField = new TextField("stad");
        countryField = new TextField("land");

//        tfRoadTaxAtego = new TextField("Wegentaks Atego");
//        tfRoadTaxAtego.setSuffixComponent(new Span("€"));
//        tfRoadArocs = new TextField("Wegentaks Arocs");
//        tfRoadArocs.setSuffixComponent(new Span("€"));
//        tfRoadTaxActros = new TextField("Wegentaks Actros");
//        tfRoadTaxActros.setSuffixComponent(new Span("€"));

        HorizontalLayout coordinatesLayout = new HorizontalLayout();
        coordinatesLayout.setSpacing(true);
        coordinatesLayout.setPadding(false);
        coordinatesLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        tfCoordinates = new TextField("Coördinaten");
        coordinatesLayout.add(tfCoordinates);
        tfCoordinates.setEnabled(false);

        HorizontalLayout distanceLayout = new HorizontalLayout();
        distanceLayout.setSpacing(true);
        distanceLayout.setPadding(false);
        distanceLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        tfDistance = new TextField("Afstand");
        tfDistance.setSuffixComponent(new Span("km"));
        distanceLayout.add(tfDistance);



        VerticalLayout dialogLayout = new VerticalLayout(nameField,
                streetField,zipField,cityField,countryField,coordinatesLayout,distanceLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }


    private void setUpBinders() {

        addressBinder = new Binder<>();
        addressBinder.forField(nameField)
                .withNullRepresentation("")
                .asRequired("Naam is verplicht")
                .withValidator(name -> {
                    return !customerService.checkIfWorkAddressNameExists(name,customer.getId());
                }, "Deze naam is al in gebruik")
                .bind(Address::getAddressName, Address::setAddressName);
        addressBinder.forField(streetField)
                .withNullRepresentation("")
                .asRequired("Straat is verplicht")
                .bind(Address::getStreet, Address::setStreet);
        addressBinder.forField(zipField)
                .withNullRepresentation("")
                .asRequired("Postcode is verplicht")
                .bind(Address::getZip, Address::setZip);
        addressBinder.forField(cityField)
                .withNullRepresentation("")
                .asRequired("Stad is verplicht")
                .bind(Address::getCity, Address::setCity);
        addressBinder.forField(countryField)
                .withNullRepresentation("")
                .bind(Address::getCountry, Address::setCountry);
        addressBinder.forField(tfCoordinates)
                .withNullRepresentation("")
                .bind(
                        address -> {
                            if (address.getCoordinates() == null) {
                                return "";
                            }
                            return address.getCoordinates().getLongitude() + " "
                                    + address.getCoordinates().getLatitude();
                        },
                        null
                );
        addressBinder.forField(tfDistance)
                .withNullRepresentation("")
                .withConverter(
                        new StringToDoubleConverter("Afstand moet een decimaal getal zijn")
                )
                .bind(Address::getDistance, Address::setDistance);
//        addressBinder.forField(tfRoadTaxAtego)
//                .withNullRepresentation("")
//                .withConverter(
//                        new StringToDoubleConverter("Afstand moet een decimaal getal zijn")
//                )
//                .bind(Address::getRoadTaxAtego, Address::setRoadTaxAtego);
//        addressBinder.forField(tfRoadArocs)
//                .withNullRepresentation("")
//                .withConverter(
//                        new StringToDoubleConverter("Afstand moet een decimaal getal zijn")
//                )
//                .bind(Address::getRoadTaxArocs, Address::setRoadTaxArocs);
//        addressBinder.forField(tfRoadTaxActros)
//                .withNullRepresentation("")
//                .withConverter(
//                        new StringToDoubleConverter("Afstand moet een decimaal getal zijn")
//                )
//                .bind(Address::getRoadTaxActros, Address::setRoadTaxActros);
        addressBinder.addValueChangeListener(x -> {
            try {
                addressBinder.writeBean(workAddress);
            } catch (ValidationException e) {

            }
            customerService.save(customer);
            actionBar.removeAll();
            setWorkAddress(workAddress);
            Notification.show("Gegevens werfadres zijn aangepast.");
        });
    }

    private void setUpEditContactsDialog() {
        editWorkAddressContactsDialog = new Dialog();
        editWorkAddressContactsDialog.setWidth("90%");
        editWorkAddressContactsDialog.setHeight("50%");
        editWorkAddressContactsDialog.setHeaderTitle("Gegevens contacten");

        VerticalLayout dialogLayout = createEditContactLayout();
        editWorkAddressContactsDialog.add(dialogLayout);

        Button saveButton = createEditContactSaveButton(editWorkAddressContactsDialog);
        Button cancelButton = new Button("Annuleer", e -> editWorkAddressContactsDialog.close());
        editWorkAddressContactsDialog.getFooter().add(cancelButton);
        editWorkAddressContactsDialog.getFooter().add(saveButton);
    }

    private VerticalLayout createEditContactLayout() {
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setSizeFull();
        dialogLayout.add(contactGrid);
        return dialogLayout;
    }

    private void setUpContactGrid() {
        contactGrid.setWidth("100%");
        contactGrid.removeAllColumns();
        contactGrid.addComponentColumn( item -> {
            TextField tfFirstName = new TextField();
            tfFirstName.setWidth("100%");
            tfFirstName.setValue(Optional.ofNullable(item.getFirstName()).orElse(""));
            tfFirstName.addValueChangeListener(e -> {
                item.setFirstName(tfFirstName.getValue());
                customerService.save(customer);
            });
            return tfFirstName;
        }).setAutoWidth(true).setHeader("Voornaam");
        contactGrid.addComponentColumn( item -> {
            TextField tfLastName = new TextField();
            tfLastName.setWidth("100%");
            tfLastName.setValue(Optional.ofNullable(item.getLastName()).orElse(""));
            tfLastName.addValueChangeListener(e -> {
                item.setLastName(tfLastName.getValue());
                customerService.save(customer);
            });
            return tfLastName;
        }).setAutoWidth(true).setHeader("Familienaam");
        contactGrid.addComponentColumn( item -> {
            TextField tfFunction = new TextField();
            tfFunction.setWidth("100%");
            if(item.getFunction() != null){
                tfFunction.setValue(item.getFunction());
            }
            else{
                tfFunction.setValue("");
            }
            tfFunction.addValueChangeListener(e -> {
                item.setFunction(tfFunction.getValue());
                customerService.save(customer);
            });
            return tfFunction;
        }).setAutoWidth(true).setHeader("Functie");
        contactGrid.addComponentColumn( item -> {
            TextField tfEmail = new TextField();
            tfEmail.setWidth("100%");
            tfEmail.setValue(Optional.ofNullable(item.getEmail()).orElse(""));
            tfEmail.addValueChangeListener(e -> {
                item.setEmail(tfEmail.getValue());
                customerService.save(customer);
            });
            return tfEmail;
        }).setAutoWidth(true).setHeader("e-mail");
        contactGrid.addComponentColumn( item -> {
            TextField tfPhone = new TextField();
            tfPhone.setWidth("100%");
            tfPhone.setValue(Optional.ofNullable(item.getPhone()).orElse(""));
            tfPhone.addValueChangeListener(e -> {
                item.setPhone(tfPhone.getValue());
                customerService.save(customer);
            });
            return tfPhone;
        }).setAutoWidth(true).setHeader("Telefoon");
        contactGrid.addComponentColumn( item -> {
            TextField tfCellphone = new TextField();
            tfCellphone.setWidth("100%");
            tfCellphone.setValue(Optional.ofNullable(item.getCellphone()).orElse(""));
            tfCellphone.addValueChangeListener(e -> {
                item.setCellphone(tfCellphone.getValue());
                customerService.save(customer);
            });
            return tfCellphone;
        }).setAutoWidth(true).setHeader("GSM");

        contactGrid.addComponentColumn( item -> {
            Checkbox checkbActive = new Checkbox();
            checkbActive.setWidth("100%");
            checkbActive.setValue(Optional.ofNullable(item.getActive()).orElse(false));
            checkbActive.addValueChangeListener(e -> {
                item.setActive(checkbActive.getValue());
                customerService.save(customer);
            });
            return checkbActive;
        }).setAutoWidth(true).setHeader("Actief");

        contactGrid.addComponentColumn( item -> {
            Button addButton = new Button(new Icon(VaadinIcon.PLUS));
            addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            addButton.addClickListener(e -> {
                Contact contact = new Contact();
                contact.setActive(true);
                contact.setCellphone("");
                contact.setPhone("");
                contact.setEmail("");
                contact.setFirstName("");
                contact.setLastName("");
                contact.setFunction("");
                selectedContactList.add(contact);
                contactGrid.getDataProvider().refreshAll();
            });
            return addButton;
        }).setAutoWidth(true).setHeader("");

        contactGrid.addComponentColumn( item -> {
            Button addButton = new Button(new Icon(VaadinIcon.MINUS));
            addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            addButton.addClickListener(e -> {
                selectedContactList.remove(item);
                contactGrid.getDataProvider().refreshAll();
            });
            return addButton;
        }).setAutoWidth(true).setHeader("");
    }

    private Button createEditContactSaveButton(Dialog editInvoiceContactsDialog) {
        Button saveButton = new Button("Bewaar", e -> {
            try {
                addressBinder.writeBean(workAddress);
                customerService.save(customer);
                actionBar.removeAll();
                setWorkAddress(workAddress);
                Notification.show("Gegevens werfadres zijn aangepast.");
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            editInvoiceContactsDialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }

    private String safe(String value) {
        return (value == null || value.equals("N/A")) ? "" : value;
    }


    private void setUpCardLayout() {

        this.removeAll();

        this.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL
        );

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        if((workAddress.getAddressName() != null) && (workAddress.getAddressName().length() > 0)) {
            Div title = new Div(new Text(workAddress.getAddressName()));
            title.addClassName("card-title");
            title.addSingleClickListener(event->{
                Map<String, List<String>> parameters = new HashMap<>();
                parameters.put(
                        "workAddressName", List.of(workAddress.getAddressName()));
                UI.getCurrent().navigate(
                        InstallationView.class,
                        new QueryParameters(parameters)
                );
            });
            horizontalLayout.add(title);
        }
        else{
            Div title = new Div(new Text(customer.getName()));
            title.addClassName("card-title");
            title.addSingleClickListener(event->{
                Map<String, List<String>> parameters = new HashMap<>();
                try{
                    parameters.put(
                            "workAddressName",List.of(workAddress.getAddressName()));
                    UI.getCurrent().navigate(
                            InstallationView.class,
                            new QueryParameters(parameters)
                    );
                }
                catch (Exception ex){
                    Notification notification = new Notification();
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.setDuration(3000);
                    notification.setText("Dit werfadres heeft nog geen naam, nu is het een kopie van de klantnaam. Gelieven een naam mee te geven!");
                    notification.open();
                }

            });
            horizontalLayout.add(title);
        }

        this.setTitle(horizontalLayout);

        VerticalLayout contactLayout = new VerticalLayout();
        contactLayout.addClassName("card-address");
        contactLayout.setSpacing(false);
        contactLayout.setPadding(false);
        contactLayout.add(new Span(workAddress.getStreet()));
        contactLayout.add(new Span(workAddress.getZip() + " " + workAddress.getCity()));
        contactLayout.add(new Span("\u00A0"));
        if((workAddress.getContactList() != null)){
            workAddress.getContactList().stream().forEach(contact -> {
                if((contact.getFirstName() != null) && (!contact.getFirstName().matches("N/A"))){
                    String text = String.join(" - ",
                            Stream.of(
                                            safe(contact.getFirstName() + " " + contact.getLastName()),
                                            safe(contact.getCellphone()),
                                            safe(contact.getEmail())
                                    )
                                    .filter(s -> !s.trim().isEmpty())
                                    .toList()
                    );

                    contactLayout.add(new Span(text));
                }
            });
        }
        this.add(contactLayout);

        VerticalLayout transportLayout  = new VerticalLayout();
        transportLayout.addClassName("card-address");
        transportLayout.setPadding(false);
        transportLayout.setSpacing(false);
        transportLayout.add(new Span("\u00A0"));
        transportLayout.add(new Span("Afstand : " + (workAddress.getDistance() != null ? workAddress.getDistance() : 0) + " km"));
        //transportLayout.add(new Span("Breedtegraad : " + (workAddress.getCoordinates() != null ? workAddress.getCoordinates().getLatitude() : 0)));
        //transportLayout.add(new Span("Lengtegraad : " + (workAddress.getCoordinates() != null ? workAddress.getCoordinates().getLongitude() : 0)));
        transportLayout.add(new Span("\u00A0"));
//        transportLayout.add(new Span("Wegentaks Atego : " + (workAddress.getRoadTaxAtego() != null ? workAddress.getRoadTaxAtego() : 0.0) + " €"));
//        transportLayout.add(new Span("Wegentaks Arocs : " + (workAddress.getRoadTaxArocs() != null ? workAddress.getRoadTaxArocs() : 0.0) + " €"));
//        transportLayout.add(new Span("Wegentaks Actros : " + (workAddress.getRoadTaxActros() != null ? workAddress.getRoadTaxActros() : 0.0) + " €"));
        this.add(transportLayout);

        this.add(getActionMenu());

    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setWidth("100%");

        VerticalLayout actionVLayout = new VerticalLayout();
        actionVLayout.setWidth("30%");
        actionVLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");
        //actie.getSubMenu().addItem("Pas aan werfadres", editWorkAddress());
        //actie.getSubMenu().addItem("Wijzig contactpersonen", editWorkAddressContacts());
        //actie.getSubMenu().addItem("Maak werkbon", generateWorkOrder());

        actionVLayout.add(actionBar);

        VerticalLayout layout = new VerticalLayout();
        actionBarLayout.addToStart(layout);
        actionBarLayout.addToEnd(actionVLayout);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> generateWorkOrder() {
        return event -> {
            Map<String, String> params = Map.of(
                    "workAddressStreet", workAddress.getStreet()
            );

            UI.getCurrent().navigate(
                    WorkorderView.class,
                    QueryParameters.simple(params)
            );
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> editWorkAddressContacts() {
        return event -> {
            addressBinder.readBean(workAddress);
            selectedContactList = workAddress.getContactList();
            contactGrid.setItems(selectedContactList);
            editWorkAddressContactsDialog.open();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> editWorkAddress() {
        return event -> {
            editWorkAddressFunction();
        };
    }

    private void editWorkAddressFunction() {
        //if customer has no filled workaddress -> fill in the invoice parameters
        List<Address> invoiceAddress = customer.getAddresses().stream().filter(x -> (x.getInvoiceAddress() != null) && (x.getInvoiceAddress() == true)).collect(Collectors.toList());
        if((workAddress.getAddressName() != null) && (workAddress.getAddressName().length() > 0)){
            //keep name
        }
        else{
            workAddress.setAddressName(customer.getName());
        }
        if((workAddress.getStreet() != null) && (workAddress.getStreet().length() > 0)){
            //keep name
        }
        else{
            workAddress.setStreet(invoiceAddress.get(0).getStreet());
        }
        if((workAddress.getZip() != null) && (workAddress.getZip().length() > 0)){
            //keep name
        }
        else{
            workAddress.setZip(invoiceAddress.get(0).getZip());
        }
        if((workAddress.getCity() != null) && (workAddress.getCity().length() > 0)){
            //keep name
        }
        else{
            workAddress.setCity(invoiceAddress.get(0).getCity());
        }

        addressBinder.readBean(workAddress);

        editWorkAddressDialog.open();
    }


    public void setWorkAddress(Customer customer, Address workAddress) {

        this.customer = customer;
        this.workAddress = workAddress;

        if((this.workAddress.getAddressName() != null) && (this.workAddress.getAddressName().length() > 0)){
            //Do nothing
        }
        else{
            //set Customername as workaddressname
            this.workAddress.setAddressName(customer.getAddresses().stream().filter(y -> y.getInvoiceAddress() == true).findFirst().get().getCustomerName());
        }


        if((workAddress.getContactList() != null) && (workAddress.getContactList().size() >= 1)){
            this.selectedContactList = workAddress.getContactList();
        }
        else{
            this.selectedContactList = null;
        }

        setUpCardLayout();

    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

}
