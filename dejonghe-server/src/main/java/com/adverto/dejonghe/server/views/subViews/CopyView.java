package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.dbservices.*;
import com.adverto.dejonghe.common.entities.product.product.*;
import com.adverto.dejonghe.server.customEvents.AddProductEventListener;
import com.adverto.dejonghe.common.entities.product.enums.E_Product_Level;
import com.adverto.dejonghe.common.repos.ProductRepo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.context.annotation.Scope;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Component
@Scope("prototype")
public class CopyView extends Div {
    SelectProductSubView selectProductSubView;
    AddProductEventListener listener;

    private Product selectedProduct;

    private final ProductService productService;
    private ProductLevel1Service productLevel1Service;
    private ProductLevel2Service productLevel2Service;
    private ProductLevel3Service productLevel3Service;
    private ProductLevel4Service productLevel4Service;
    private ProductLevel5Service productLevel5Service;
    private ProductLevel6Service productLevel6Service;
    private ProductLevel7Service productLevel7Service;

    private List<ProductLevel1> level1ListTo;
    private Button bAddProductLevel1;
    private Button bAddProductLevel2;
    private Button bAddProductLevel3;
    private Button bAddProductLevel4;
    private Button bAddProductLevel5;
    private Button bAddProductLevel6;
    private Button bAddProductLevel7;

    private List<ProductLevel1> level1ListFrom;
    private List<ProductLevel2>level2List;
    private List<ProductLevel3>level3List;
    private List<ProductLevel4>level4List;
    private List<ProductLevel5>level5List;
    private List<ProductLevel6>level6List;
    private List<ProductLevel7>level7List;

    private ComboBox<ProductLevel1> cbProductLevel1From;
    private ComboBox<ProductLevel2> cbProductLevel2From;
    private ComboBox<ProductLevel3> cbProductLevel3From;
    private ComboBox<ProductLevel4> cbProductLevel4From;
    private ComboBox<ProductLevel5> cbProductLevel5From;
    private ComboBox<ProductLevel6> cbProductLevel6From;
    private ComboBox<ProductLevel7> cbProductLevel7From;

    private ComboBox<ProductLevel1> cbProductLevel1To;
    private ComboBox<ProductLevel2> cbProductLevel2To;
    private ComboBox<ProductLevel3> cbProductLevel3To;
    private ComboBox<ProductLevel4> cbProductLevel4To;
    private ComboBox<ProductLevel5> cbProductLevel5To;
    private ComboBox<ProductLevel6> cbProductLevel6To;
    private ComboBox<ProductLevel7> cbProductLevel7To;

    Grid.Column codeColumnFrom;
    Grid.Column posColumnFrom;
    Grid.Column internalNameColumnFrom;
    Grid.Column commentColumnFrom;
    Grid.Column unitColumnFrom;
    Grid.Column purchaceColumnFrom;
    Grid.Column sellComumnFrom;
    Grid.Column sellIndustryComumnFrom;
    Grid.Column marginColumnFrom;
    Grid.Column marginIndustryColumnFrom;

    Grid.Column codeColumnTo;
    Grid.Column posColumnTo;
    Grid.Column internalNameColumnTo;
    Grid.Column commentColumnTo;
    Grid.Column unitColumnTo;
    Grid.Column purchaceColumnTo;
    Grid.Column sellComumnTo;
    Grid.Column sellIndustryComumnTo;
    Grid.Column marginColumnTo;
    Grid.Column marginIndustryColumnTo;

    private E_Product_Level selectedProductLevel;

    AtomicBoolean updating = new AtomicBoolean(false);
    List<Product> productListToShowInGridFrom = new ArrayList<>();
    List<Product> productListToShowInGridTo = new ArrayList<>();

    private List<Product>productForGrid;

    private final Grid<Product> fromGrid = new Grid<>(Product.class, false);
    private Grid<Product> toGrid = new Grid<>(Product.class, false);

