package com.adverto.dejonghe.server.views.orders;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.dbservices.OrderService;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.entities.product.product.Order;
import com.adverto.dejonghe.server.customEvents.GetSelectedInvoiceEvent;
import com.adverto.dejonghe.server.views.invoice.NewInvoiceView;
import com.adverto.dejonghe.server.views.subViews.CurrentInvoiceSubView;
import com.adverto.dejonghe.server.views.subViews.toolsSubView.CurrentOrderSubView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@PageTitle("Orders")
@Route("order-view")
@Menu(order = 0, icon = LineAwesomeIconUrl.SHOPPING_CART_SOLID)
@Component
@Scope("prototype")
public class OrderView extends VerticalLayout implements BeforeEnterObserver{

    private final OrderService orderService;
    private final CurrentOrderSubView currentOrderSubView;
    MenuBar actionBar;

    public OrderView(OrderService orderService,
                     CurrentOrderSubView currentOrderSubView) {
        this.orderService = orderService;
        this.currentOrderSubView = currentOrderSubView;
    }

    private void loadData(){
        Optional<List<Order>> allOrders = orderService.getAllOrders();
        if(allOrders.isPresent()){
            currentOrderSubView.addItemsToOrderGrid(allOrders.get());
            currentOrderSubView.setSizeFull();
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

            this.add(currentOrderSubView);
        }
        else{
            Notification notification = Notification.show("Geen orders gevonden");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
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

        //add Action MenuBar
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Verwijder geselecteerde order",getRemoveOrderClickEvent());
        actionBarLayout.add(backButton,actionBar);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getRemoveOrderClickEvent() {
        return  (event) -> {
            currentOrderSubView.showRemoveNotification();
        };
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Map<String, List<String>> params =
                beforeEnterEvent.getLocation()
                        .getQueryParameters()
                        .getParameters();

        loadData();
    }
}
