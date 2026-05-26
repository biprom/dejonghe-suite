package com.adverto.dejonghe.server.views;

import com.adverto.dejonghe.server.views.Staff.EmployeeView;
import com.adverto.dejonghe.server.views.Suppliers.SupplierView;
import com.adverto.dejonghe.server.views.articles.ImportArticleView;
import com.adverto.dejonghe.server.views.articles.ImportArticleViewNieuw;
import com.adverto.dejonghe.server.views.customers.CustomerDashboardView;
import com.adverto.dejonghe.server.views.dashboard.DashboardView;
import com.adverto.dejonghe.server.views.invoice.FinalInvoiceView;
import com.adverto.dejonghe.server.views.invoice.ProformaInvoiceView;
import com.adverto.dejonghe.server.views.workorder.FinishedWorkorderView;
import com.adverto.dejonghe.server.views.workorder.PendingWorkorderView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
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

    SideNav nav;
    DrawerToggle toggle;

    Map<Class<? extends Component>, String> noNav;
    Map<Class<? extends Component>, String> navCustomer;
    Map<Class<? extends Component>, String> navWorkOrder;

    public MainLayout() {
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
        logo.setHeight("100px");
        logo.addClickListener(event -> {
            UI.getCurrent().navigate(ImportArticleView.class);
        });


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

        SideNavItem dashboardLink = new SideNavItem("",
                DashboardView.class);
        dashboardLink.setPrefixComponent(createProformaMenuLayout("Dashboard".toUpperCase(),VaadinIcon.DASHBOARD.create()));

//        SideNavItem quoteLink = new SideNavItem("",
//                QuoteView.class);
//        quoteLink.setPrefixComponent(createProformaMenuLayout("Offerte".toUpperCase(),VaadinIcon.TEXT_INPUT.create()));

        SideNavItem proformaLink = new SideNavItem("",
                ProformaInvoiceView.class);
        proformaLink.setPrefixComponent(createProformaMenuLayout("Proforma".toUpperCase(),VaadinIcon.PASTE.create()));

        SideNavItem invoiceLink = new SideNavItem("",
                FinalInvoiceView.class);
        invoiceLink.setPrefixComponent(createInvoiceMenuLayout("Facturatie".toUpperCase(),VaadinIcon.EURO.create()));

        SideNavItem workOrderLink = new SideNavItem("", FinishedWorkorderView.class);
        workOrderLink.setPrefixComponent(createWorkOrderMenuLayout("Werkbon".toUpperCase(),VaadinIcon.TWIN_COL_SELECT.create()));

        navWorkOrder = Map.of(
                PendingWorkorderView.class, "Openstaand".toUpperCase(),
                FinishedWorkorderView.class, "Afgewerkt".toUpperCase()
        );

        SideNavItem customerLink = new SideNavItem("",
                CustomerDashboardView.class);
        customerLink.setPrefixComponent(createCustomerLayout("Klanten".toUpperCase(),VaadinIcon.CLIPBOARD_USER.create()));

        SideNavItem technicianLink = new SideNavItem("",
                EmployeeView.class);
        technicianLink.setPrefixComponent(createMenuLayout("Techniekers".toUpperCase(),VaadinIcon.USERS.create()));

        SideNavItem supplierLink = new SideNavItem("",
                SupplierView.class);
        supplierLink.setPrefixComponent(createMenuLayout("Leveranciers".toUpperCase(),VaadinIcon.TRUCK.create()));

        nav.addItem(importProductLinkNieuw,supplierLink,customerLink,technicianLink,workOrderLink, proformaLink,invoiceLink,dashboardLink);

//        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
//        menuEntries.forEach(entry -> {
//            if (entry.icon() != null) {
//                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
//            } else {
//                nav.addItem(new SideNavItem(entry.title(), entry.path()));
//            }
//        });
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
            if (view.equals(FinishedWorkorderView.class)) {
                button.setClassName("subNav-button");
                button.addClassName("subNav-selected");
                subMenusLayout.addToEnd(button);
            }
            else{
                button.setClassName("subNav-button");
                subMenusLayout.addToEnd(button);
            }
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

    private VerticalLayout createProformaMenuLayout(String title, Icon icon) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);
        layout.setPadding(true);
        Icon cog = icon;
        cog.setSize("32px");  // vergroot icon
        Span label = new Span(title);
        layout.add(cog, label);
        viewTitle.setText("Proforma");
        layout.addSingleClickListener(x -> updateNavbar(noNav));
        return layout;
    }

    private VerticalLayout createInvoiceMenuLayout(String title, Icon icon) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);
        layout.setPadding(true);
        Icon cog = icon;
        cog.setSize("32px");  // vergroot icon
        Span label = new Span(title);
        layout.add(cog, label);
        viewTitle.setText("Facturatie");
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