    List<Product>draggedItem;
    GridListDataView dataViewFrom;
    GridListDataView dataViewTo;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));
    private Binder<Product> productBinderFrom;
    Editor<Product> editorFrom;
    private Binder<Product> productBinderTo;
    Editor<Product> editorTo;

    private TextField tfPositionNumberFrom;
    private TextField tfInternalNameFrom;
    private TextField tfCommentFrom;
    private TextField tfPurchasePriceFrom;
    private TextField tfSellMarginFrom;
    private TextField tfSellIndustryMarginFrom;
    private TextField tfSellPriceFrom;
    private TextField tfSellIndustryPriceFrom;

    private TextField tfPositionNumberTo;
    private TextField tfInternalNameTo;
    private TextField tfCommentTo;
    private TextField tfPurchasePriceTo;
    private TextField tfSellMarginTo;
    private TextField tfSellIndustryMarginTo;
    private TextField tfSellPriceTo;
    private TextField tfSellIndustryPriceTo;

    Notification deleteCustomerNotification;


    public CopyView(ProductService productService,
                    SelectProductSubView selectProductSubView,
                    AddProductEventListener listener,
                    ProductRepo productRepo,
                    ProductLevel1Service productLevel1Service,
                    ProductLevel2Service productLevel2Service,
                    ProductLevel3Service productLevel3Service,
                    ProductLevel4Service productLevel4Service,
                    ProductLevel5Service productLevel5Service,
                    ProductLevel6Service productLevel6Service,
                    ProductLevel7Service productLevel7Service) {

        this.productService = productService;
        this.selectProductSubView = selectProductSubView;
        this.listener = listener;
        this.productLevel1Service = productLevel1Service;
        this.productLevel2Service = productLevel2Service;
        this.productLevel3Service = productLevel3Service;
        this.productLevel4Service = productLevel4Service;
        this.productLevel5Service = productLevel5Service;
        this.productLevel6Service = productLevel6Service;
        this.productLevel7Service = productLevel7Service;

        createEditorLayout();

        setUpFromGrid();
        setUpToGrid();
        add(createGridLayout());

        setUpProductLevelComboBoxesFrom();
        setUpProductLevelComboBoxesTo();

        setUpBinderFrom();
        setUpBinderTo();

        setUpPriceValueChangeListenersTo();
        setUpPriceValueChangeListenersFrom();
    }

    private void createEditorLayout() {

        tfPositionNumberFrom = new TextField("Positienummer");
        tfInternalNameFrom = new TextField("Interne omschrijving");
        tfPurchasePriceFrom = new TextField("Aankoopprijs");
        tfSellMarginFrom = new TextField("Verkoopmarge Agro");
        tfSellPriceFrom = new TextField("Verkoopprijs Agro");
        tfSellIndustryMarginFrom = new TextField("Verkoopmarge Industrie");
        tfSellIndustryPriceFrom = new TextField("Verkoopprijs Industrie");
        tfCommentFrom = new TextField("Commentaar");

        tfPositionNumberTo = new TextField("Positienummer");
        tfInternalNameTo = new TextField("Interne omschrijving");
        tfPurchasePriceTo = new TextField("Aankoopprijs");
        tfSellMarginTo = new TextField("Verkoopmarge Agro");
        tfSellPriceTo = new TextField("Verkoopprijs Agro");
        tfSellIndustryMarginTo = new TextField("Verkoopmarge Industrie");
        tfSellIndustryPriceTo = new TextField("Verkoopprijs Industrie");
        tfCommentTo = new TextField("Commentaar");

        tfInternalNameFrom.addValueChangeListener(item -> {
            if (editorFrom.getItem() != null) {
                editorFrom.save();
                productService.save(editorFrom.getItem());
            }
        });

        tfInternalNameTo.addValueChangeListener(item -> {
            if (editorTo.getItem() != null) {
                editorTo.save();
                productService.save(editorFrom.getItem());
            }
        });

        tfCommentFrom.addValueChangeListener(item -> {
            if (editorFrom.getItem() != null) {
                editorFrom.save();
                productService.save(editorFrom.getItem());
            }
        });

        tfCommentTo.addValueChangeListener(item -> {
            if (editorTo.getItem() != null) {
                editorTo.save();
                productService.save(editorFrom.getItem());
            }
        });
    }


    private void setUpBinderFrom() {
        productBinderFrom = new Binder<>(Product.class);
        editorFrom = fromGrid.getEditor();
        editorFrom.setBuffered(false);
        editorFrom.setBinder(productBinderFrom);

        productBinderFrom.forField(tfPositionNumberFrom)
                .withNullRepresentation("")
                .bind(Product::getPositionNumber, Product::setPositionNumber);
        posColumnFrom.setEditorComponent(tfPositionNumberFrom);
        tfInternalNameFrom.setWidth("100%");
        productBinderFrom.forField(tfInternalNameFrom)
                .withNullRepresentation("")
                .bind(Product::getInternalName, Product::setInternalName);
        internalNameColumnFrom.setEditorComponent(tfInternalNameFrom);
        productBinderFrom.forField(tfPurchasePriceFrom)
                .withNullRepresentation("0.00")
//                        .withValidator(
//                                value -> {
//                                    try {
//                                        double d = Double.parseDouble(value);
//                                        return true;
//                                    } catch (NumberFormatException nfe) {
//                                        return false;
//                                    }
//                                }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getPurchasePrice, Product::setPurchasePrice);
        purchaceColumnFrom.setEditorComponent(tfPurchasePriceFrom);
        productBinderFrom.forField(tfSellMarginFrom)
                .withNullRepresentation("0.00")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellMargin, Product::setSellMargin);
        marginColumnFrom.setEditorComponent(tfSellMarginFrom);
        productBinderFrom.forField(tfSellIndustryMarginFrom)
                .withNullRepresentation("0.00")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellMarginIndustry, Product::setSellMarginIndustry);
        marginIndustryColumnFrom.setEditorComponent(tfSellIndustryMarginFrom);

        productBinderFrom.forField(tfSellPriceFrom)
                .withNullRepresentation("0.0")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellPrice, Product::setSellPrice);
        sellComumnFrom.setEditorComponent(tfSellPriceFrom);
        productBinderFrom.forField(tfSellIndustryPriceFrom)
                .withNullRepresentation("0.0")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellPriceIndustry, Product::setSellPriceIndustry);
        sellIndustryComumnFrom.setEditorComponent(tfSellIndustryPriceFrom);
        tfCommentFrom.setWidth("100%");
        productBinderFrom.forField(tfCommentFrom)
                .withNullRepresentation("")
                .bind(Product::getComment, Product::setComment);
        commentColumnFrom.setEditorComponent(tfCommentFrom);
    }

    private void setUpBinderTo() {
        productBinderTo = new Binder<>(Product.class);
        editorTo = toGrid.getEditor();
        editorTo.setBuffered(false);
        editorTo.setBinder(productBinderTo);

        productBinderTo.forField(tfPositionNumberTo)
                .withNullRepresentation("")
                .bind(Product::getPositionNumber, Product::setPositionNumber);
        posColumnTo.setEditorComponent(tfPositionNumberTo);
        tfInternalNameTo.setWidth("100%");
        productBinderTo.forField(tfInternalNameTo)
                .withNullRepresentation("")
                .bind(Product::getInternalName, Product::setInternalName);
        internalNameColumnTo.setEditorComponent(tfInternalNameTo);
        productBinderTo.forField(tfPurchasePriceTo)
                .withNullRepresentation("0.00")
//                        .withValidator(
//                                value -> {
//                                    try {
//                                        double d = Double.parseDouble(value);
//                                        return true;
//                                    } catch (NumberFormatException nfe) {
//                                        return false;
//                                    }
//                                }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getPurchasePrice, Product::setPurchasePrice);
        purchaceColumnTo.setEditorComponent(tfPurchasePriceTo);
        productBinderTo.forField(tfSellMarginTo)
                .withNullRepresentation("0.00")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellMargin, Product::setSellMargin);
        marginColumnTo.setEditorComponent(tfSellMarginTo);
        productBinderTo.forField(tfSellIndustryMarginTo)
                .withNullRepresentation("0.00")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellMarginIndustry, Product::setSellMarginIndustry);
        marginIndustryColumnTo.setEditorComponent(tfSellIndustryMarginTo);

        productBinderTo.forField(tfSellPriceTo)
                .withNullRepresentation("0.0")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellPrice, Product::setSellPrice);
        sellComumnTo.setEditorComponent(tfSellPriceTo);
        productBinderTo.forField(tfSellIndustryPriceTo)
                .withNullRepresentation("0.0")
