package com.adverto.dejonghe.server.views.Suppliers;

import com.adverto.dejonghe.common.entities.product.product.Supplier;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class SupplierCard extends Card {

    Supplier supplier;
    MenuBar actionBar;

    SupplierView supplierView;


    public SupplierCard(Supplier supplier, SupplierView supplierView) {
        this.supplierView = supplierView;
        this.supplier = supplier;
        setUpCardLayout(supplier);
    }


    private void setUpCardLayout(Supplier supplier) {
        this.removeAll();
        this.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL
        );

        this.getStyle().set("background-color", "#fbf6ea");

        Div title = new Div(supplier.getName());
        title.addClassName("card-title");
        this.setTitle(title);

        Div subtitle = new Div(new Text(supplier.getVatNumber()));
        subtitle.addClassName("card-subtitle");
        this.add(subtitle);

        this.addToFooter(getActionMenu());
    }

    private HorizontalLayout getActionMenu() {
        HorizontalLayout actionBarLayout = new HorizontalLayout();
        actionBarLayout.setWidth("100%");
        actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Pas aan", editSupplier());
        actie.getSubMenu().addItem("Verwijderen", removeEmployee());
        actionBarLayout.addToEnd(actionBar);
        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> editSupplier() {
        return event -> {
            supplierView.openEditDialog(supplier, this);
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> removeEmployee() {
        return event -> {
            supplierView.openRemoveDialog(supplier, this);
        };
    }

    public void refreshWith(Supplier supplierToEdit) {
        setUpCardLayout(supplierToEdit);
    }
}
