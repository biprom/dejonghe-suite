package com.adverto.dejonghe.tabletdemo.tabletdemo.views;

import com.adverto.dejonghe.common.entities.updateVersion.UpdateStatus;
import com.adverto.dejonghe.tabletdemo.tabletdemo.services.SyncService;
import com.adverto.dejonghe.tabletdemo.tabletdemo.services.TabletUpdateService;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.articles.ImportArticleViewNieuw;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.customers.CustomerDashboardView;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.workorder.PendingWorkorderView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private final TabletUpdateService tabletUpdateService;

    private final Div updateIndicator = new Div();

    private H1 viewTitle;

    private HorizontalLayout navbar;
    private HorizontalLayout subMenusLayout;

    private SyncService syncService;

    SideNav nav;
    DrawerToggle toggle;

    Map<Class<? extends Component>, String> noNav;
    Map<Class<? extends Component>, String> navCustomer;
    Map<Class<? extends Component>, String> navWorkOrder;

    @Value("${feature.sync.enabled:true}")
    private boolean syncEnabled;

    public MainLayout(SyncService syncService,
                      TabletUpdateService tabletUpdateService) {

        this.syncService = syncService;
        this.tabletUpdateService = tabletUpdateService;

        UI.getCurrent().setLocale(new Locale("nl", "BE"));
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

        // Update indicator
        updateIndicator.setWidth("60px");
        updateIndicator.setHeight("60px");
        updateIndicator.setMinWidth("60px");
        updateIndicator.setMinHeight("60px");

        updateIndicator.getStyle()
                .set("position", "relative")
                .set("border-radius", "50%")
                .set("display", "inline-block")
                .set("box-sizing", "border-box")
                .set("transition", "all 0.3s ease");

        /*
         * Witte glans bovenaan de LED
         */
        Div shine = new Div();
        shine.setWidth("25px");
        shine.setHeight("16px");

        shine.getStyle()
                .set("position", "absolute")
                .set("top", "8px")
                .set("left", "10px")
                .set("border-radius", "50%")
                .set("background",
                        "radial-gradient(ellipse, " +
                                "rgba(255,255,255,0.90) 0%, " +
                                "rgba(255,255,255,0.45) 45%, " +
                                "rgba(255,255,255,0) 75%)")
                .set("filter", "blur(1px)")
                .set("pointer-events", "none");

        updateIndicator.add(shine);

        HorizontalLayout statusLayout = new HorizontalLayout();
        statusLayout.setSpacing(true);
        statusLayout.getStyle()
                .set("margin-right", "20px");
        statusLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Span softwareText = new Span("Software");
        softwareText.getStyle()
                .set("font-size", "20px")
                .set("font-weight", "500")
                .set("color", "#5d5b5e");

        statusLayout.add(
                softwareText,
                updateIndicator
        );

        updateUpdateIndicator();

        toggle = new DrawerToggle();
        toggle.getElement().getStyle()
                .set("color", "#4e4a47");

        toggle.setAriaLabel("Menu toggle");
        toggle.addClassName("large-toggle");


        viewTitle = new H1();

        viewTitle.getElement().getStyle()
                .set("font-size", "30px")
                .set("color", "#5d5b5e");

        viewTitle.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.NONE
        );

        viewTitle.setWhiteSpace(
                HasText.WhiteSpace.NOWRAP
        );


        subMenusLayout = new HorizontalLayout();
        subMenusLayout.setSpacing(true);

        Div spacer = new Div();

        navbar.add(
                toggle,
                viewTitle,
                subMenusLayout,
                spacer,
                statusLayout
        );

        navbar.expand(spacer);

        addToNavbar(true, navbar);
    }

    private void updateUpdateIndicator() {

        if (tabletUpdateService.isUpdateAvailable()) {

            setUpdateIndicatorRed();

            updateIndicator.setTitle(
                    "Update beschikbaar: "
                            + tabletUpdateService.getLatestVersion()
            );

        } else {

            setUpdateIndicatorGreen();

            updateIndicator.setTitle(
                    "Tablet is up-to-date"
            );
        }
    }

    private void setUpdateIndicatorGreen() {

        updateIndicator.getStyle()
                .set(
                        "background",
                        "radial-gradient(circle at 35% 30%, " +
                                "#d8ffd8 0%, " +
                                "#70ff70 12%, " +
                                "#25e625 35%, " +
                                "#08b408 65%, " +
                                "#046504 100%)"
                )
                .set(
                        "box-shadow",
                        "0 0 5px rgba(0,255,0,0.9), " +
                                "0 0 14px rgba(0,255,0,0.65), " +
                                "0 0 25px rgba(0,255,0,0.25), " +
                                "inset 5px 5px 9px rgba(255,255,255,0.35), " +
                                "inset -7px -9px 12px rgba(0,0,0,0.35)"
                )
                .set(
                        "border",
                        "2px solid rgba(0,100,0,0.7)"
                );
    }

    private void setUpdateIndicatorRed() {

        updateIndicator.getStyle()
                .set(
                        "background",
                        "radial-gradient(circle at 35% 30%, " +
                                "#ffd6d6 0%, " +
                                "#ff7777 12%, " +
                                "#ff2828 35%, " +
                                "#d40000 65%, " +
                                "#760000 100%)"
                )
                .set(
                        "box-shadow",
                        "0 0 5px rgba(255,0,0,0.9), " +
                                "0 0 14px rgba(255,0,0,0.65), " +
                                "0 0 25px rgba(255,0,0,0.25), " +
                                "inset 5px 5px 9px rgba(255,255,255,0.35), " +
                                "inset -7px -9px 12px rgba(0,0,0,0.35)"
                )
                .set(
                        "border",
                        "2px solid rgba(120,0,0,0.7)"
                );
    }

    private void addDrawerContent() {
        Image logo = new Image("icons/img.png", "Logo");
        logo.addClickListener(x -> {

            if(syncEnabled){
                Notification.show("Verzenden van alle afgewerkte werkbonnen naar de server");
                try {
                    syncService.sendWorkOrders();
                } catch (IOException e) {
                    Notification.show("Mislukt om de werkbonnen naar de server te verzenden").addThemeVariants(NotificationVariant.LUMO_ERROR);
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

                try {

                    UpdateStatus status = syncService.checkForUpdate();

                    if (status != null) {

                        tabletUpdateService.setVersionStatus(
                                true,
                                status.isUpdateAvailable(),
                                status.getLatestVersion()
                        );

                    } else {

                        tabletUpdateService.setVersionStatus(
                                false,
                                true,
                                null
                        );
                    }

                } catch (Exception e) {

                    tabletUpdateService.setVersionStatus(
                            false,
                            true,
                            null
                    );
                }
                Notification.show("Synchronisatie Artikelmappen beïndigd");

                Notification.show("Synchronisatie Personeel gestart");
                syncService.syncEmployeesFromServer();
                Notification.show("Synchronisatie Personeel beïndigd");
            }
            else{
                Notification.show("Synchronisatie wordt continu uitgevoerd");
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

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        viewTitle.setText(getCurrentPageTitle());
    }
}