//                .withValidator(
//                        value -> {
//                            try {
//                                double d = Double.parseDouble(value);
//                                return true;
//                            } catch (NumberFormatException nfe) {
//                                return false;
//                            }
//                        }
//                        ,"De ingave moet een decimaal nummer zijn (getal met een punt als komma)")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellPriceIndustry, Product::setSellPriceIndustry);
        sellIndustryComumnTo.setEditorComponent(tfSellIndustryPriceTo);
        tfCommentTo.setWidth("100%");
        productBinderTo.forField(tfCommentTo)
                .withNullRepresentation("")
                .bind(Product::getComment, Product::setComment);
        commentColumnTo.setEditorComponent(tfCommentTo);
    }

    private void setUpPriceValueChangeListenersFrom() {
        tfPurchasePriceFrom.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                //check if there are other selected products with this code and change them
                tryToCalculateSellPriceAgroFrom(editorFrom.getItem());
                tryToCalculateSellPriceIndustryFrom(editorFrom.getItem());
            }
        });
        tfSellMarginFrom.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                tryToCalculateSellPriceAgroFrom(editorFrom.getItem());
            }
        });
        tfSellIndustryMarginFrom.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                tryToCalculateSellPriceIndustryFrom(editorFrom.getItem());
            }
        });
    }

    private void setUpPriceValueChangeListenersTo() {
        tfPurchasePriceTo.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                //check if there are other selected products with this code and change them
                tryToCalculateSellPriceAgroTo(editorTo.getItem());
                tryToCalculateSellPriceIndustryTo(editorTo.getItem());
            }
        });
        tfSellMarginTo.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                tryToCalculateSellPriceAgroTo(editorTo.getItem());
            }
        });
        tfSellIndustryMarginTo.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                tryToCalculateSellPriceIndustryTo(editorTo.getItem());
            }
        });
    }

    private void setUpFromGrid() {

        Grid.Column<Product> sortColumnPosNr = fromGrid.addColumn(item -> {
            if((item.getPositionNumber() != null) && (!item.getPositionNumber().isEmpty())){
                try{
                    return Integer.valueOf(item.getPositionNumber().split("[^0-9]")[0]);
                }
                catch (Exception e){
                    return 4999;
                }
            }
            else{
                return 5000;
            }
        });
        sortColumnPosNr.setVisible(false);

        codeColumnFrom = fromGrid.addColumn("productCode").setHeader("Code").setResizable(true).setWidth("190px").setFlexGrow(0);
        posColumnFrom = fromGrid.addColumn(o -> o.getPositionNumber())
                .setComparator((o1, o2) -> {
                    return compareOnderdeel(o1.getPositionNumber(), o2.getPositionNumber()); // ascending of descending handled by Vaadin
                })
                .setHeader("Pos")
                .setResizable(true)
                .setWidth("75px")
                .setFlexGrow(0);
        internalNameColumnFrom = fromGrid.addColumn("internalName").setSortable(true)
                .setComparator((o1, o2) -> compareOnderdeel(o1.getInternalName(), o2.getInternalName()))
                .setHeader("Naam").setResizable(true).setFlexGrow(2);
        fromGrid.sort(List.of(new GridSortOrder<>(internalNameColumnFrom, SortDirection.ASCENDING)));


        commentColumnFrom = fromGrid.addColumn("comment").setHeader("Commentaar").setWidth("330px").setFlexGrow(1).setResizable(true);

        purchaceColumnFrom = fromGrid.addColumn(item -> {
            if(item.getPurchasePrice() != null){
                return "€ " + df.format(item.getPurchasePrice());
            }
            else{
                return "-";
            }
        }).setHeader("Aankoop").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        marginColumnFrom = fromGrid.addColumn(item -> {
            if((item.getSellMargin() != null) && (!item.getSellMargin().isNaN())){
                return df.format(item.getSellMargin());
            }
            else{
                return "-";
            }
        }).setHeader("Marge A").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        sellComumnFrom = fromGrid.addColumn(item -> {
            if((item.getSellPrice() != null) && (!item.getSellPrice().isNaN())){
                return "€ " + df.format(item.getSellPrice());
            }
            else{
                return "-";
            }
        }).setHeader("Verkoop A").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        marginIndustryColumnFrom = fromGrid.addColumn(item -> {
            if(item.getSellMarginIndustry() != null){
                return df.format(item.getSellMarginIndustry());
            }
            else{
                return "-";
            }
        }).setHeader("Marge I").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);
        marginIndustryColumnFrom.setPartNameGenerator(item -> "industry-column");

        sellIndustryComumnFrom = fromGrid.addColumn(item -> {
            if(item.getSellPriceIndustry() != null){
                return "€ " + df.format(item.getSellPriceIndustry());
            }
            else{
                return "-";
            }
        }).setHeader("Verkoop I").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        unitColumnFrom = fromGrid.addColumn(item -> {
            if(item.getUnit() != null){
                return item.getUnit();
            }
            else{
                return "";
            }
        }).setHeader("E/H.").setResizable(true).setWidth("80px").setFlexGrow(0).setFrozenToEnd(true);

        fromGrid.setPartNameGenerator(product -> {
            return "industry";
        });

        fromGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        fromGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        fromGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        fromGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        fromGrid.setRowsDraggable(true);
        fromGrid.addDragStartListener(e -> {
            draggedItem = e.getDraggedItems();
            fromGrid.setDropMode(GridDropMode.ON_GRID);
            toGrid.setDropMode(GridDropMode.ON_GRID);
        });
        fromGrid.addDropListener(e -> {
            //TODO save items
            dataViewFrom.addItems(draggedItem);
            if(draggedItem != null && draggedItem.size() > 0){
                draggedItem.stream().filter(item -> ((item.getSet() == null) || (item.getSet() == false))).forEach(item -> {
                    item.setId(null);
                    item.setSetElement(false);
                    item.setProductLevel1(cbProductLevel1From.getValue());
                    item.setProductLevel2(cbProductLevel2From.getValue());
                    item.setProductLevel3(cbProductLevel3From.getValue());
                    item.setProductLevel4(cbProductLevel4From.getValue());
                    item.setProductLevel5(cbProductLevel5From.getValue());
                    productService.save(item);
                });
            }
        });
        fromGrid.addDragEndListener(e -> {
            draggedItem = null;
            fromGrid.setDropMode(null);
            toGrid.setDropMode(null);
        });

        fromGrid.addItemDoubleClickListener(event -> {
            selectedProduct = event.getItem();
            editorFrom.cancel();
            editorFrom.editItem(event.getItem());
            Component editorComponent = event.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });

        Grid.Column<Product> trashColumnFrom = fromGrid.addComponentColumn(item -> {
            Button closeButton = new Button(new Icon(VaadinIcon.TRASH));
            closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            closeButton.addClickListener(event -> {
                dataViewFrom.removeItem(item);
                productService.delete(item);
            });
            return closeButton;
        }).setFlexGrow(0).setHeader(new Icon(VaadinIcon.TRASH)).setTextAlign(ColumnTextAlign.CENTER);
        trashColumnFrom.addClassName("center-header");
        trashColumnFrom.getElement().getThemeList().add("no-min-width");
        trashColumnFrom.setWidth("70px");

    }

    private void setUpToGrid() {
        Grid.Column<Product> sortColumnPosNr = toGrid.addColumn(item -> {
            if((item.getPositionNumber() != null) && (!item.getPositionNumber().isEmpty())){
                try{
                    return Integer.valueOf(item.getPositionNumber().split("[^0-9]")[0]);
                }
                catch (Exception e){
                    return 4999;
                }
            }
            else{
                return 5000;
            }
        });
        sortColumnPosNr.setVisible(false);

        codeColumnTo = toGrid.addColumn("productCode").setHeader("Code").setResizable(true).setWidth("190px").setFlexGrow(0);
        posColumnTo = toGrid.addColumn(o -> o.getPositionNumber())
                .setComparator((o1, o2) -> {
                    return compareOnderdeel(o1.getPositionNumber(), o2.getPositionNumber()); // ascending of descending handled by Vaadin
                })
                .setHeader("Pos")
                .setResizable(true)
                .setWidth("75px")
                .setFlexGrow(0);
        internalNameColumnTo = toGrid.addColumn("internalName").setSortable(true)
                .setComparator((o1, o2) -> compareOnderdeel(o1.getInternalName(), o2.getInternalName()))
                .setHeader("Naam").setResizable(true).setFlexGrow(2);
        toGrid.sort(List.of(new GridSortOrder<>(internalNameColumnTo, SortDirection.ASCENDING)));


        commentColumnTo = toGrid.addColumn("comment").setHeader("Commentaar").setWidth("330px").setFlexGrow(1).setResizable(true);

        purchaceColumnTo = toGrid.addColumn(item -> {
            if(item.getPurchasePrice() != null){
                return "€ " + df.format(item.getPurchasePrice());
            }
            else{
                return "-";
            }
        }).setHeader("Aankoop").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        marginColumnTo = toGrid.addColumn(item -> {
            if((item.getSellMargin() != null) && (!item.getSellMargin().isNaN())){
                return df.format(item.getSellMargin());
            }
            else{
                return "-";
            }
        }).setHeader("Marge A").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        sellComumnTo = toGrid.addColumn(item -> {
            if((item.getSellPrice() != null) && (!item.getSellPrice().isNaN())){
                return "€ " + df.format(item.getSellPrice());
            }
            else{
                return "-";
            }
        }).setHeader("Verkoop A").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        marginIndustryColumnTo = toGrid.addColumn(item -> {
            if(item.getSellMarginIndustry() != null){
                return df.format(item.getSellMarginIndustry());
            }
            else{
                return "-";
            }
        }).setHeader("Marge I").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);
        marginIndustryColumnTo.setPartNameGenerator(item -> "industry-column");

        sellIndustryComumnTo = toGrid.addColumn(item -> {
            if(item.getSellPriceIndustry() != null){
                return "€ " + df.format(item.getSellPriceIndustry());
            }
            else{
                return "-";
            }
        }).setHeader("Verkoop I").setResizable(true).setWidth("120px").setFlexGrow(0).setTextAlign(ColumnTextAlign.END);

        unitColumnTo = toGrid.addColumn(item -> {
            if(item.getUnit() != null){
                return item.getUnit();
            }
            else{
                return "";
            }
        }).setHeader("E/H.").setResizable(true).setWidth("80px").setFlexGrow(0).setFrozenToEnd(true);

        toGrid.setPartNameGenerator(product -> {
            return "industry";
        });

        toGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        toGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        toGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        toGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        toGrid.setRowsDraggable(true);
        toGrid.addDragStartListener(e -> {
            draggedItem = e.getDraggedItems();
            fromGrid.setDropMode(GridDropMode.ON_GRID);
            toGrid.setDropMode(GridDropMode.ON_GRID);
        });
        toGrid.addDropListener(e -> {
            //TODO save items
            dataViewTo.addItems(draggedItem);
            if(draggedItem != null && draggedItem.size() > 0){
                draggedItem.stream().filter(item -> ((item.getSet() == null) || (item.getSet() == false))).forEach(item -> {
                    item.setId(null);
                    item.setSetElement(false);
                    item.setProductLevel1(cbProductLevel1To.getValue());
                    item.setProductLevel2(cbProductLevel2To.getValue());
                    item.setProductLevel3(cbProductLevel3To.getValue());
                    item.setProductLevel4(cbProductLevel4To.getValue());
                    item.setProductLevel5(cbProductLevel5To.getValue());
                    productService.save(item);
                });
            }
        });
        toGrid.addDragEndListener(e -> {
            draggedItem = null;
            fromGrid.setDropMode(null);
            toGrid.setDropMode(null);
        });

        toGrid.addItemDoubleClickListener(event -> {
            selectedProduct = event.getItem();
            if (editorTo.isOpen()) {
                editorTo.cancel();
            }
            editorTo.editItem(selectedProduct);
            Component editorComponent = event.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable<?>) editorComponent).focus();
            }
        });

        Grid.Column<Product> trashColumnTo = toGrid.addComponentColumn(item -> {
            Button closeButton = new Button(new Icon(VaadinIcon.TRASH));
            closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            closeButton.addClickListener(event -> {
                dataViewTo.removeItem(item);
                productService.delete(item);
            });
            return closeButton;
        }).setFlexGrow(0).setHeader(new Icon(VaadinIcon.TRASH)).setTextAlign(ColumnTextAlign.CENTER);
        trashColumnTo.addClassName("center-header");
        trashColumnTo.getElement().getThemeList().add("no-min-width");
        trashColumnTo.setWidth("70px");
    }

    private Div createGridLayout() {
        Div wrapper = new Div();
        wrapper.setSizeFull();
        wrapper.setClassName("grid-wrapper");
        SplitLayout gridSplitLayoout = new SplitLayout();
        gridSplitLayoout.setHeight("100%");
        gridSplitLayoout.setOrientation(SplitLayout.Orientation.VERTICAL);
        gridSplitLayoout.setSplitterPosition(80);
        gridSplitLayoout.addToPrimary(createFromLayout(), fromGrid);
        gridSplitLayoout.addToSecondary(createToLayout(), toGrid);
        wrapper.add(gridSplitLayoout);
        return wrapper;
    }

    private Div createToLayout() {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setWidth("100%");
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setWidth("100%");
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        HorizontalLayout hLayout1 = new HorizontalLayout();
        hLayout1.setWidth("100%");
        HorizontalLayout hLayout2 = new HorizontalLayout();
        hLayout2.setWidth("100%");

        cbProductLevel1To = new ComboBox("");
        cbProductLevel1To.setWidth("100%");
        cbProductLevel2To = new ComboBox("");
        cbProductLevel2To.setWidth("100%");
        cbProductLevel3To = new ComboBox("");
        cbProductLevel3To.setWidth("100%");
        cbProductLevel4To = new ComboBox("");
        cbProductLevel4To.setWidth("100%");
        cbProductLevel5To = new ComboBox("");
        cbProductLevel5To.setWidth("100%");
        cbProductLevel6To = new ComboBox("");
        cbProductLevel6To.setWidth("100%");
        cbProductLevel7To = new ComboBox("");
        cbProductLevel7To.setWidth("100%");
        hLayout1.add(
                setUpHorizontalLayoutFor(cbProductLevel1To,bAddProductLevel1, E_Product_Level.PRODUCTLEVEL1),
                setUpHorizontalLayoutFor(cbProductLevel2To,bAddProductLevel2,E_Product_Level.PRODUCTLEVEL2),
                setUpHorizontalLayoutFor(cbProductLevel3To,bAddProductLevel3,E_Product_Level.PRODUCTLEVEL3),
                setUpHorizontalLayoutFor(cbProductLevel4To,bAddProductLevel4,E_Product_Level.PRODUCTLEVEL4),
                setUpHorizontalLayoutFor(cbProductLevel5To,bAddProductLevel5,E_Product_Level.PRODUCTLEVEL5)
                //setUpHorizontalLayoutFor(cbProductLevel6,bAddProductLevel6,E_Product_Level.PRODUCTLEVEL6),
                //setUpHorizontalLayoutFor(cbProductLevel7,bAddProductLevel7,E_Product_Level.PRODUCTLEVEL7),
        );

        editorDiv.add(hLayout1);
        return editorLayoutDiv;
    }

    private Div createFromLayout() {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setWidth("100%");
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setWidth("100%");
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        HorizontalLayout hLayout1 = new HorizontalLayout();
        hLayout1.setWidth("100%");
        HorizontalLayout hLayout2 = new HorizontalLayout();
        hLayout2.setWidth("100%");

        cbProductLevel1From = new ComboBox("");
        cbProductLevel1From.setWidth("100%");
        cbProductLevel2From = new ComboBox("");
        cbProductLevel2From.setWidth("100%");
        cbProductLevel3From = new ComboBox("");
        cbProductLevel3From.setWidth("100%");
        cbProductLevel4From = new ComboBox("");
        cbProductLevel4From.setWidth("100%");
        cbProductLevel5From = new ComboBox("");
        cbProductLevel5From.setWidth("100%");
        cbProductLevel6From = new ComboBox("");
        cbProductLevel6From.setWidth("100%");
        cbProductLevel7From = new ComboBox("");
        cbProductLevel7From.setWidth("100%");
        hLayout1.add(
                setUpHorizontalLayoutFor(cbProductLevel1From,bAddProductLevel1, E_Product_Level.PRODUCTLEVEL1),
                setUpHorizontalLayoutFor(cbProductLevel2From,bAddProductLevel2,E_Product_Level.PRODUCTLEVEL2),
                setUpHorizontalLayoutFor(cbProductLevel3From,bAddProductLevel3,E_Product_Level.PRODUCTLEVEL3),
                setUpHorizontalLayoutFor(cbProductLevel4From,bAddProductLevel4,E_Product_Level.PRODUCTLEVEL4),
                setUpHorizontalLayoutFor(cbProductLevel5From,bAddProductLevel5,E_Product_Level.PRODUCTLEVEL5)
                //setUpHorizontalLayoutFor(cbProductLevel6,bAddProductLevel6,E_Product_Level.PRODUCTLEVEL6),
                //setUpHorizontalLayoutFor(cbProductLevel7,bAddProductLevel7,E_Product_Level.PRODUCTLEVEL7),
        );

        editorDiv.add(hLayout1);
        return editorLayoutDiv;
    }

    private HorizontalLayout setUpHorizontalLayoutFor(ComboBox comboBox, Button addButton, E_Product_Level productLevel) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
