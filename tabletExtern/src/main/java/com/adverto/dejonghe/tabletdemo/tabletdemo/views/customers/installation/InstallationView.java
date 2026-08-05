package com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers.installation;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.DeviceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.repos.DeviceRepo;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers.DeviceCard;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@PageTitle("Installatie")
@Route("installation")
public class InstallationView extends VerticalLayout implements BeforeEnterObserver {

    private final DeviceRepo deviceRepo;
    DeviceService deviceService;
    CustomerService customerService;

    Customer selectedCustomer;
    Address selectedWorkAddress;

    DeviceCard deviceCard;

    HorizontalLayout layout;


    public InstallationView(DeviceService deviceService,
                            CustomerService customerService, DeviceRepo deviceRepo) {

        this.deviceService = deviceService;
        this.customerService = customerService;

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
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.add(getActionMenu());
        this.add(horizontalLayout);

        layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setHeight("92%");
        layout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        layout.getStyle().set("overflow", "auto");
        this.add(layout);
        this.deviceRepo = deviceRepo;
    }


    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(Alignment.CENTER);
        actionBarLayout.setPadding(true);

        //add Back button
        Button backButton = new Button(VaadinIcon.ARROW_LEFT.create());
        backButton.addClassName("subNav-new");
        backButton.addClickListener(e -> {
            UI.getCurrent().getPage().getHistory().back();
        });

        //add New Button
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {

//            Device newDevice = new Device();
//            newDevice.setType("");
//            newDevice.setSerialNumber("");
//            String newDeviceId = deviceService.save(newDevice);
//
//            selectedWorkAddress.getCoupledDeviceList().add(newDeviceId);
//            customerService.save(selectedCustomer);

        });
        actionBarLayout.add(backButton,newButton);
        return actionBarLayout;
    }


    public void fillCard() {
        layout.removeAll();
        VerticalLayout deviceCardVerticalLayout = new VerticalLayout();
        deviceCardVerticalLayout.setPadding(false);
        deviceCardVerticalLayout.setSpacing(true);
        deviceCardVerticalLayout.setWidth("50%");
        deviceCard = new DeviceCard();
        deviceCard.setDeviceId(selectedWorkAddress.getCoupledDeviceList(), selectedWorkAddress, selectedCustomer);
        deviceCard.setWidthFull();
        deviceCardVerticalLayout.add(deviceCard);
        layout.add(deviceCardVerticalLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Map<String, List<String>> params =
                beforeEnterEvent.getLocation()
                        .getQueryParameters()
                        .getParameters();

        String workAddressName = Optional.ofNullable(params.get("workAddressName"))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .orElse(null);


        Optional<List<Customer>>optCustomer = customerService.getCustomerByWorkAddressName(workAddressName);
        if (optCustomer.isPresent() && optCustomer.get().size() > 0) {
            List<Customer>customerList = optCustomer.get();
            if(customerList != null && customerList.size() > 1) {
                Notification.show("Er zijn meer dan 1 klanten met hetzelfde werfadres!");
            }
            else{
                selectedCustomer = optCustomer.get().get(0);
                selectedWorkAddress = selectedCustomer.getAddresses().stream().filter(item -> (item.getInvoiceAddress() == null)||(item.getInvoiceAddress() == false)).filter(item -> (item.getAddressName() != null) && (item.getAddressName().toLowerCase().matches(workAddressName.toLowerCase()))).findFirst().orElse(null);
                if(selectedWorkAddress.getCoupledDeviceList() == null){
                    selectedWorkAddress.setCoupledDeviceList(new ArrayList<>());
                    fillCard();
                }
                else{
                    fillCard();
                }
            }
        }
        else{
            Notification.show("Er zijn geen klanten met hetzelfde werfadres!");
        }
    }
}
