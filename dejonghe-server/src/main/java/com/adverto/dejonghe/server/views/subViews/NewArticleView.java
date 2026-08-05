package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.services.SetService;
import com.adverto.dejonghe.server.customEvents.AddProductEventListener;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.services.ProductServices;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import org.springframework.context.annotation.Scope;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@org.springframework.stereotype.Component
@Scope("prototype")
public class NewArticleView extends Div {
    SelectProductSubView selectProductSubView;
    ProductService productService;
    ProductServices productServices;
    AddProductEventListener listener;
    SetService setService;

    Grid<Product> productGrid = new Grid<>();
    Editor<Product> editor = productGrid.getEditor();
    Binder<Product> binder;

    Dialog dialog;
    ConfirmDialog confirmDialog;

    Grid.Column setNameColumn;
    Grid.Column setSellColumn;
    Grid.Column setSellIndustryColumn;
    Grid.Column setCommentColumn;
    Grid.Column setPurchaseColumn;
    Grid.Column setMarginColumn;
    Grid.Column setMarginIndustryColumn;
    Grid.Column<Product> editColumn;

    TextField sellMarginTextField;
    TextField sellMarginIndustryTextField;
    TextField nameTextField;

    Product productSellMarginToChange;
    List<Product> sameProductList;

    TextField priceEntery;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));

    public NewArticleView(ProductService productService,
                          SelectProductSubView selectProductSubView,
                          AddProductEventListener listener,
                          SetService setService,
                          ProductServices productServices) {

        this.productService = productService;
        this.selectProductSubView = selectProductSubView;
        this.listener = listener;
        this.setService = setService;
        this.productServices = productServices;

        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);

        selectProductSubView.setUserFunctionAndDocumentDate(UserFunction.ADMIN, LocalDate.now());
        setUpConfirmDialog();

        setUpProductGrid();
        setUpBinder();

        this.add(productGrid);
        this.add(setUpPriceEntery());

    }

    private void setUpBinder() {

        binder = new Binder<>(Product.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        sellMarginTextField = new TextField();
        sellMarginIndustryTextField = new TextField();
        nameTextField = new TextField();

        sellMarginTextField.setWidthFull();
        sellMarginIndustryTextField.setWidthFull();
        nameTextField.setWidthFull();


        binder.forField(nameTextField)
                //.asRequired("Mag niet leeg zijn")
                .withNullRepresentation("")
                .bind(Product::getInternalName, Product::setInternalName);
        setNameColumn.setEditorComponent(nameTextField);
        binder.forField(sellMarginTextField)
                .asRequired("Mag niet leeg zijn")
                .withNullRepresentation("0.0")
                .withConverter(new StringToDoubleConverter("Gelieve een geldig positief decimaal getal in te vullen aub."))
                .bind(product -> Double.valueOf(df.format(product.getSellMargin()).replace(",", ".")), (x,y) ->x.setSellMargin(y));
        setMarginColumn.setEditorComponent(sellMarginTextField);

        binder.forField(sellMarginIndustryTextField)
                .asRequired("Mag niet leeg zijn")
                .withNullRepresentation("0.0")
                .withConverter(new StringToDoubleConverter("Gelieve een geldig positief decimaal getal in te vullen aub."))
                .bind(product -> Double.valueOf(df.format(product.getSellMarginIndustry()).replace(",", ".")), (x,y) ->x.setSellMarginIndustry(y));
        setMarginIndustryColumn.setEditorComponent(sellMarginIndustryTextField);

        Button saveButton = new Button("Bewaar", e -> {
            editor.save();
            productServices.calcSellPriceAgroFromPurchasePriceAndMargin(productSellMarginToChange);
            productServices.calcSellPriceIndustryFromPurchasePriceAndMarginIndustry(productSellMarginToChange);
            productGrid.getDataProvider().refreshAll();
            if((productSellMarginToChange.getSetElement() == null) || (productSellMarginToChange.getSetElement() == false)){
                productService.save(productSellMarginToChange);
            }
            else{
                Optional<List<Product>> setsWithThisElement = productService.findSetsWithThisElement(productSellMarginToChange);
                if(!setsWithThisElement.isEmpty()) {
                    setsWithThisElement.get().forEach(set -> {
                        //change margins and price
                        set.getSetList().stream().filter(item -> item.getProductCode().matches(productSellMarginToChange.getProductCode())).forEach(item -> {
                            item.setPurchasePrice(productSellMarginToChange.getPurchasePrice());
                            item.setSellMargin(productSellMarginToChange.getSellMargin());
                            item.setSellPrice(productSellMarginToChange.getSellPrice());
                            item.setSellMarginIndustry(productSellMarginToChange.getSellMarginIndustry());
                            item.setSellPriceIndustry(productSellMarginToChange.getSellPriceIndustry());
                        });
                        set.setSellPrice(setService.tryToCalculateSellAgroPrice(set));
                        set.setSellPriceIndustry(setService.tryToCalculateSellIndustryPrice(set));
                        productService.save(set);
                    });
                }
                productService.save(productSellMarginToChange);
            }
            Notification.show("Dit item is aangepast.");
        });
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);
    }


    private VerticalLayout setUpPriceEntery() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.add(getInfoSpan());
        layout.add(getPriceEntery());
        return layout;
    }

    private HorizontalLayout getPriceEntery() {
        HorizontalLayout layout = new HorizontalLayout();
        priceEntery = new TextField();
        priceEntery.setSuffixComponent(new Span("€"));
        priceEntery.addValueChangeListener(event -> {
            try {
                Number number = Double.valueOf(priceEntery.getValue());
                if ((number.doubleValue() >= 0)) {
                    priceEntery.setInvalid(false);
                } else {
                    priceEntery.setInvalid(true);
                    priceEntery.setErrorMessage("Geen geldig positief decimaal getal");
                }
            } catch (Exception f) {
                Notification.show("Kan dit decimaal getal niet herkennen");
            }
        });
        Button submitButton = new Button("Kopieer deze aankoopprijs");
        submitButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        submitButton.addClickListener(event -> {
            for(Product product : sameProductList) {
                Number number = null;
                try {
                    number = df.parse(priceEntery.getValue());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                double value = number.doubleValue();
                if(product.getPurchasePrice() != value) {
                    product.setPurchasePrice(value);
                    productServices.calcSellPriceAgroFromPurchasePriceAndMargin(product);
                    productServices.calcSellPriceIndustryFromPurchasePriceAndMarginIndustry(product);
                    if ((product.getSetElement() != null) && (product.getSetElement() == true)) {
                        //Update this set price
                        Optional<List<Product>> allSetsContaining = productService.getAllSetsContaining(product);
                        if (!allSetsContaining.isEmpty()) {
                            for (Product set : allSetsContaining.get()) {
                                set.getSetList().stream().filter(item -> item.getProductCode().matches(product.getProductCode())).forEach(item -> {
                                    item.setPurchasePrice(product.getPurchasePrice());
                                    productServices.calcSellPriceAgroFromPurchasePriceAndMargin(item);
                                    productServices.calcSellPriceIndustryFromPurchasePriceAndMarginIndustry(item);
                                });
                                set.setPurchasePrice(setService.tryToCalculatePurchasePrice(set));
                                set.setSellPrice(setService.tryToCalculateSellAgroPrice(set));
                                set.setSellPriceIndustry(setService.tryToCalculateSellIndustryPrice(set));
                                productService.save(set);
                            }

                        }
                    }
                }
                productService.save(product);
            }
            confirmDialog.open();
        });
        layout.add(priceEntery);
        layout.add(submitButton);
        return layout;
    }

    private Html getInfoSpan() {
        Html html = new Html("<span>Ingave nieuwe  <b>aankoopprijs</b> voor alle gelijknamige artikelen</span>");
        return html;
    }

    private void setUpConfirmDialog() {
        confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Bent u zeker dat u de prijs wil overnemen?");
        confirmDialog.setText(
                "Het geselecteerd artikel zal de aankoopprijs overnemen");
        confirmDialog.setCancelable(true);
        confirmDialog.addCancelListener(event -> confirmDialog.close());

        confirmDialog.setConfirmText("Save");
        confirmDialog.addConfirmListener(event -> {
            for(Product product : sameProductList) {
                Number number = null;
                try {
                    number = df.parse(priceEntery.getValue());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(product.getPurchasePrice() != number.doubleValue()) {
                    product.setPurchasePrice(number.doubleValue());
                    productServices.calcSellPriceAgroFromPurchasePriceAndMargin(product);
                    productServices.calcSellPriceIndustryFromPurchasePriceAndMarginIndustry(product);
                    productService.save(product);
                    if ((product.getSetElement() != null) && (product.getSetElement() == true)) {
                        //Update this set price
                        Optional<List<Product>> allSetsContaining = productService.getAllSetsContaining(product);
                        if (!allSetsContaining.isEmpty()) {
                            for (Product set : allSetsContaining.get()) {
                                set.getSetList().stream().filter(item -> item.getProductCode().matches(product.getProductCode())).forEach(item -> {
                                    item.setPurchasePrice(product.getPurchasePrice());
                                    productServices.calcSellPriceAgroFromPurchasePriceAndMargin(item);
                                    productServices.calcSellPriceIndustryFromPurchasePriceAndMarginIndustry(item);
                                });
                                set.setPurchasePrice(setService.tryToCalculatePurchasePrice(set));
                                set.setSellPrice(setService.tryToCalculateSellAgroPrice(set));
                                set.setSellPriceIndustry(setService.tryToCalculateSellIndustryPrice(set));
                                productService.save(set);
                            }
                        }
                    }
                }
            }
            productGrid.getDataProvider().refreshAll();
        });
    }


    private Component setUpProductGrid() {

        productGrid.setWidth("100%");
        productGrid.addComponentColumn(product -> {
            if ((product.getSetElement() != null) && (product.getSetElement() == true)) {
                Button elementButton = new Button("O");
                elementButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return elementButton;
            }
            if ((product.getSet() != null) && (product.getSet() == true)) {
                Button setButton = new Button("S");
                setButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return setButton;
            } else {
                return new Span("");
            }
        }).setFlexGrow(0).setHeader(new Span("S/0")).setTextAlign(ColumnTextAlign.CENTER);
        productGrid.addColumn(item -> item.getProductCode()).setAutoWidth(true).setHeader("Code").setResizable(true);
        setNameColumn = productGrid.addColumn(item -> item.getInternalName()).setAutoWidth(true).setHeader("Naam").setResizable(true);
        setPurchaseColumn = productGrid.addColumn(item -> df.format(item.getPurchasePrice())).setAutoWidth(true).setHeader("Aankoop").setResizable(false).setTextAlign(ColumnTextAlign.END);
        setMarginColumn = productGrid.addColumn(item -> df.format(item.getSellMargin())).setAutoWidth(true).setHeader("Marge A").setResizable(false).setTextAlign(ColumnTextAlign.END);
        setSellColumn = productGrid.addColumn(item -> df.format(item.getSellPrice())).setAutoWidth(true).setHeader("Verkoop A").setResizable(false).setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        //setMarginIndustryColumn = productGrid.addColumn(item -> df.format(item.getSellMarginIndustry())).setAutoWidth(true).setHeader("Marge I").setResizable(false).setTextAlign(ColumnTextAlign.END);

        setMarginIndustryColumn = productGrid.addColumn(item -> {
            if((item.getSellMarginIndustry() != null)) {
                return df.format(item.getSellMarginIndustry());
            }
            else{
                return "";
            }
        }).setAutoWidth(true).setHeader("Marge I").setResizable(false).setTextAlign(ColumnTextAlign.END);;

        //setSellIndustryColumn = productGrid.addColumn(item -> df.format(item.getSellPriceIndustry())).setAutoWidth(true).setHeader("Verkoop I").setResizable(false).setAutoWidth(true).setTextAlign(ColumnTextAlign.END);

        setSellIndustryColumn = productGrid.addColumn(item -> {
            if((item.getSellPriceIndustry() != null)) {
                return df.format(item.getSellPriceIndustry());
            }
            else{
                return "";
            }
        }).setAutoWidth(true).setHeader("Verkoop I").setResizable(false).setAutoWidth(true).setTextAlign(ColumnTextAlign.END);

        setCommentColumn = productGrid.addColumn(item -> item.getComment()).setAutoWidth(true).setHeader("Commentaar").setResizable(true);
        productGrid.addColumn(item -> getStringLevel(item)).setAutoWidth(true).setHeader("Niveau").setResizable(true).setAutoWidth(true);
        editColumn = productGrid.addComponentColumn(product -> {
            Span span = new Span("");
            return span;
        }).setWidth("150px").setFlexGrow(0);
        productGrid.addItemDoubleClickListener(event -> {
            if (editor.isOpen())
                editor.cancel();
            productSellMarginToChange = event.getItem();
            binder.readBean(productSellMarginToChange);
            editor.editItem(productSellMarginToChange);
        });
        return productGrid;
    }

    public void setSameProductList(Optional<List<Product>> byProductCodeContaining) {
        this.sameProductList = byProductCodeContaining.orElse(new ArrayList<>());
        productGrid.setItems(sameProductList);
        productGrid.getDataProvider().refreshAll();
    }

    private String getStringLevel(Product selectedProduct) {
        String levelString = selectedProduct.getProductLevel1().getName();
        if(selectedProduct.getProductLevel2() != null){
            levelString = levelString + " " + selectedProduct.getProductLevel2().getName();
        }
        if(selectedProduct.getProductLevel3() != null){
            levelString = levelString + " " + selectedProduct.getProductLevel3().getName();
        }
        if(selectedProduct.getProductLevel4() != null){
            levelString = levelString + " " + selectedProduct.getProductLevel4().getName();
        }
        if(selectedProduct.getProductLevel5() != null){
            levelString = levelString + " " + selectedProduct.getProductLevel5().getName();
        }
        if(selectedProduct.getProductLevel6() != null){
            levelString = levelString + " " + selectedProduct.getProductLevel6().getName();
        }
        if(selectedProduct.getProductLevel7() != null){
            levelString = levelString + " " + selectedProduct.getProductLevel7().getName();
        }
        return levelString;
    }

    public void setConfirmDialog(Dialog newArticleDialog) {
        this.dialog = newArticleDialog;
    }

    public void setCorrectedPrice(String byProductCodeContaining) {
        priceEntery.setValue(byProductCodeContaining);
    }
}