//        addButton = new Button(new Icon(VaadinIcon.PLUS));
//        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
//                ButtonVariant.LUMO_WARNING);
//        addButton.addClickListener(event -> {
//            switch(productLevel) {
//                case E_Product_Level.PRODUCTLEVEL1:
//                    Notification.show("Product level 1");
//                    selectedProductLevel = E_Product_Level.PRODUCTLEVEL1;
//                    break;
//                case E_Product_Level.PRODUCTLEVEL2:
//                    Notification.show("Product level 2");
//                    selectedProductLevel = E_Product_Level.PRODUCTLEVEL2;
//                    break;
//                case E_Product_Level.PRODUCTLEVEL3:
//                    Notification.show("Product level 3");
//                    selectedProductLevel = E_Product_Level.PRODUCTLEVEL3;
//                    break;
//                case E_Product_Level.PRODUCTLEVEL4:
//                    Notification.show("Product level 4");
//                    selectedProductLevel = E_Product_Level.PRODUCTLEVEL4;
//                    break;
//                case E_Product_Level.PRODUCTLEVEL5:
//                    Notification.show("Product level 5");
//                    selectedProductLevel = E_Product_Level.PRODUCTLEVEL5;
//                    break;
//                case E_Product_Level.PRODUCTLEVEL6:
//                    Notification.show("Product level 6");
//                    selectedProductLevel = E_Product_Level.PRODUCTLEVEL6;
//                    break;
//                case E_Product_Level.PRODUCTLEVEL7:
//                    Notification.show("Product level 7");
//                    selectedProductLevel = E_Product_Level.PRODUCTLEVEL7;
//                    break;
//                default:
//                    Notification.show("Geen geselecteerd niveau!");
//            }
//        });
        horizontalLayout.add(comboBox);
        return horizontalLayout;
    }

    private void fillComboBoxLevel1WithItemsToStartFrom() {
        Optional<List<ProductLevel1>>optLevel1List = productLevel1Service.getAllProductLevel1();
        if (!optLevel1List.isEmpty()) {
            cbProductLevel1From.setEnabled(true);
            level1ListFrom = optLevel1List.get();
            cbProductLevel1From.setItems(level1ListFrom);
            cbProductLevel1From.setItemLabelGenerator(x -> x.getName());
        }
        else{
            cbProductLevel1From.setEnabled(false);
            level1ListFrom = new ArrayList<>();
        }
    }

    private void fillComboBoxLevel1WithItemsToStartTo() {
        Optional<List<ProductLevel1>>optLevel1List = productLevel1Service.getAllProductLevel1();
        if (!optLevel1List.isEmpty()) {
            cbProductLevel1To.setEnabled(true);
            level1ListTo = optLevel1List.get();
            cbProductLevel1To.setItems(level1ListTo);
            cbProductLevel1To.setItemLabelGenerator(x -> x.getName());
        }
        else{
            cbProductLevel1To.setEnabled(false);
            level1ListTo = new ArrayList<>();
        }
    }

    private void setUpProductLevelComboBoxesTo() {
        cbProductLevel2To.setEnabled(false);
        cbProductLevel3To.setEnabled(false);
        cbProductLevel4To.setEnabled(false);
        cbProductLevel5To.setEnabled(false);
        cbProductLevel6To.setEnabled(false);
        cbProductLevel7To.setEnabled(false);
        fillComboBoxLevel1WithItemsToStartTo();


        cbProductLevel1To.addClassName("highlight-border");
        cbProductLevel1To.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try{
                Optional<List<ProductLevel2>>productLevel2List = productLevel2Service.getProductLevel2sFromPreviousLevels(event.getValue());
                if (!productLevel2List.isEmpty()) {
                    cbProductLevel2To.setEnabled(true);
                    level2List = productLevel2List.get();
                    cbProductLevel2To.setPlaceholder("");
                    cbProductLevel2To.setItems(level2List);
                    cbProductLevel2To.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel3To.setPlaceholder("");
                    cbProductLevel3To.clear();
                    cbProductLevel3To.setEnabled(false);
                    cbProductLevel4To.setPlaceholder("");
                    cbProductLevel4To.clear();
                    cbProductLevel4To.setEnabled(false);
                    cbProductLevel5To.setPlaceholder("");
                    cbProductLevel5To.clear();
                    cbProductLevel5To.setEnabled(false);
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName());
                    if(allProductsByCategory.isPresent()){
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    }
                    else{
                        List<Product>products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }


                }
                else{
                    cbProductLevel2To.setPlaceholder("");
                    cbProductLevel2To.clear();
                    cbProductLevel2To.setEnabled(false);
                    cbProductLevel3To.setPlaceholder("");
                    cbProductLevel3To.clear();
                    cbProductLevel3To.setEnabled(false);
                    cbProductLevel4To.setPlaceholder("");
                    cbProductLevel4To.clear();
                    cbProductLevel4To.setEnabled(false);
                    cbProductLevel5To.setPlaceholder("");
                    cbProductLevel5To.clear();
                    cbProductLevel5To.setEnabled(false);
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName());
                    if(allProductsByCategory.isPresent()){
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    }
                    else{
                        List<Product>products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }

        });

        cbProductLevel2To.addClassName("highlight-border");
        cbProductLevel2To.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel3>> productLevel3List = productLevel3Service.getProductLevel3sFromPreviousLevels(cbProductLevel2To.getValue(), cbProductLevel1To.getValue());
                if (!productLevel3List.isEmpty()) {
                    cbProductLevel3To.setEnabled(true);
                    level3List = productLevel3List.get();
                    cbProductLevel3To.setItems(level3List);
                    cbProductLevel3To.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel4To.setPlaceholder("");
                    cbProductLevel4To.clear();
                    cbProductLevel4To.setEnabled(false);
                    cbProductLevel5To.setPlaceholder("");
                    cbProductLevel5To.clear();
                    cbProductLevel5To.setEnabled(false);
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }

                } else {
                    cbProductLevel3To.setPlaceholder("");
                    cbProductLevel3To.clear();
                    cbProductLevel3To.setEnabled(false);
                    cbProductLevel4To.setPlaceholder("");
                    cbProductLevel4To.clear();
                    cbProductLevel4To.setEnabled(false);
                    cbProductLevel5To.setPlaceholder("");
                    cbProductLevel5To.clear();
                    cbProductLevel5To.setEnabled(false);
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel3To.addClassName("highlight-border");
        cbProductLevel3To.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel4>> productLevel4List = productLevel4Service.getProductLevel4ByPreviousLevelNames(cbProductLevel3To.getValue(), cbProductLevel2To.getValue(), cbProductLevel1To.getValue());
                if (!productLevel4List.isEmpty()) {
                    cbProductLevel4To.setEnabled(true);
                    level4List = productLevel4List.get();
                    cbProductLevel4To.setItems(level4List);
                    cbProductLevel4To.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel5To.setPlaceholder("");
                    cbProductLevel5To.clear();
                    cbProductLevel5To.setEnabled(false);
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }


                } else {
                    cbProductLevel4To.setPlaceholder("");
                    cbProductLevel4To.clear();
                    cbProductLevel4To.setEnabled(false);
                    cbProductLevel5To.setPlaceholder("");
                    cbProductLevel5To.clear();
                    cbProductLevel5To.setEnabled(false);
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel4To.addClassName("highlight-border");
        cbProductLevel4To.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel5>> productLevel5List = productLevel5Service.getProductLevel5ByPreviousLevelNames(cbProductLevel4To.getValue(), cbProductLevel3To.getValue(), cbProductLevel2To.getValue(), cbProductLevel1To.getValue());
                if (!productLevel5List.isEmpty()) {
                    cbProductLevel5To.setEnabled(true);
                    level5List = productLevel5List.get();
                    cbProductLevel5To.setItems(level5List);
                    cbProductLevel5To.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel3To.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }

                } else {
                    cbProductLevel5To.setPlaceholder("");
                    cbProductLevel5To.clear();
                    cbProductLevel5To.setEnabled(false);
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);
                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel3To.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }
                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel5To.addClassName("highlight-border");
        cbProductLevel5To.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel6>> productLevel6List = productLevel6Service.getProductLevel6ByPreviousLevelNames(cbProductLevel5To.getValue(), cbProductLevel4To.getValue(), cbProductLevel3To.getValue(), cbProductLevel2To.getValue(), cbProductLevel1To.getValue());
                if (!productLevel6List.isEmpty()) {
                    cbProductLevel6To.setEnabled(true);
                    level6List = productLevel6List.get();
                    cbProductLevel6To.setItems(level6List);
                    cbProductLevel6To.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel4To.getValue().getName(), cbProductLevel3To.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }


                } else {
                    cbProductLevel6To.setPlaceholder("");
                    cbProductLevel6To.clear();
                    cbProductLevel6To.setEnabled(false);
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);
                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel4To.getValue().getName(), cbProductLevel3To.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });
        cbProductLevel6To.addClassName("highlight-border");
        cbProductLevel6To.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel7>> productLevel7List = productLevel7Service.getProductLevel7ByPreviousLevelNames(cbProductLevel6To.getValue(), cbProductLevel5To.getValue(), cbProductLevel4To.getValue(), cbProductLevel3To.getValue(), cbProductLevel2To.getValue(), cbProductLevel1To.getValue());
                if (!productLevel7List.isEmpty()) {
                    cbProductLevel7To.setEnabled(true);
                    level7List = productLevel7List.get();
                    cbProductLevel7To.setItems(level7List);
                    cbProductLevel7To.setItemLabelGenerator(x -> x.getName());


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel5To.getValue().getName(), cbProductLevel4To.getValue().getName(), cbProductLevel3To.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsToGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsToGrid(productForGrid);
                    }


                } else {
                    cbProductLevel7To.setPlaceholder("");
                    cbProductLevel7To.clear();
                    cbProductLevel7To.setEnabled(false);
                    if (selectedProduct == null) {
                        //filter Products
                        Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel5To.getValue().getName(), cbProductLevel4To.getValue().getName(), cbProductLevel3To.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                        if (allProductsByCategory.isPresent()) {
                            productForGrid = allProductsByCategory.get();
                            addItemsToGrid(allProductsByCategory.get());
                        } else {
                            List<Product> products = new ArrayList<>();
                            productForGrid = products;
                            addItemsToGrid(productForGrid);
                        }
                    }
                }

            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel7To.addClassName("highlight-border");
        cbProductLevel7To.addValueChangeListener(event -> {
            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel6To.getValue().getName(), cbProductLevel5To.getValue().getName(), cbProductLevel4To.getValue().getName(), cbProductLevel3To.getValue().getName(), cbProductLevel2To.getValue().getName(), cbProductLevel1To.getValue().getName());
                if (allProductsByCategory.isPresent()) {
                    productForGrid = allProductsByCategory.get();
                    addItemsToGrid(productForGrid);
                } else {
                    List<Product> products = new ArrayList<>();
                    productForGrid = products;
                    addItemsToGrid(productForGrid);
                }
            }
            finally {
                updating.set(false);
            }
        });
    }


    private void setUpProductLevelComboBoxesFrom() {
        cbProductLevel2From.setEnabled(false);
        cbProductLevel3From.setEnabled(false);
        cbProductLevel4From.setEnabled(false);
        cbProductLevel5From.setEnabled(false);
        cbProductLevel6From.setEnabled(false);
        cbProductLevel7From.setEnabled(false);
        fillComboBoxLevel1WithItemsToStartFrom();


        cbProductLevel1From.addClassName("highlight-border");
        cbProductLevel1From.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try{
                Optional<List<ProductLevel2>>productLevel2List = productLevel2Service.getProductLevel2sFromPreviousLevels(event.getValue());
                if (!productLevel2List.isEmpty()) {
                    cbProductLevel2From.setEnabled(true);
                    level2List = productLevel2List.get();
                    cbProductLevel2From.setPlaceholder("");
                    cbProductLevel2From.setItems(level2List);
                    cbProductLevel2From.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel3From.setPlaceholder("");
                    cbProductLevel3From.clear();
                    cbProductLevel3From.setEnabled(false);
                    cbProductLevel4From.setPlaceholder("");
                    cbProductLevel4From.clear();
                    cbProductLevel4From.setEnabled(false);
                    cbProductLevel5From.setPlaceholder("");
                    cbProductLevel5From.clear();
                    cbProductLevel5From.setEnabled(false);
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName());
                    if(allProductsByCategory.isPresent()){
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    }
                    else{
                        List<Product>products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }


                }
                else{
                    cbProductLevel2From.setPlaceholder("");
                    cbProductLevel2From.clear();
                    cbProductLevel2From.setEnabled(false);
                    cbProductLevel3From.setPlaceholder("");
                    cbProductLevel3From.clear();
                    cbProductLevel3From.setEnabled(false);
                    cbProductLevel4From.setPlaceholder("");
                    cbProductLevel4From.clear();
                    cbProductLevel4From.setEnabled(false);
                    cbProductLevel5From.setPlaceholder("");
                    cbProductLevel5From.clear();
                    cbProductLevel5From.setEnabled(false);
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName());
                    if(allProductsByCategory.isPresent()){
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    }
                    else{
                        List<Product>products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }

        });

        cbProductLevel2From.addClassName("highlight-border");
        cbProductLevel2From.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel3>> productLevel3List = productLevel3Service.getProductLevel3sFromPreviousLevels(cbProductLevel2From.getValue(), cbProductLevel1From.getValue());
                if (!productLevel3List.isEmpty()) {
                    cbProductLevel3From.setEnabled(true);
                    level3List = productLevel3List.get();
                    cbProductLevel3From.setItems(level3List);
                    cbProductLevel3From.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel4From.setPlaceholder("");
                    cbProductLevel4From.clear();
                    cbProductLevel4From.setEnabled(false);
                    cbProductLevel5From.setPlaceholder("");
                    cbProductLevel5From.clear();
                    cbProductLevel5From.setEnabled(false);
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }

                } else {
                    cbProductLevel3From.setPlaceholder("");
                    cbProductLevel3From.clear();
                    cbProductLevel3From.setEnabled(false);
                    cbProductLevel4From.setPlaceholder("");
                    cbProductLevel4From.clear();
                    cbProductLevel4From.setEnabled(false);
                    cbProductLevel5From.setPlaceholder("");
                    cbProductLevel5From.clear();
                    cbProductLevel5From.setEnabled(false);
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel3From.addClassName("highlight-border");
        cbProductLevel3From.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel4>> productLevel4List = productLevel4Service.getProductLevel4ByPreviousLevelNames(cbProductLevel3From.getValue(), cbProductLevel2From.getValue(), cbProductLevel1From.getValue());
                if (!productLevel4List.isEmpty()) {
                    cbProductLevel4From.setEnabled(true);
                    level4List = productLevel4List.get();
                    cbProductLevel4From.setItems(level4List);
                    cbProductLevel4From.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel5From.setPlaceholder("");
                    cbProductLevel5From.clear();
                    cbProductLevel5From.setEnabled(false);
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }


                } else {
                    cbProductLevel4From.setPlaceholder("");
                    cbProductLevel4From.clear();
                    cbProductLevel4From.setEnabled(false);
                    cbProductLevel5From.setPlaceholder("");
                    cbProductLevel5From.clear();
                    cbProductLevel5From.setEnabled(false);
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel4From.addClassName("highlight-border");
        cbProductLevel4From.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel5>> productLevel5List = productLevel5Service.getProductLevel5ByPreviousLevelNames(cbProductLevel4From.getValue(), cbProductLevel3From.getValue(), cbProductLevel2From.getValue(), cbProductLevel1From.getValue());
                if (!productLevel5List.isEmpty()) {
                    cbProductLevel5From.setEnabled(true);
                    level5List = productLevel5List.get();
                    cbProductLevel5From.setItems(level5List);
                    cbProductLevel5From.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel3From.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }

                } else {
                    cbProductLevel5From.setPlaceholder("");
                    cbProductLevel5From.clear();
                    cbProductLevel5From.setEnabled(false);
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);
                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel3From.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }
                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel5From.addClassName("highlight-border");
        cbProductLevel5From.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel6>> productLevel6List = productLevel6Service.getProductLevel6ByPreviousLevelNames(cbProductLevel5From.getValue(), cbProductLevel4From.getValue(), cbProductLevel3From.getValue(), cbProductLevel2From.getValue(), cbProductLevel1From.getValue());
                if (!productLevel6List.isEmpty()) {
                    cbProductLevel6From.setEnabled(true);
                    level6List = productLevel6List.get();
                    cbProductLevel6From.setItems(level6List);
                    cbProductLevel6From.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel4From.getValue().getName(), cbProductLevel3From.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }


                } else {
                    cbProductLevel6From.setPlaceholder("");
                    cbProductLevel6From.clear();
                    cbProductLevel6From.setEnabled(false);
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);
                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel4From.getValue().getName(), cbProductLevel3From.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });
        cbProductLevel6From.addClassName("highlight-border");
        cbProductLevel6From.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<ProductLevel7>> productLevel7List = productLevel7Service.getProductLevel7ByPreviousLevelNames(cbProductLevel6From.getValue(), cbProductLevel5From.getValue(), cbProductLevel4From.getValue(), cbProductLevel3From.getValue(), cbProductLevel2From.getValue(), cbProductLevel1From.getValue());
                if (!productLevel7List.isEmpty()) {
                    cbProductLevel7From.setEnabled(true);
                    level7List = productLevel7List.get();
                    cbProductLevel7From.setItems(level7List);
                    cbProductLevel7From.setItemLabelGenerator(x -> x.getName());


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel5From.getValue().getName(), cbProductLevel4From.getValue().getName(), cbProductLevel3From.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productForGrid = allProductsByCategory.get();
                        addItemsFromGrid(productForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productForGrid = products;
                        addItemsFromGrid(productForGrid);
                    }


                } else {
                    cbProductLevel7From.setPlaceholder("");
                    cbProductLevel7From.clear();
                    cbProductLevel7From.setEnabled(false);
                    if (selectedProduct == null) {
                        //filter Products
                        Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel5From.getValue().getName(), cbProductLevel4From.getValue().getName(), cbProductLevel3From.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                        if (allProductsByCategory.isPresent()) {
                            productForGrid = allProductsByCategory.get();
                            addItemsFromGrid(allProductsByCategory.get());
                        } else {
                            List<Product> products = new ArrayList<>();
                            productForGrid = products;
                            addItemsFromGrid(productForGrid);
                        }
                    }
                }

            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel7From.addClassName("highlight-border");
        cbProductLevel7From.addValueChangeListener(event -> {
            if (updating.get()) return;
            updating.set(true);
            try {
                Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel6From.getValue().getName(), cbProductLevel5From.getValue().getName(), cbProductLevel4From.getValue().getName(), cbProductLevel3From.getValue().getName(), cbProductLevel2From.getValue().getName(), cbProductLevel1From.getValue().getName());
                if (allProductsByCategory.isPresent()) {
                    productForGrid = allProductsByCategory.get();
                    addItemsFromGrid(productForGrid);
                } else {
                    List<Product> products = new ArrayList<>();
                    productForGrid = products;
                    addItemsFromGrid(productForGrid);
                }
            }
            finally {
                updating.set(false);
            }
        });
    }

    private int compareOnderdeel(String s1, String s2) {
        if((s1 != null) && (s2 != null)){
            List<Object> parts1 = splitAlphaNumeric(s1);
            List<Object> parts2 = splitAlphaNumeric(s2);

            int len = Math.min(parts1.size(), parts2.size());

            for (int i = 0; i < len; i++) {
                Object p1 = parts1.get(i);
                Object p2 = parts2.get(i);

                int cmp;
                if (p1 instanceof String && p2 instanceof String) {
                    cmp = ((String) p1).compareToIgnoreCase((String) p2);
                } else if (p1 instanceof Number && p2 instanceof Number) {
                    cmp = Double.compare(((Number) p1).doubleValue(), ((Number) p2).doubleValue());
                } else {
                    // String vs Number → String komt altijd eerst
                    cmp = (p1 instanceof String) ? -1 : 1;
                }

                if (cmp != 0) return cmp;
            }

            // Als alles gelijk is, kortere string komt eerst
            return Integer.compare(parts1.size(), parts2.size());
        }
        return 9999;
    }

    private List<Object> splitAlphaNumeric(String input) {
        List<Object> parts = new ArrayList<>();

        Matcher matcher = Pattern.compile("(\\d+[\\.,]?\\d*|\\D+)").matcher(input);
        while (matcher.find()) {
            String part = matcher.group(1).trim();
            if (part.matches("\\d+[\\.,]?\\d*")) {
                part = part.replace(",", "."); // vervang komma door punt
                try {
                    parts.add(Double.parseDouble(part));
                } catch (NumberFormatException e) {
                    parts.add(part); // fallback: behandel als string
                }
            } else {
                parts.add(part);
            }
        }

        return parts;
    }

    public void addItemsFromGrid(List<Product> productList) {
        productListToShowInGridFrom.clear();
        productListToShowInGridFrom.addAll(productList);
        dataViewFrom = fromGrid.setItems(productListToShowInGridFrom);
    }

    private void addItemsToGrid(List<Product> productList) {
        productListToShowInGridTo.clear();
        productListToShowInGridTo.addAll(productList);
        dataViewTo = toGrid.setItems(productListToShowInGridTo);
    }

    public void goToSelectedFolder(String placeholder1, String placeholder2, String placeholder3, String placeholder4, String placeholder5) {
        updating.set(true);
        cbProductLevel1From.setItems(productLevel1Service.getAllProductLevel1().get());
        updating.set(false);
        if(placeholder1.length() > 0)
            cbProductLevel1From.setValue(productLevel1Service.getProductLevel1ByName(placeholder1).get());
        if(placeholder2.length() > 0)
            cbProductLevel2From.setValue(productLevel2Service.getProductLevel2ByName(placeholder2).get());
        if(placeholder3.length() > 0)
            cbProductLevel3From.setValue(productLevel3Service.getProductLevel3ByName(placeholder3).get());
        if(placeholder4.length() > 0)
            cbProductLevel4From.setValue(productLevel4Service.getProductLevel4ByName(placeholder4).get());
        if(placeholder5.length() > 0)
            cbProductLevel5From.setValue(productLevel5Service.getProductLevel5ByName(placeholder5).get());
    }

    private void tryToCalculateSellPriceAgroFrom(Product product) {
        try{
            Optional<Double>optDoublePurchasePrice = Optional.of(Double.valueOf(tfPurchasePriceFrom.getValue()));
            Optional<Double>optDoubleSellMargin = Optional.of(Double.valueOf(tfSellMarginFrom.getValue()));

            if(optDoublePurchasePrice.isPresent()) {
                if(optDoubleSellMargin.isPresent()) {
                    product.setSellPrice(optDoublePurchasePrice.get()  *(optDoubleSellMargin.get()));
                    tfSellPriceFrom.setValue(product.getSellPrice().toString());
                    productService.save(product);
                    Notification.show("Artikel is aangepast.");
                }
            }
            else{
                Notification.show("Gelieve te starten met de aankoopprijs en marge");
            }
        }
        catch (Exception e){
            Notification notification = new Notification("Gelieve decimale getallen in te vullen aub!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void tryToCalculateSellPriceIndustryFrom(Product product) {
        try{
            Optional<Double>optDoublePurchasePrice = Optional.of(Double.valueOf(tfPurchasePriceFrom.getValue()));
            Optional<Double>optDoubleSellMargin = Optional.of(Double.valueOf(tfSellIndustryMarginFrom.getValue()));

            if(optDoublePurchasePrice.isPresent()) {
                if(optDoubleSellMargin.isPresent()) {
                    product.setSellPriceIndustry(optDoublePurchasePrice.get()  *(optDoubleSellMargin.get()));
                    tfSellIndustryPriceFrom.setValue(product.getSellPriceIndustry().toString());
                    productService.save(product);
                    Notification.show("Artikel is aangepast.");
                }
            }
            else{
                Notification.show("Gelieve te starten met de aankoopprijs en marge");
            }
        }
        catch (Exception e){
            Notification notification = new Notification("Gelieve decimale getallen in te vullen aub!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void tryToCalculateSellPriceAgroTo(Product product) {
        try{
            Optional<Double>optDoublePurchasePrice = Optional.of(Double.valueOf(tfPurchasePriceTo.getValue()));
            Optional<Double>optDoubleSellMargin = Optional.of(Double.valueOf(tfSellMarginTo.getValue()));

            if(optDoublePurchasePrice.isPresent()) {
                if(optDoubleSellMargin.isPresent()) {
                    product.setSellPrice(optDoublePurchasePrice.get()  *(optDoubleSellMargin.get()));
                    tfSellPriceTo.setValue(product.getSellPrice().toString());
                    productService.save(product);
                    Notification.show("Artikel is aangepast.");
                }
            }
            else{
                Notification.show("Gelieve te starten met de aankoopprijs en marge");
            }
        }
        catch (Exception e){
            Notification notification = new Notification("Gelieve decimale getallen in te vullen aub!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void tryToCalculateSellPriceIndustryTo(Product product) {
        try{
            Optional<Double>optDoublePurchasePrice = Optional.of(Double.valueOf(tfPurchasePriceTo.getValue()));
            Optional<Double>optDoubleSellMargin = Optional.of(Double.valueOf(tfSellIndustryMarginTo.getValue()));

            if(optDoublePurchasePrice.isPresent()) {
                if(optDoubleSellMargin.isPresent()) {
                    product.setSellPriceIndustry(optDoublePurchasePrice.get()  *(optDoubleSellMargin.get()));
                    tfSellIndustryPriceTo.setValue(product.getSellPriceIndustry().toString());
                    productService.save(product);
                    Notification.show("Artikel is aangepast.");
                }
            }
            else{
                Notification.show("Gelieve te starten met de aankoopprijs en marge");
            }
        }
        catch (Exception e){
            Notification notification = new Notification("Gelieve decimale getallen in te vullen aub!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
