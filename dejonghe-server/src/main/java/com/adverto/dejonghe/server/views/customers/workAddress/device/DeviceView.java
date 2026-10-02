package com.adverto.dejonghe.server.views.customers.workAddress.device;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.DeviceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.installation.Device;
import com.adverto.dejonghe.server.views.subViews.CurrentDeviceSubView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@PageTitle("Toestellen")
@Route("toestellen")
@Menu(order = 0, icon = LineAwesomeIconUrl.EURO_SIGN_SOLID)
@Component
@Scope("prototype")
public class DeviceView extends VerticalLayout implements BeforeEnterObserver {

    CurrentDeviceSubView currentDeviceSubView;
    DeviceService deviceService;
    CustomerService customerService;

    Customer selectedCustomer;
    Address selectedWorkAddress;
    List<Device>devices = new ArrayList<>();

    MenuBar actionBar;

    public DeviceView(CurrentDeviceSubView currentDeviceSubView,
                      DeviceService deviceService,
                      CustomerService customerService) {
        this.currentDeviceSubView = currentDeviceSubView;
        this.deviceService = deviceService;
        this.customerService = customerService;
    }

    private void loadData(){
        devices.clear();
        for(String deviceId : selectedWorkAddress.getCoupledDeviceList()){
            Optional<Device> device = deviceService.getDeviceById(deviceId);
            if(device.isPresent()){
                devices.add(device.get());
            }
        }
        currentDeviceSubView.addItemsToDeviceGrid(devices);
        currentDeviceSubView.setSelectedCustomer(selectedCustomer);
        currentDeviceSubView.setselectedWorkAddress(selectedWorkAddress);
        currentDeviceSubView.setSizeFull();
        this.removeAll();
        this.setSizeFull();
        this.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");
        this.addClassName("view-wrapper");
        this.getStyle().set("background-color", "#e9ebef");
        this.setPadding(true);
        this.setSpacing(true);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.add(getActionMenu());

        this.add(horizontalLayout);
        this.add(currentDeviceSubView);
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setAlignItems(Alignment.CENTER);
        actionBarLayout.setPadding(true);

        //add New Button
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {
            Device newDevice = new Device();
            newDevice.setDeviceName("");
            newDevice.setType("");
            newDevice.setSerialNumber("");
            newDevice.setDate(LocalDate.now());
            String newDeviceId = deviceService.save(newDevice);
            if(selectedWorkAddress.getCoupledDeviceList() != null){
                selectedWorkAddress.getCoupledDeviceList().add(newDeviceId);
            }
            else{
                selectedWorkAddress.setCoupledDeviceList(List.of(newDeviceId));
            }
            customerService.save(selectedCustomer);
            loadData();
        });

        //add Action MenuBar
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getSubMenu().addItem("Verwijder geselecteerde toestel",removeSelectedDevice());
        actie.getElement().getClassList().add("menu-as-button");

        //add Back button
        Button backButton = new Button(VaadinIcon.ARROW_LEFT.create());
        backButton.addClassName("subNav-new");
        backButton.addClickListener(e -> {
            UI.getCurrent().getPage().getHistory().back();
        });

        actionBarLayout.add(backButton,newButton,actionBar);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> removeSelectedDevice() {
        return  (event) -> {
            currentDeviceSubView.showRemoveNotification();
        };
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
                selectedWorkAddress = selectedCustomer.getAddresses().stream().filter(item -> (item.getAddressName() != null) && (item.getAddressName().toLowerCase().matches(workAddressName.toLowerCase())) && ((item.getInvoiceAddress() == null)||(item.getInvoiceAddress() == false))).findFirst().orElse(null);
                if(selectedWorkAddress.getCoupledDeviceList() == null){
                    selectedWorkAddress.setCoupledDeviceList(new ArrayList<>());
                    loadData();
                }
                else{
                    loadData();
                }
            }
        }
        else{
            Notification.show("Er zijn geen klanten met hetzelfde werfadres!");
        }
    }
}
