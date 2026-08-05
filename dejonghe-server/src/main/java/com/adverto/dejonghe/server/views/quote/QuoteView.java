package com.adverto.dejonghe.server.views.quote;

import com.adverto.dejonghe.server.customEvents.GetSelectedQuoteEvent;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.QuoteService;
import com.adverto.dejonghe.common.entities.quote.Quote;
import com.adverto.dejonghe.server.views.subViews.CurrentQuoteSubView;
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


@PageTitle("Offerte")
@Route("offerte")
@Menu(order = 0, icon = LineAwesomeIconUrl.EURO_SIGN_SOLID)
@Component
@Scope("prototype")
public class QuoteView extends VerticalLayout implements BeforeEnterObserver {

    CurrentQuoteSubView currentQuoteSubView;
    QuoteService quoteService;
    CustomerService customerService;

    MenuBar actionBar;

    public QuoteView(CurrentQuoteSubView currentQuoteSubView,
                     QuoteService quoteService,
                     CustomerService customerService) {
        this.currentQuoteSubView = currentQuoteSubView;
        this.quoteService = quoteService;
        this.customerService = customerService;
    }

    private void loadData(List<Quote> quotesForCustomer){
        //Optional<List<Quote>> allQuotesByStatus = quoteService.getAllQuoteByFinalQuote();

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

        this.add(currentQuoteSubView);

        if(quotesForCustomer != null){
            currentQuoteSubView.addItemsToProformaGrid(quotesForCustomer);
            currentQuoteSubView.setSizeFull();
            currentQuoteSubView.tryToSetPreviousFilters();
        }
        else{
            currentQuoteSubView.addItemsToProformaGrid(quoteService.getAll().get());
            currentQuoteSubView.setSizeFull();
            currentQuoteSubView.tryToSetPreviousFilters();
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

        //add New Button
        Button newButton = new Button("+");
        newButton.addClassName("subNav-new");
        newButton.addClickListener(event -> {
            Map<String, String> params = Map.of(
                    "proforma", "true",
                    "workAddressStreet", ""
            );
            UI.getCurrent().navigate(
                    NewQuoteView.class,
                    new RouteParameters("id", "none"),
                    QueryParameters.simple(params)
            );
        });


        //add Action MenuBar
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Verwijder geselecteerde offerte", getRemoveQuoteClickEvent());
        actionBarLayout.add(backButton,newButton,actionBar);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> getRemoveQuoteClickEvent() {
        return  (event) -> {
            currentQuoteSubView.showRemoveNotification();
        };
    }

    private void loadCustomerData(List<Quote> quoteList){
        currentQuoteSubView.addItemsToProformaGrid(quoteList);
        currentQuoteSubView.setSizeFull();
        this.setSizeFull();
        this.add(currentQuoteSubView);
    }

    @EventListener
    public void handleSelectedInvoiceEvent(GetSelectedQuoteEvent event) {
        if (UI.getCurrent() != null && UI.getCurrent().equals(UI.getCurrent())) {
            Optional<Quote> selectedQuote = Optional.of(event.getSelectedInvoice());
//            Map<String, String> params = Map.of(
//                    "param1", "1",
//                    "param2", "2"
//            );
            UI.getCurrent().navigate(
                    NewQuoteView.class,
                    new RouteParameters("id", selectedQuote.get().getId())
//                    ,QueryParameters.simple(params)
            );
        }
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Map<String, List<String>> params =
                beforeEnterEvent.getLocation()
                        .getQueryParameters()
                        .getParameters();

        String customerId = Optional.ofNullable(params.get("customerId"))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .orElse(null);


        if(customerId == null){
            loadData(null);
        }
        else if ((customerId != null) ) {
            List<Quote> quotesForCustomer = quoteService.getQuotesForCustomer(customerId);
            if((quotesForCustomer != null) && (!quotesForCustomer.isEmpty())){
                loadData(quotesForCustomer);
                //loadCustomerData(quotesForCustomer.stream().filter(invoice -> invoice.getFinalizeQuote() == false).collect(Collectors.toList()));
            }
            else{
                loadData(null);
                Notification notification = Notification.show("Geen offertes gevonden");
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }
        }
    }
}
