package com.adverto.dejonghe.tabletdemo.tabletdemo.views;

import com.adverto.dejonghe.tabletdemo.tabletdemo.services.SyncService;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.articles.ImportArticleViewNieuw;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers.CustomerDashboardView;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.workorder.PendingWorkorderView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

/**
 * The main view is a top-level placeholder for other views.
 */
@Slf4j
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    private HorizontalLayout navbar;
    private HorizontalLayout subMenusLayout;

    private SyncService syncService;

    SideNav nav;
    DrawerToggle toggle;

    Map<Class<? extends Component>, String> noNav;
    Map<Class<? extends Component>, String> navCustomer;
    Map<Class<? extends Component>, String> navWorkOrder;

    @Value("${feature.sync.enabled:false}")
    private boolean syncEnabled;

    public MainLayout(SyncService syncService) {

        this.syncService = syncService;

        UI.getCurrent().setLocale(new Locale("nl", "BE"));
        System.out.println("Default locale: " + Locale.getDefault());
        System.out.println("Language: " + Locale.getDefault().getLanguage());
        System.out.println("Country: " + Locale.getDefault().getCountry());
        System.out.println("Decimal separator: " +
                DecimalFormatSymbols.getInstance().getDecimalSeparator());
        setUpNavBar();
        setPrimarySection(Section.DRAWER);
        addHeaderContent();
        addDrawerContent();
    }

    private void setUpNavBar() {
        navbar = new HorizontalLayout();
        navbar.setAlignItems(FlexComponent.Alignment.CENTER);
        navbar.setHeight("100px");
        navbar.setWidth("40%");
        navbar.setMargin(false);
        navbar.setSpacing(true);

    }

    private void addHeaderContent() {
        toggle = new DrawerToggle();
        toggle.getElement().getStyle().set("color", "#4e4a47");
        toggle.setAriaLabel("Menu toggle");
        toggle.addClassName("large-toggle");

        viewTitle = new H1();
        viewTitle.getElement().getStyle().set("font-size", "30px");
        viewTitle.getElement().getStyle().set("color", "#5d5b5e");
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        viewTitle.setWhiteSpace(HasText.WhiteSpace.NOWRAP);

        subMenusLayout = new HorizontalLayout();
        subMenusLayout.setSpacing(true);
        subMenusLayout.setWidth("100%");

        navbar.add(toggle,viewTitle, subMenusLayout);
        addToNavbar(true, navbar);

    }

    private void addDrawerContent() {
        Image logo = new Image("icons/img.png", "Logo");
        logo.addClickListener(x -> {

            if(syncEnabled){
                Notification.show("Verzenden van alle afgewerkte werkbonnen naar de server");
                try {
                    syncService.sendWorkOrders();
                } catch (IOException e) {
                    Notification.show("Mislukt om de werkbonnen naar de server te verzenden");
                }
                Notification.show("Werkbonnen verzonden ");

                Notification.show("Verwijderen van alle documenten en foto's");
                syncService.deleteAllDocuments();
                Notification.show("Verwijderen gelukt");

//                Notification.show("Synchronisatie van werkbonnen op de server");
//                try {
//                    syncService.receiveWorkOrders();
//                } catch (IOException e) {
//                    Notification.show("Synchronisatie van werkbonnen op de server niet gelukt");
//                }
//                Notification.show("Synchronisatie van werkbonnen op de server gelukt");


                Notification.show("Synchronisatie Klanten gestart");
                syncService.syncCustomersFromServer();
                Notification.show("Synchronisatie Klanten beïndigd");


                Notification.show("Synchronisatie Artikelen gestart");
                syncService.syncProducts();
                Notification.show("Synchronisatie Artikelen beïndigd");

                Notification.show("Synchronisatie Artikelmappen gestart");
                syncService.syncProductFolders1();
                syncService.syncProductFolders2();
                syncService.syncProductFolders3();
                syncService.syncProductFolders4();
                syncService.syncProductFolders5();
                syncService.syncProductFolders6();
                syncService.syncProductFolders7();
                Notification.show("Synchronisatie Artikelmappen beïndigd");

                Notification.show("Synchronisatie Personeel gestart");
                syncService.syncEmployeesFromServer();
                Notification.show("Synchronisatie Personeel beïndigd");
            }
            else{
                Notification.show("Synchronisatie is uitgeschakeld");
            }
        });
        logo.setHeight("100px");


        Scroller scroller = new Scroller(createNavigation());
        scroller.getElement().setAttribute("theme", Lumo.DARK);
        //scroller.setClassName(LumoUtility.Padding.XLARGE);

        addToDrawer(logo,scroller);
        this.viewTitle.addClickListener(event -> {
            setDrawerOpened(false);
        });
    }

    private SideNav createNavigation() {
        nav = new SideNav();

        SideNavItem importProductLinkNieuw =
                new SideNavItem("", ImportArticleViewNieuw.class);
        importProductLinkNieuw.setPrefixComponent(
                createMenuLayout("ARTIKELEN", VaadinIcon.COG.create())
        );

        noNav = Map.of();

        SideNavItem workOrderLink = new SideNavItem("", PendingWorkorderView.class);
        workOrderLink.setPrefixComponent(createWorkOrderMenuLayout("Werkbon".toUpperCase(),VaadinIcon.TWIN_COL_SELECT.create()));

        navWorkOrder = Map.of(
                PendingWorkorderView.class, "Openstaand".toUpperCase()
        );

        SideNavItem customerLink = new SideNavItem("",
                CustomerDashboardView.class);
        customerLink.setPrefixComponent(createCustomerLayout("Klanten".toUpperCase(),VaadinIcon.CLIPBOARD_USER.create()));

        nav.addItem(importProductLinkNieuw,customerLink,workOrderLink);

        return nav;
    }

    private void updateNavbar(Map<Class<? extends Component>, String> views) {
        subMenusLayout.removeAll();

        views.forEach((view, label) -> {
            Button button = new Button(label);
            button.addClickListener(event -> {
                UI.getCurrent().navigate(view);
                subMenusLayout.getChildren().forEach(child -> {child.removeClassName("subNav-selected");});
                button.addClassName("subNav-selected");
            });
            button.setClassName("subNav-button");
            subMenusLayout.addToEnd(button);
        });

    }

    private VerticalLayout createMenuLayout(String title, Icon icon) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidth("100%");
        Icon cog = icon;
        cog.setSize("32px");  // vergroot icon
        Span label = new Span(title);
        label.addClassName("menu-label");
        layout.add(cog, label);
        viewTitle.setText("");
        layout.addSingleClickListener(x -> updateNavbar(noNav));
        return layout;
    }

    private VerticalLayout createCustomerLayout(String title, Icon icon) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidth("100%");
        Icon cog = icon;
        cog.setSize("32px");  // vergroot icon
        Span label = new Span(title);
        layout.add(cog, label);
        viewTitle.setText("");
        layout.addSingleClickListener(x -> updateNavbar(noNav));
        return layout;
    }


    private VerticalLayout createWorkOrderMenuLayout(String title, Icon icon) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);
        layout.setPadding(true);
        Icon cog = icon;
        cog.setSize("32px");  // vergroot icon
        Span label = new Span(title);
        layout.add(cog, label);
        viewTitle.setText("Werkbon");
        layout.addSingleClickListener(x -> updateNavbar(navWorkOrder));
        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
