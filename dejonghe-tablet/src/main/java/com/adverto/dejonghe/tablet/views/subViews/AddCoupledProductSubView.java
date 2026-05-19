package com.adverto.dejonghe.tablet.views.subViews;

import com.adverto.dejonghe.tablet.customEvents.AddRemoveProductEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.product.product.Product;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@org.springframework.stereotype.Component
@Scope("prototype")
public class AddCoupledProductSubView extends Div {

    ApplicationEventPublisher eventPublisher;
    List<Product>productsToAddList = new ArrayList<>();
    List<Product>selectedProductList;
    Customer selectedCustomer;
    Grid<Product>productGrid;
    Integer selectedTeam;
    LocalDate selectedDocumentDate;
    Boolean alreadyInSelectedList;

    Span titleSpan = new Span("Gekoppelde artikels voor : ");
    VerticalLayout titleLayout;
    Span alreadyInSelectedListSpan;

    public AddCoupledProductSubView(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;

        setUpTitle();
        this.add(setUpProductGrid());;
    }

    private Grid<Product> setUpProductGrid() {
        productGrid = new Grid<>();
        productGrid.addComponentColumn(     item -> {
            if(item.getProductCode() != null){
                if(item.isBoldMode() == false){
                    return new Span(item.getProductCode());
                }
                else{
                    Span span = new Span(item.getProductCode());
                    span.getStyle().set("font-weight", "bold");
                    span.addClassName("boxed-text");
                    return span;
                }
            }
            else{
                return new Span("");
            }
        });
        productGrid.addComponentColumn(item -> {
            if(item.getInternalName() != null){
                if(item.isBoldMode() == false){
                    return new Span(item.getInternalName());
                }
                else{
                    Span span = new Span(item.getInternalName());
                    span.getStyle().set("font-weight", "bold");
                    span.addClassName("boxed-text");
                    return span;
                }
            }
            else{
                return new Span("");
            }
        });
        productGrid.addComponentColumn(item -> {
            Button minusButton = new Button("-");
            minusButton.addThemeVariants(LUMO_TERTIARY_INLINE);
            minusButton.addClickListener(event -> {
                item.setSelectedAmount(item.getSelectedAmount() - 1);
                productGrid.getDataProvider().refreshItem(item);
            });
            return minusButton;
        }).setHeader(" - 1" ).setWidth("100px").setAutoWidth(true).setFlexGrow(0);
        productGrid.addComponentColumn(item -> {
            TextField tfAmount = new TextField();
            tfAmount.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfAmount.setMaxWidth(4, Unit.PICAS);
            if(item.getSelectedAmount() != null){
                tfAmount.setValue(item.getSelectedAmount().toString());
            }
            else{
                item.setSelectedAmount(0.0);
                tfAmount.setValue("0.0");
            }
            tfAmount.addValueChangeListener(value -> {
                try{
                    item.setSelectedAmount(Double.parseDouble(value.getValue().toString()));
                    //todo add evetListener
                    eventPublisher.publishEvent(new AddRemoveProductEvent(this, "Product toegevoegd",item));
                }
                catch (NumberFormatException e){
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfAmount;
        }).setHeader("Aantal").setWidth("100px").setAutoWidth(true).setFlexGrow(0);

        productGrid.addComponentColumn(item -> {
            Button plusButton = new Button("+");
            plusButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            plusButton.addClickListener(event -> {
                item.setSelectedAmount(item.getSelectedAmount() + 1);
                productGrid.getDataProvider().refreshItem(item);
            });
            return plusButton;
        }).setHeader(" + 1 ").setAutoWidth(true).setFlexGrow(0).setFrozenToEnd(true);

        return productGrid;
    }

    private void setUpTitle() {
        titleLayout = new VerticalLayout();
        titleLayout.setSpacing(true);
        alreadyInSelectedListSpan = new Span("Dit artikel is al geselecteerd in deze werkbon!");
        alreadyInSelectedListSpan.getElement().getStyle().set("background-color", "yellow");
        alreadyInSelectedListSpan.getElement().getStyle().set("font-size", "24px");
        titleLayout.add(titleSpan);
        //titleLayout.add(alreadyInSelectedListSpan);
        add(titleLayout);
    }

    private Double getTotalProductPrice(Double selectedAmount, Double sellPrice) {
        if((selectedAmount != null) && (sellPrice != null)) {
            return selectedAmount * sellPrice;
        }
        else{
            return 0.0;
        }
    }


    public void setCoupledProducts(List<Product>productList){
        productsToAddList.clear();
        productsToAddList.addAll(productList);
    }

    public void setSelectedProdcutList(List<Product>selectedProdcutList){
        if((selectedProdcutList != null) && (selectedProdcutList.size() > 0)) {
            this.selectedProductList = selectedProdcutList;
        }
    }

    public List<Product> getSelectedProdcutList(){
        return productsToAddList;
    }

    public void setSelectedCustomer(Customer selectedCustomer){
        if((selectedCustomer != null)) {
            this.selectedCustomer = selectedCustomer;
        }
    }

    public void setSelectedTeam(Integer selectedTeam){
        if((selectedTeam != null)) {
            this.selectedTeam = selectedTeam;
        }
    }

    public void setSelectedDocumentDate(LocalDate selectedDocumentDate){
        if((selectedDocumentDate != null)) {
            this.selectedDocumentDate = selectedDocumentDate;
        }
    }


    public void addSelectedProduct(Product productToAdd) {
        productToAdd.setBoldMode(true);
        productsToAddList.add(productToAdd);
        Collections.reverse(productsToAddList);
        productGrid.setItems(productsToAddList);
        titleSpan.setText("Gekoppelde artikels voor : " + productToAdd.getInternalName());
    }

    public void allreadyInSelectedList(boolean present) {
        alreadyInSelectedList = present;
        if(alreadyInSelectedList){
            titleLayout.add(alreadyInSelectedListSpan);
        }
        else{
            titleLayout.remove(alreadyInSelectedListSpan);
        }
    }
}
