package com.adverto.dejonghe.tablet.views.customers;

import com.vaadin.flow.component.*;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Contact;
import com.adverto.dejonghe.common.entities.customers.Customer;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Stream;


public class InvoiceCard extends Card {

    Customer customer;
    Address invoiceAddress;
    Contact contact;

    CustomerService customerService;
    InvoiceService invoiceService;
    MenuBar actionBar;

    private Grid<Contact> contactGrid = new Grid<>();
    private List<Contact> selectedContactList;


    Binder<Customer> customerBinder;
    Binder<Address> addressBinder;
    Binder<Contact> contactBinder;

    TextField firstNameField;
    TextField lastNameField;
    TextField emailField;
    TextField phoneField;

    TextField nameField;
    TextField streetField;
    TextField zipField;
    TextField cityField;
    TextField countryField;

    TextField vatNumberField;
    Checkbox checkbAgro;
    Checkbox checkbIndustry;
    Checkbox checkbProjectCustomer;

    TextArea commentField;
    TextArea alertMessage;
    Checkbox alertCheckBox;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));

    public InvoiceCard() {
        setUpNumberFormat();
        setUpContactGrid();
        setUpBinders();
    }

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    private void setUpBinders() {
        customerBinder = new Binder<>();
        customerBinder.forField(nameField)
                .asRequired("Naam is verplicht")
                .bind(Customer::getName, Customer::setName);
        customerBinder.forField(vatNumberField)
                .withValidator(value -> {
                    if (value == null) return false;
                    long letterCount = value.chars()
                            .filter(Character::isLetter)
                            .count();
                    return letterCount >= 2;
                }, "Het BTW-nummer moet minstens 2 letters bevatten")
                .bind(Customer::getVatNumber, Customer::setVatNumber);
        customerBinder.forField(checkbAgro)
                .bind(Customer::getBAgro, Customer::setBAgro);
        customerBinder.forField(checkbIndustry)
                .bind(Customer::getBIndustry, Customer::setBIndustry);
        customerBinder.forField(checkbProjectCustomer)
                .bind(Customer::getBProjectCustomer, Customer::setBProjectCustomer);
        customerBinder.forField(commentField)
                .bind(Customer::getComment, Customer::setComment);
        customerBinder.forField(alertMessage)
                .bind(Customer::getAlertMessage, Customer::setAlertMessage);
        addressBinder = new Binder<>();
        addressBinder.forField(streetField)
                .asRequired("Straat is verplicht")
                .bind(Address::getStreet, Address::setStreet);
        addressBinder.forField(zipField)
                .asRequired("Postcode is verplicht")
                .bind(Address::getZip, Address::setZip);
        addressBinder.forField(cityField)
                .asRequired("Stad is verplicht")
                .bind(Address::getCity, Address::setCity);
        addressBinder.forField(countryField)
                .bind(Address::getCountry, Address::setCountry);

        contactBinder = new Binder<>();
        contactBinder.forField(firstNameField)
                .bind(Contact::getFirstName, Contact::setFirstName);
        contactBinder.forField(lastNameField)
                .bind(Contact::getLastName, Contact::setLastName);
        contactBinder.forField(emailField)
                .bind(Contact::getEmail, Contact::setEmail);
        contactBinder.forField(phoneField)
                .bind(Contact::getCellphone, Contact::setCellphone);
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
                customerBinder.writeBean(customer);
                //addressBinder.writeBean(invoiceAddress);
                //contactBinder.writeBean(contact);

                customerService.save(customer);
                actionBar.removeAll();
                setCustomer(customer);

                Notification.show("Gegevens contactpersonen zijn aangepast.");
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            editInvoiceContactsDialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }

    private VerticalLayout createDialogLayout() {

        firstNameField = new TextField("voornaam");
        lastNameField = new TextField("familienaam");
        emailField = new TextField("email");
        phoneField = new TextField("gsm");

        nameField = new TextField("naam");
        streetField = new TextField("straat + nr");
        zipField = new TextField("postcode");
        cityField = new TextField("stad");
        countryField = new TextField("land");

        vatNumberField = new TextField("btw-nummer");
        checkbAgro = new Checkbox("Agro");
        checkbAgro.addValueChangeListener(event -> {
            checkbIndustry.setValue(!event.getValue());
        });
        checkbIndustry = new Checkbox("Industrie");
        checkbIndustry.addValueChangeListener(event -> {
            checkbAgro.setValue(!event.getValue());
        });
        checkbProjectCustomer = new Checkbox("Projectklant");
        checkbProjectCustomer.addValueChangeListener(event -> {

        });
        commentField = new TextArea("commentaar");
        alertMessage = new TextArea("alarm");
        alertCheckBox = new Checkbox("commentaar alarm");

        VerticalLayout dialogLayout = new VerticalLayout(nameField,
                streetField,zipField,cityField,countryField,vatNumberField,new HorizontalLayout(checkbAgro,checkbIndustry),checkbProjectCustomer,commentField,alertMessage,alertCheckBox,firstNameField,lastNameField,emailField,phoneField);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
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
        Div title = new Div(new Text(customer.getName()));
        title.addClassName("card-title");
        title.addSingleClickListener(event -> {
            customerBinder.readBean(customer);
            addressBinder.readBean(invoiceAddress);
            contactBinder.readBean(contact);
        });
        horizontalLayout.add(title);

        if((customer.getBIndustry() != null) && (customer.getBIndustry() == true)) {
            horizontalLayout.add(new Icon(VaadinIcon.FACTORY));
        }

        if((customer.getBAgro() != null) && (customer.getBAgro() == true)) {
            horizontalLayout.add(new Icon(VaadinIcon.PIGGY_BANK));
        }

        if((customer.getBProjectCustomer() != null) && (customer.getBProjectCustomer() == true)) {
            horizontalLayout.add(new Icon(VaadinIcon.TOOLS));
        }

        this.setTitle(horizontalLayout);

        Div subtitle = new Div(new Text(customer.getVatNumber()));
        subtitle.addClassName("card-subtitle");
        this.add(subtitle);

        if((customer.getAlertMessage() != null) && (customer.getAlertMessage().length() > 0)) {
            this.getStyle().set("background-color", "#fbf6ea");
            Span alertSpan = new Span(customer.getAlertMessage());
            alertSpan.getStyle().set("color", "red").set("font-weight", "bold").set("font-size", "1.2rem");
            HorizontalLayout layout = new HorizontalLayout(alertSpan);
            layout.setSpacing(true);
            layout.setPadding(false);
            this.setHeaderSuffix(layout);
        }
        else{
            this.getStyle().set("background-color", "#fbf6ea");
//            Span badge = new Span("geen alarm");
//            badge.getStyle().set("font-size", "1.2rem");
//            badge.getElement().getThemeList().add("badge success");
//            this.setHeaderSuffix(badge);
        }


        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("card-address");
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.add(new Span(invoiceAddress.getStreet()));
        layout.add(new Span(invoiceAddress.getZip() + " " + invoiceAddress.getCity()));
        layout.add(new Span("\u00A0"));
        if((invoiceAddress.getContactList() != null)){
            invoiceAddress.getContactList().stream().forEach(contact -> {
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

                    layout.add(new Span(text));
                }
            });
        }

        this.add(layout);

        this.add(getActionMenu());
    }

    private VerticalLayout getActionMenu() {
        VerticalLayout actionBarLayout = new VerticalLayout();
        actionBarLayout.setWidth("100%");

        HorizontalLayout actionVLayout = new HorizontalLayout();
        actionVLayout.setWidthFull();
        actionVLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");;
        actie.getSubMenu().addItem("Wijzig contactpersonen");

        actionVLayout.add(actionBar);

        VerticalLayout layout = new VerticalLayout();
        actionBarLayout.add(layout);
        actionBarLayout.add(actionVLayout);
        return actionBarLayout;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;

        this.invoiceAddress = customer.getAddresses().stream().filter(address -> (address.getInvoiceAddress() != null) && (address.getInvoiceAddress() == true)).findFirst().get();

        if((invoiceAddress.getContactList() != null) && (invoiceAddress.getContactList().size() == 1)){
            this.contact = invoiceAddress.getContactList().get(0);
        }

        setUpCardLayout();

    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
