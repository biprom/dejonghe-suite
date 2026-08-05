package com.adverto.dejonghe.tabletdemo.tabletdemo.views.articles;

import com.adverto.dejonghe.common.dbservices.*;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.product.enums.E_Product_Level;
import com.adverto.dejonghe.common.entities.product.product.*;
import com.adverto.dejonghe.common.repos.ProductRepo;
import com.adverto.dejonghe.common.services.ProductServices;
import com.adverto.dejonghe.common.services.SetService;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.subViews.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.*;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@PageTitle("Artikelen")
@Route("artikelen")
@Menu(order = 0, icon = LineAwesomeIconUrl.COG_SOLID)
public class ImportArticleViewNieuw extends VerticalLayout implements BeforeEnterObserver {

    Notification deleteProductNotification;

    private final ProductService productService;
    private ProductServices productServices;
    private ProductLevel1Service productLevel1Service;
    private ProductLevel2Service productLevel2Service;
    private ProductLevel3Service productLevel3Service;
    private ProductLevel4Service productLevel4Service;
    private ProductLevel5Service productLevel5Service;
    private ProductLevel6Service productLevel6Service;
    private ProductLevel7Service productLevel7Service;
    private SetService setService;
    private OrderSubView orderSubView;

    private ProductRepo productRepo;
    private SupplierService supplierService;

    private SetViewSimple setViewSimple;
    private ShowImageSubVieuw showImageSubVieuw;
    private ShowPdfSubVieuw showPdfSubVieuw;
    private ShowLinkSubVieuw showLinkSubVieuw;

    private List<ProductLevel1>level1List;
    private List<ProductLevel2>level2List;
    private List<ProductLevel3>level3List;
    private List<ProductLevel4>level4List;
    private List<ProductLevel5>level5List;
    private List<ProductLevel6>level6List;
    private List<ProductLevel7>level7List;


    private Dialog orderDialog;
    private Dialog showImageDialog;
    private Dialog pdfDialog;
    private Dialog linkDialog;
    private Dialog setViewSimpleDialog;

    private final Grid<Product> grid = new Grid<>(Product.class, false);
    private List<Product> productsForGrid;

    private TextField tfGeneralFilter;
    private TextField tfFolderFilter;
    private TextField tfProductCode;
    private TextField tfPositionNumber;
    private TextField tfInternalName;
    private TextField tfComment;
    private TextField tfMoQ;
    private TextField tfUnit;

    private TextField tfPurchasePrice;
    private TextField tfSellMargin;
    private TextField tfSellIndustryMargin;
    private TextField tfSellPrice;
    private TextField tfSellIndustryPrice;

    private ComboBox<ProductLevel1>cbProductLevel1;
    private ComboBox<ProductLevel2>cbProductLevel2;
    private ComboBox<ProductLevel3>cbProductLevel3;
    private ComboBox<ProductLevel4>cbProductLevel4;
    private ComboBox<ProductLevel5>cbProductLevel5;
    private ComboBox<ProductLevel6>cbProductLevel6;
    private ComboBox<ProductLevel7>cbProductLevel7;

    Grid.Column codeColumn;
    Grid.Column posColumn;
    Grid.Column internalNameColumn;
    Grid.Column commentColumn;
    Grid.Column unitColumn;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));

    private Binder<Product> productBinder;
    Editor<Product> editor;

    private Product selectedProduct;
    Button goToFolderButton;

    AtomicBoolean updating = new AtomicBoolean(false);
    List<Product>productListToShowInGrid = new ArrayList<>();
    List<Product>temporaryProductList = new ArrayList<>();
    Optional<List<Product>> byProductCodeContaining;
    List<Product>selectedSetList = new ArrayList<>();
    MenuBar actionBar;

    public ImportArticleViewNieuw(ProductService productService,
                                  ProductRepo productRepo,
                                  ProductLevel1Service productLevel1Service,
                                  ProductLevel2Service productLevel2Service,
                                  ProductLevel3Service productLevel3Service,
                                  ProductLevel4Service productLevel4Service,
                                  ProductLevel5Service productLevel5Service,
                                  ProductLevel6Service productLevel6Service,
                                  ProductLevel7Service productLevel7Service,
                                  SupplierService supplierService,
                                  ShowImageSubVieuw showImageSubVieuw,
                                  ShowPdfSubVieuw showPdfSubVieuw,
                                  ShowLinkSubVieuw showLinkSubVieuw,
                                  SetViewSimple setViewSimple,
                                  SetService setService,
                                  ProductServices productServices,
                                  OrderSubView orderSubView) {
        this.productService = productService;
        this.productRepo = productRepo;
        this.productLevel1Service = productLevel1Service;
        this.productLevel2Service = productLevel2Service;
        this.productLevel3Service = productLevel3Service;
        this.productLevel4Service = productLevel4Service;
        this.productLevel5Service = productLevel5Service;
        this.productLevel6Service = productLevel6Service;
        this.productLevel7Service = productLevel7Service;
        this.supplierService = supplierService;
        this.showImageSubVieuw = showImageSubVieuw;
        this.showPdfSubVieuw = showPdfSubVieuw;
        this.showLinkSubVieuw = showLinkSubVieuw;
        this.setViewSimple = setViewSimple;
        this.setService = setService;
        this.productServices = productServices;
        this.orderSubView = orderSubView;

        addClassNames("master-detail-view");

        // Create UI
        setUpNumberFormat();
        setUpLinkDialog();
        setUpImageDialog();
        setUpPdfDialog();
        setUpSetSimpleDialog();
        setUpOrderDialog();
        createReportError();

        this.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");
        this.addClassName("view-wrapper");
        this.getStyle().set("background-color", "#e9ebef");

        this.setPadding(true);
        this.setSpacing(true);
        this.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(true);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.add(createEditorLayout());
        this.add(horizontalLayout);

        VerticalLayout layout = new VerticalLayout();
        layout.setHeight("92%");
        layout.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        layout.add(createSplitLayout());
        this.add(layout);

        setUpGrid();
        addDataToGrid();
        setUpBinder();
        setUpProductLevelComboBoxes();
        setUpPriceValueChangeListeners();
        setUpFilter();
    }


    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    private void setUpPdfDialog() {
        pdfDialog = new Dialog();
        pdfDialog.setCloseOnEsc(true);
        pdfDialog.setWidth("60%");
        pdfDialog.setHeight("60%");
        pdfDialog.add(showPdfSubVieuw);
        Button cancelButton = new Button("Sluiten", e -> {
            pdfDialog.close();
            refreshItemsInGrid();
        });
        pdfDialog.getFooter().add(cancelButton);
    }

    private void setUpSetSimpleDialog() {
        setViewSimpleDialog = new Dialog();
        setViewSimpleDialog.setCloseOnEsc(true);
        setViewSimpleDialog.setWidth("60%");
        setViewSimpleDialog.setHeight("60%");
        setViewSimpleDialog.setDraggable(true);
        setViewSimple.setParentDialog(setViewSimpleDialog);
        setViewSimple.setImportArticleViewNieuw(this);
        setViewSimpleDialog.add(setViewSimple);
        Button cancelButton = new Button("Sluiten", e -> {
            setViewSimpleDialog.close();
            refreshItemsInGrid();
        });
        setViewSimpleDialog.getFooter().add(cancelButton);
    }

    private void setUpLinkDialog() {
        linkDialog = new Dialog();
        linkDialog.setCloseOnEsc(true);
        linkDialog.setWidth("60%");
        linkDialog.setHeight("60%");
        linkDialog.add(showLinkSubVieuw);
        Button cancelButton = new Button("Sluiten", e -> {
            linkDialog.close();
            refreshItemsInGrid();
        });
        linkDialog.getFooter().add(cancelButton);
    }

    private void setUpImageDialog() {
        showImageDialog = new Dialog();
        showImageDialog.setCloseOnEsc(true);
        showImageDialog.setWidth("60%");
        showImageDialog.setHeight("60%");
        showImageDialog.add(showImageSubVieuw);
        Button cancelButton = new Button("Sluiten", e -> {
            showImageDialog.close();
            refreshItemsInGrid();
        });
        showImageDialog.getFooter().add(cancelButton);
    }


    private Notification createReportError() {
        deleteProductNotification = new Notification();
        deleteProductNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteProductNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je dit artikel wil wissen?"), retryBtn,
                createCloseBtn(deleteProductNotification));
        layout.setAlignItems(Alignment.CENTER);

        deleteProductNotification.add(layout);

        return deleteProductNotification;
    }

    public Button createCloseBtn(Notification notification) {
        Button closeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    notification.close();
                    if(productsForGrid != null){
                        //when filter is selected
                        productListToShowInGrid.remove(grid.getSelectedItems());
                        productService.deleteSelecteditems(grid.getSelectedItems());
                        grid.setItems(productListToShowInGrid);
                        Notification.show("Artikel is verwijderd");
                    }
                    else{
                        //when filter is not touched
                        productListToShowInGrid.remove(grid.getSelectedItems());
                        productService.deleteSelecteditems(grid.getSelectedItems());
                        grid.setItems(productListToShowInGrid);
                        Notification.show("Artikel is verwijderd");
                    }
                    //check if selectedProduct is in sets and remove product + recalc set
                    Optional<List<Product>>setsWhereProductIsIn = setService.removeItemFromSetsAndRecalculateSet(selectedProduct);


//                    //refresh set if it is in this folder
//                    if(setsWhereProductIsIn.isPresent()){
//                        if (setsWhereProductIsIn.get().size() >= 1) {
//                            for (Product set : setsWhereProductIsIn.get()) {
//                                for(Product product : productsForGrid){
//                                    System.out.println("product : " + product.getId() + " -> " + set.getId());
//                                    if(product.getId().equals(set.getId())){
//                                        product.setPurchasePrice(setService.tryToCalculatePurchasePrice(product));
//                                        product.setSellPrice(setService.tryToCalculateSellAgroPrice(product));
//                                        product.setSellPriceIndustry(setService.tryToCalculateSellIndustryPrice(product));
//                                    }
//                                }
//                            }
//                        }
//                    }
                    //addItemsToGrid(productsForGrid);
                    //refreshItemsInGrid();
                });
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        return closeBtn;
    }

    private void setUpFilter() {
        tfGeneralFilter.setWidth("100%");
        tfGeneralFilter.addValueChangeListener(e -> {
            if (updating.get()) return;
            updating.set(true);
            cbProductLevel1.clear();
            cbProductLevel2.clear();
            cbProductLevel3.clear();
            cbProductLevel4.clear();
            cbProductLevel5.clear();
            cbProductLevel6.clear();
            cbProductLevel7.clear();
            cbProductLevel1.setPlaceholder("");
            cbProductLevel2.setPlaceholder("");
            cbProductLevel3.setPlaceholder("");
            cbProductLevel4.setPlaceholder("");
            cbProductLevel5.setPlaceholder("");
            cbProductLevel6.setPlaceholder("");
            cbProductLevel7.setPlaceholder("");
            tfFolderFilter.setValue("");
            Optional<List<Product>> optCustomer = productService.getProductByInternalNameOrCodeOrComment(tfGeneralFilter.getValue(), tfGeneralFilter.getValue(), tfGeneralFilter.getValue());
            if(optCustomer.isPresent()) {
                productsForGrid = optCustomer.get();
                addItemsToGrid(productsForGrid);
            }
            else{
                Notification.show("Geen klanten gevonden");
            }
            updating.set(false);
        });

        tfFolderFilter.setWidth("100%");
        tfFolderFilter.addValueChangeListener(e -> {
            if (updating.get()) return;
            updating.set(true);
            tfGeneralFilter.setValue("");
            addItemsToGrid(productsForGrid.stream().filter(item -> ((item.getInternalName() != null) && (item.getInternalName().toLowerCase().contains(e.getValue().toLowerCase())))||
                    ((item.getProductCode() != null) && (item.getProductCode().toLowerCase().contains(e.getValue().toLowerCase())))||
                    ((item.getComment() != null) && (item.getComment().toLowerCase().contains(e.getValue().toLowerCase())))).collect(Collectors.toList()));
            updating.set(false);
        });
    }

    private void setUpPriceValueChangeListeners() {
        tfPurchasePrice.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                //check if there are other selected products with this code and change them
                tryToCalculateSellPriceAgro(editor.getItem());
                tryToCalculateSellPriceIndustry(editor.getItem());
            }
        });
        tfSellMargin.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                tryToCalculateSellPriceAgro(editor.getItem());
            }
        });
        tfSellIndustryMargin.addValueChangeListener(listener -> {
            if(listener.isFromClient()){
                tryToCalculateSellPriceIndustry(editor.getItem());
            }
        });
    }

    private void setUpProductLevelComboBoxes() {

        cbProductLevel2.setEnabled(false);
        cbProductLevel3.setEnabled(false);
        cbProductLevel4.setEnabled(false);
        cbProductLevel5.setEnabled(false);
        cbProductLevel6.setEnabled(false);
        cbProductLevel7.setEnabled(false);
        fillComboBoxLevel1WithItemsToStart();


        cbProductLevel1.addClassName("highlight-border");
        cbProductLevel1.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try{
                tfGeneralFilter.setValue("");
                tfFolderFilter.setValue("");
                Optional<List<ProductLevel2>>productLevel2List = productLevel2Service.getProductLevel2sFromPreviousLevels(event.getValue());
                if (!productLevel2List.isEmpty()) {
                    cbProductLevel2.setEnabled(true);
                    level2List = productLevel2List.get();
                    cbProductLevel2.setPlaceholder("");
                    cbProductLevel2.setItems(level2List);
                    cbProductLevel2.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel3.setPlaceholder("");
                    cbProductLevel3.clear();
                    cbProductLevel3.setEnabled(false);
                    cbProductLevel4.setPlaceholder("");
                    cbProductLevel4.clear();
                    cbProductLevel4.setEnabled(false);
                    cbProductLevel5.setPlaceholder("");
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName());
                    if(allProductsByCategory.isPresent()){
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    }
                    else{
                        List<Product>products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }


                }
                else{
                    cbProductLevel2.setPlaceholder("");
                    cbProductLevel2.clear();
                    cbProductLevel2.setEnabled(false);
                    cbProductLevel3.setPlaceholder("");
                    cbProductLevel3.clear();
                    cbProductLevel3.setEnabled(false);
                    cbProductLevel4.setPlaceholder("");
                    cbProductLevel4.clear();
                    cbProductLevel4.setEnabled(false);
                    cbProductLevel5.setPlaceholder("");
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName());
                    if(allProductsByCategory.isPresent()){
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    }
                    else{
                        List<Product>products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }

                }
            }
             finally {
                updating.set(false);
            }

        });

        cbProductLevel2.addClassName("highlight-border");
        cbProductLevel2.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                tfGeneralFilter.setValue("");
                tfFolderFilter.setValue("");
                Optional<List<ProductLevel3>> productLevel3List = productLevel3Service.getProductLevel3sFromPreviousLevels(cbProductLevel2.getValue(), cbProductLevel1.getValue());
                if (!productLevel3List.isEmpty()) {
                    cbProductLevel3.setEnabled(true);
                    level3List = productLevel3List.get();
                    cbProductLevel3.setItems(level3List);
                    cbProductLevel3.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel4.setPlaceholder("");
                    cbProductLevel4.clear();
                    cbProductLevel4.setEnabled(false);
                    cbProductLevel5.setPlaceholder("");
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }

                } else {
                    cbProductLevel3.setPlaceholder("");
                    cbProductLevel3.clear();
                    cbProductLevel3.setEnabled(false);
                    cbProductLevel4.setPlaceholder("");
                    cbProductLevel4.clear();
                    cbProductLevel4.setEnabled(false);
                    cbProductLevel5.setPlaceholder("");
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel3.addClassName("highlight-border");
        cbProductLevel3.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                tfGeneralFilter.setValue("");
                tfFolderFilter.setValue("");
                Optional<List<ProductLevel4>> productLevel4List = productLevel4Service.getProductLevel4ByPreviousLevelNames(cbProductLevel3.getValue(), cbProductLevel2.getValue(), cbProductLevel1.getValue());
                if (!productLevel4List.isEmpty()) {
                    cbProductLevel4.setEnabled(true);
                    level4List = productLevel4List.get();
                    cbProductLevel4.setItems(level4List);
                    cbProductLevel4.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel5.setPlaceholder("");
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }


                } else {
                    cbProductLevel4.setPlaceholder("");
                    cbProductLevel4.clear();
                    cbProductLevel4.setEnabled(false);
                    cbProductLevel5.setPlaceholder("");
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);

                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel4.addClassName("highlight-border");
        cbProductLevel4.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                tfGeneralFilter.setValue("");
                tfFolderFilter.setValue("");
                Optional<List<ProductLevel5>> productLevel5List = productLevel5Service.getProductLevel5ByPreviousLevelNames(cbProductLevel4.getValue(), cbProductLevel3.getValue(), cbProductLevel2.getValue(), cbProductLevel1.getValue());
                if (!productLevel5List.isEmpty()) {
                    cbProductLevel5.setEnabled(true);
                    level5List = productLevel5List.get();
                    cbProductLevel5.setItems(level5List);
                    cbProductLevel5.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel3.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }

                } else {
                    cbProductLevel5.setPlaceholder("");
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);
                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel3.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }
                }
            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel5.addClassName("highlight-border");
        cbProductLevel5.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                tfGeneralFilter.setValue("");
                tfFolderFilter.setValue("");
                Optional<List<ProductLevel6>> productLevel6List = productLevel6Service.getProductLevel6ByPreviousLevelNames(cbProductLevel5.getValue(), cbProductLevel4.getValue(), cbProductLevel3.getValue(), cbProductLevel2.getValue(), cbProductLevel1.getValue());
                if (!productLevel6List.isEmpty()) {
                    cbProductLevel6.setEnabled(true);
                    level6List = productLevel6List.get();
                    cbProductLevel6.setItems(level6List);
                    cbProductLevel6.setItemLabelGenerator(x -> x.getName());
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel4.getValue().getName(), cbProductLevel3.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }


                } else {
                    cbProductLevel6.setPlaceholder("");
                    cbProductLevel6.clear();
                    cbProductLevel6.setEnabled(false);
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);
                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel4.getValue().getName(), cbProductLevel3.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }

                }
            }
            finally {
                updating.set(false);
            }
        });
        cbProductLevel6.addClassName("highlight-border");
        cbProductLevel6.addValueChangeListener(event -> {

            if (updating.get()) return;
            updating.set(true);
            try {
                tfGeneralFilter.setValue("");
                tfFolderFilter.setValue("");
                Optional<List<ProductLevel7>> productLevel7List = productLevel7Service.getProductLevel7ByPreviousLevelNames(cbProductLevel6.getValue(), cbProductLevel5.getValue(), cbProductLevel4.getValue(), cbProductLevel3.getValue(), cbProductLevel2.getValue(), cbProductLevel1.getValue());
                if (!productLevel7List.isEmpty()) {
                    cbProductLevel7.setEnabled(true);
                    level7List = productLevel7List.get();
                    cbProductLevel7.setItems(level7List);
                    cbProductLevel7.setItemLabelGenerator(x -> x.getName());


                    Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel5.getValue().getName(), cbProductLevel4.getValue().getName(), cbProductLevel3.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                    if (allProductsByCategory.isPresent()) {
                        productsForGrid = allProductsByCategory.get();
                        addItemsToGrid(productsForGrid);
                    } else {
                        List<Product> products = new ArrayList<>();
                        productsForGrid = products;
                        addItemsToGrid(productsForGrid);
                    }


                } else {
                    cbProductLevel7.setPlaceholder("");
                    cbProductLevel7.clear();
                    cbProductLevel7.setEnabled(false);
                    if (selectedProduct == null) {
                        //filter Products
                        Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel5.getValue().getName(), cbProductLevel4.getValue().getName(), cbProductLevel3.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                        if (allProductsByCategory.isPresent()) {
                            productsForGrid = allProductsByCategory.get();
                            addItemsToGrid(allProductsByCategory.get());
                        } else {
                            List<Product> products = new ArrayList<>();
                            productsForGrid = products;
                            addItemsToGrid(productsForGrid);
                        }
                    }
                }

            }
            finally {
                updating.set(false);
            }
        });

        cbProductLevel7.addClassName("highlight-border");
        cbProductLevel7.addValueChangeListener(event -> {
            if (updating.get()) return;
            updating.set(true);
            try {
                tfGeneralFilter.setValue("");
                tfFolderFilter.setValue("");
                Optional<List<Product>> allProductsByCategory = productService.getAllProductsByCategory(event.getValue().getName(), cbProductLevel6.getValue().getName(), cbProductLevel5.getValue().getName(), cbProductLevel4.getValue().getName(), cbProductLevel3.getValue().getName(), cbProductLevel2.getValue().getName(), cbProductLevel1.getValue().getName());
                if (allProductsByCategory.isPresent()) {
                    productsForGrid = allProductsByCategory.get();
                    addItemsToGrid(productsForGrid);
                } else {
                    List<Product> products = new ArrayList<>();
                    productsForGrid = products;
                    addItemsToGrid(productsForGrid);
                }
            }
            finally {
                updating.set(false);
            }
        });
    }

    private void fillComboBoxLevel1WithItemsToStart() {
        Optional<List<ProductLevel1>>optLevel1List = productLevel1Service.getAllProductLevel1();
        if (!optLevel1List.isEmpty()) {
            cbProductLevel1.setEnabled(true);
            level1List = optLevel1List.get();
            cbProductLevel1.setItems(level1List);
            cbProductLevel1.setItemLabelGenerator(x -> x.getName());
        }
        else{
            cbProductLevel1.setEnabled(false);
            level1List = new ArrayList<>();
        }
    }

    private void setUpBinder() {
        // Configure Form
        productBinder = new Binder<>(Product.class);
        editor = grid.getEditor();
        editor.setBuffered(true);
        editor.setBinder(productBinder);
        productBinder.forField(tfProductCode)
                .withNullRepresentation("")
                .bind(Product::getProductCode, Product::setProductCode);
        codeColumn.setEditorComponent(tfProductCode);
        productBinder.forField(tfPositionNumber)
                .withNullRepresentation("")
                    .bind(Product::getPositionNumber, Product::setPositionNumber);
        posColumn.setEditorComponent(tfPositionNumber);
        tfInternalName.setWidth("100%");
        productBinder.forField(tfInternalName)
                .withNullRepresentation("")
                .bind(Product::getInternalName, Product::setInternalName);
        internalNameColumn.setEditorComponent(tfInternalName);
        productBinder.forField(tfPurchasePrice)
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

        productBinder.forField(tfSellMargin)
                .withNullRepresentation("0.00")
                .withConverter(
                        new StringToDoubleConverter("De ingave moet een decimaal nummer zijn (getal met een punt als komma)"))
                .bind(Product::getSellMargin, Product::setSellMargin);

        productBinder.forField(tfSellIndustryMargin)
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

        productBinder.forField(tfSellPrice)
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
        productBinder.forField(tfSellIndustryPrice)
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
        tfComment.setWidth("100%");
        productBinder.forField(tfComment)
                .withNullRepresentation("")
                .bind(Product::getComment, Product::setComment);
        commentColumn.setEditorComponent(tfComment);
        tfMoQ.setWidth("100%");
        productBinder.forField(tfMoQ)
                .withNullRepresentation("")
                .bind(Product::getMoq, Product::setMoq);
        //moqColumn.setEditorComponent(tfMoQ);
        productBinder.forField(tfUnit)
                .withNullRepresentation("")
                .bind(Product::getUnit, Product::setUnit);
        unitColumn.setEditorComponent(tfUnit);
//        productBinder.forField(cbProductLevel1)
//                        .bind(Product::getProductLevel1, Product::setProductLevel1);
//        productBinder.forField(cbProductLevel2)
//                .bind(Product::getProductLevel2, Product::setProductLevel2);
//        productBinder.forField(cbProductLevel3)
//                .bind(Product::getProductLevel3, Product::setProductLevel3);
//        productBinder.forField(cbProductLevel4)
//                .bind(Product::getProductLevel4, Product::setProductLevel4);
//        productBinder.forField(cbProductLevel5)
//                .bind(Product::getProductLevel5, Product::setProductLevel5);
//        productBinder.forField(cbProductLevel6)
//                .bind(Product::getProductLevel6, Product::setProductLevel6);
//        productBinder.forField(cbProductLevel7)
//                .bind(Product::getProductLevel7, Product::setProductLevel7);
//        productBinder.addValueChangeListener(event -> {
//            if ((selectedProduct != null) && (event.getValue() != null)) {
//                try {
//                    productBinder.writeBean(selectedProduct);
//                } catch (ValidationException e) {
//                    throw new RuntimeException(e);
//                }
//                grid.getDataProvider().refreshItem(selectedProduct);
//            }
//            else{
//                if(!event.isFromClient()){
//                    clearForm();
//                    cbProductLevel1.setValue(cbProductLevel1.getEmptyValue());
//                    cbProductLevel2.setValue(cbProductLevel2.getEmptyValue());
//                    cbProductLevel3.setValue(cbProductLevel3.getEmptyValue());
//                    cbProductLevel4.setValue(cbProductLevel4.getEmptyValue());
//                    cbProductLevel5.setValue(cbProductLevel5.getEmptyValue());
//                    cbProductLevel6.setValue(cbProductLevel6.getEmptyValue());
//                    cbProductLevel7.setValue(cbProductLevel7.getEmptyValue());
//                }
//            }
//        });
    }

    private void addDataToGrid() {
        Optional<List<Product>>optproducts = productService.getAllProducts();
        if (!optproducts.isEmpty()) {
            addItemsToGrid(optproducts.get());
        }
        else{
            Notification.show("No products found");
            List<Product>products = new ArrayList<>();
            Product product = new Product();
            product.setId(LocalDateTime.now().toString());
            product.setProductCode("sampleCode");
            product.setInternalName("interne omschrijving");
            product.setLinked(false);
            ProductLevel1 productLevel1 = new ProductLevel1();
            productLevel1.setName("N/A");
            product.setProductLevel1(productLevel1);
            products.add(product);
            productService.save(product);
            productsForGrid = products;
            addItemsToGrid(productsForGrid);
        }
    }

    private void setUpGrid() {
        grid.addClassName("no-large-cells");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        Grid.Column<Product> sortColumnPosNr = grid.addColumn(item -> {
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
        Grid.Column<Product> camColumn = grid.addComponentColumn(product -> {
            if ((product.getImageList() != null) && (!product.getImageList().isEmpty())) {
                Icon imageButton = new Icon(VaadinIcon.CAMERA);
                imageButton.addClickListener(event -> {
                    showImageSubVieuw.setUser(UserFunction.TECHNICIAN);
                    showImageSubVieuw.setSelectedWorkOrder(product.getImageList());
                    showImageSubVieuw.setTitle(product.getProductCode() + " " + product.getInternalName());
                    showImageDialog.open();
                });
                return imageButton;
            } else {
                return new Span("");
            }
        }).setFlexGrow(0).setHeader(new Icon(VaadinIcon.CAMERA)).setTextAlign(ColumnTextAlign.CENTER);
        camColumn.addClassName("center-header");
        camColumn.setWidth("70px");

        Grid.Column<Product> OSGrid = grid.addComponentColumn(product -> {
            if ((product.getSetElement() != null) && (product.getSetElement() == true)) {
                Button elementButton = new Button("O");
                elementButton.getStyle().set("color", "black");
                elementButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                elementButton.addClickListener(event -> {
                    selectedSetList.clear();
                    Optional<List<Product>> allSets = productService.getAllSetsContaining(product);
                    if (!allSets.isEmpty()) {
                        selectedSetList.addAll(allSets.get());
                        setViewSimple.setSelectedProduct(product);
                        setViewSimpleDialog.open();
                    }
                });
                return elementButton;
            }
            if ((product.getSet() != null) && (product.getSet() == true)) {
                Button setButton = new Button("S");
                setButton.getStyle().set("color", "black");
                setButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                setButton.addClickListener(event -> {
                    setViewSimple.setSelectedProduct(product);
                    setViewSimpleDialog.open();
                });
                return setButton;
            } else {
                //Check if there are Copies
                if((product.getProductCode() != null) && (!product.getProductCode().isEmpty())){
                    Optional<List<Product>> byProductCodeEqualCaseInsensitive = productService.findByProductCodeEqualCaseInsensitive(product.getProductCode());
                    if (byProductCodeEqualCaseInsensitive.isPresent()) {
                        if(byProductCodeEqualCaseInsensitive.get() != null){
                            long amountArticlesInSet = byProductCodeEqualCaseInsensitive.get().stream().filter(item -> (item.getSetElement() != null) &&(item.getSetElement() == true)).count();
                            if(amountArticlesInSet > 0){
                                Button setButton = new Button("C");
                                setButton.getStyle().set("color", "black");
                                setButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                                setButton.addClickListener(event -> {
                                    selectedProduct = product;
                                    setViewSimple.setSelectedProduct(product);
                                    setViewSimpleDialog.open();
                                });
                                return setButton;
                            }
                            else{
                                return new Span("");
                            }
                        }
                    }
                }
                return new Span("");
            }
        }).setFlexGrow(0).setHeader(new Span("S/O/C")).setTextAlign(ColumnTextAlign.CENTER);
        OSGrid.addClassName("center-header");
        OSGrid.getElement().getThemeList().add("no-min-width");
        OSGrid.setWidth("70px");

        Grid.Column<Product> pdfColumn = grid.addComponentColumn(product -> {
            if ((product.getPdfList() != null) && (!product.getPdfList().isEmpty())) {
                Icon pdfButton = new Icon(VaadinIcon.FILE_FONT);
                pdfButton.addClickListener(event -> {
                    showPdfSubVieuw.setUser(UserFunction.TECHNICIAN);
                    showPdfSubVieuw.setSelectedWorkOrder(product.getPdfList());
                    showPdfSubVieuw.setTitle(product.getProductCode() + " " + product.getInternalName());
                    pdfDialog.open();
                });
                return pdfButton;
            } else return new Span("");
        }).setFlexGrow(0).setHeader(new Icon(VaadinIcon.FILE_FONT)).setTextAlign(ColumnTextAlign.CENTER);
        pdfColumn.addClassName("center-header");
        pdfColumn.getElement().getThemeList().add("no-min-width");
        pdfColumn.setWidth("70px");

        Grid.Column<Product> linkColumn = grid.addComponentColumn(product -> {
            if ((product.getLinkDocumentList() != null) && (!product.getLinkDocumentList().isEmpty()) && (product.getLinkDocumentList().stream().anyMatch(item -> item.getLink().length() > 0))) {
                Icon linkButton = new Icon(VaadinIcon.LINK);
                linkButton.addClickListener(event -> {
                    showLinkSubVieuw.setSelectedWorkOrder(product.getLinkDocumentList());
                    showLinkSubVieuw.setTitle(product.getProductCode() + " " + product.getInternalName());
                    linkDialog.open();
                });
                return linkButton;
            } else return new Span("");
        }).setFlexGrow(0).setHeader(new Icon(VaadinIcon.LINK)).setTextAlign(ColumnTextAlign.CENTER);
        linkColumn.addClassName("center-header");
        linkColumn.getElement().getThemeList().add("no-min-width");
        linkColumn.setWidth("70px");

        codeColumn = grid.addColumn("productCode").setHeader("Code").setResizable(true).setWidth("190px").setFlexGrow(0);
        posColumn = grid.addColumn(o -> o.getPositionNumber())
                .setComparator((o1, o2) -> {
                    return compareOnderdeel(o1.getPositionNumber(), o2.getPositionNumber()); // ascending of descending handled by Vaadin
                })
                .setHeader("Pos")
                .setResizable(true)
                .setWidth("75px")
                .setFlexGrow(0);
        internalNameColumn = grid.addColumn("internalName")
                .setSortable(true)
                .setComparator((o1, o2) -> {
                    boolean o1HasPositionNumber = o1.getPositionNumber() != null && !o1.getPositionNumber().isBlank();
                    boolean o2HasPositionNumber = o2.getPositionNumber() != null && !o2.getPositionNumber().isBlank();

                    // 1. Alles met positionNumber bovenaan
                    if (o1HasPositionNumber && !o2HasPositionNumber) return -1;
                    if (!o1HasPositionNumber && o2HasPositionNumber) return 1;

                    // 2. Binnen dezelfde groep sorteren op het juiste veld
                    String value1 = o1HasPositionNumber ? o1.getPositionNumber() : o1.getInternalName();
                    String value2 = o2HasPositionNumber ? o2.getPositionNumber() : o2.getInternalName();

                    return compareOnderdeel(value1, value2);
                })
                .setHeader("Naam")
                .setResizable(true)
                .setFlexGrow(5);

        grid.sort(List.of(new GridSortOrder<>(internalNameColumn, SortDirection.ASCENDING)));

        commentColumn = grid.addColumn("comment").setHeader("Commentaar").setFlexGrow(1).setResizable(true);

        unitColumn = grid.addColumn(item -> {
            if(item.getUnit() != null){
                return item.getUnit();
            }
            else{
                return "";
            }
        }).setHeader("E/H.").setResizable(true).setWidth("80px").setFlexGrow(0).setFrozenToEnd(true);

        grid.setPartNameGenerator(product -> {
            return "industry";
        });

        //when a row is selected or deselected, populate form
        grid.addItemClickListener(event -> {

            //if selectionColumn is aangeklikt -> don't trigger event!
            if(event.getColumn() == null){
                return;
            }

           editor.cancel();
           editor.closeEditor();

           selectedProduct = event.getItem();
           grid.deselectAll();
           grid.select(selectedProduct);
           var value = event.getItem();

            if (value != null) {
                updating.set(true);
                if (value.getProductLevel1() != null){
                    if((cbProductLevel1.getValue() == null)){
                        cbProductLevel1.clear();
                        cbProductLevel1.setEnabled(true);
                        cbProductLevel1.setPlaceholder(value.getProductLevel1().getName());
                    }
                    else{
                        cbProductLevel1.setPlaceholder(value.getProductLevel1().getName());
                    }
                }
                else{
                    cbProductLevel1.clear();
                    cbProductLevel1.setEnabled(false);
                    cbProductLevel1.setPlaceholder("");
                }

                if (value.getProductLevel2() != null){
                    if(cbProductLevel2.getValue() == null){
                        cbProductLevel2.clear();
                        cbProductLevel2.setEnabled(true);
                        cbProductLevel2.setPlaceholder(value.getProductLevel2().getName());
                    }
                    else{
                        cbProductLevel2.setPlaceholder(value.getProductLevel2().getName());
                    }
                }
                else{
                    cbProductLevel2.clear();
                    cbProductLevel2.setEnabled(false);
                    cbProductLevel2.setPlaceholder("");
                }

                if (value.getProductLevel3() != null){
                    if(cbProductLevel3.getValue() == null){
                        cbProductLevel3.clear();
                        cbProductLevel3.setEnabled(true);
                        cbProductLevel3.setPlaceholder(value.getProductLevel3().getName());
                    }
                    else{
                        cbProductLevel3.setPlaceholder(value.getProductLevel3().getName());
                    }
                }
                else{
                    cbProductLevel3.clear();
                    cbProductLevel3.setEnabled(false);
                    cbProductLevel3.setPlaceholder("");
                }

                if (value.getProductLevel4() != null){
                    if((cbProductLevel4.getValue() == null)){
                        cbProductLevel4.clear();
                        cbProductLevel4.setEnabled(true);
                        cbProductLevel4.setPlaceholder(value.getProductLevel4().getName());
                    }
                    else{
                        cbProductLevel4.setPlaceholder(value.getProductLevel4().getName());
                    }
                }
                else{
                    cbProductLevel4.clear();
                    cbProductLevel4.setEnabled(false);
                    cbProductLevel4.setPlaceholder("");
                }

                if (value.getProductLevel5() != null){
                    if((cbProductLevel5.getValue() == null)){
                        cbProductLevel5.clear();
                        cbProductLevel5.setEnabled(true);
                        cbProductLevel5.setPlaceholder(value.getProductLevel5().getName());
                    }
                    else{
                        cbProductLevel5.setPlaceholder(value.getProductLevel5().getName());
                    }
                }
                else{
                    cbProductLevel5.clear();
                    cbProductLevel5.setEnabled(false);
                    cbProductLevel5.setPlaceholder("");
                }
                updating.set(false);
            }
        });

        grid.addSelectionListener(event -> {
        });

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
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


    private List<PurchasePrice> getNewPurchasePriceList() {
        List<PurchasePrice> purchasePriceList = new ArrayList<>();
        PurchasePrice purchasePrice = new PurchasePrice();
        purchasePrice.setPurchaseDate(LocalDate.now());
        purchasePriceList.add(purchasePrice);
        return purchasePriceList;
    }

    private void setUpOrderDialog() {
        orderDialog = new Dialog();
        orderDialog.setWidth("50%");
        orderDialog.setHeight("50%");

        orderDialog.add(orderSubView);

        Button cancelButton = new Button("Sluiten", e -> {
            orderDialog.close();
        });

        orderDialog.getFooter().add(cancelButton);

        orderDialog.addOpenedChangeListener(event -> {
            if (!event.isOpened()) {
                grid.deselectAll();
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }

    private Div createEditorLayout() {
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
        tfGeneralFilter = new TextField("");
        tfGeneralFilter.setPlaceholder("General filter");
        tfFolderFilter = new TextField("");
        tfFolderFilter.setPlaceholder("Folder filter");
        tfProductCode = new TextField("Artikelcode");
        tfPositionNumber = new TextField("Positienummer");
        tfInternalName = new TextField("Interne omschrijving");
        tfPurchasePrice = new TextField("Aankoopprijs");
        tfSellMargin = new TextField("Verkoopmarge Agro");
        tfSellPrice = new TextField("Verkoopprijs Agro");
        tfSellIndustryMargin = new TextField("Verkoopmarge Industrie");
        tfSellIndustryPrice = new TextField("Verkoopprijs Industrie");
        tfComment = new TextField("Commentaar");
        tfMoQ = new TextField("MoQ");
        tfUnit = new TextField("Eenheid");
        cbProductLevel1 = new ComboBox("");
        cbProductLevel1.setWidth("100%");
        cbProductLevel2 = new ComboBox("");
        cbProductLevel2.setWidth("100%");
        cbProductLevel3 = new ComboBox("");
        cbProductLevel3.setWidth("100%");
        cbProductLevel4 = new ComboBox("");
        cbProductLevel4.setWidth("100%");
        cbProductLevel5 = new ComboBox("");
        cbProductLevel5.setWidth("100%");
        cbProductLevel6 = new ComboBox("");
        cbProductLevel6.setWidth("100%");
        cbProductLevel7 = new ComboBox("");
        cbProductLevel7.setWidth("100%");
        hLayout1.add(getJumpToFolderIcon(),
                setUpHorizontalLayoutFor(cbProductLevel1,E_Product_Level.PRODUCTLEVEL1),
                setUpHorizontalLayoutFor(cbProductLevel2,E_Product_Level.PRODUCTLEVEL2),
                        setUpHorizontalLayoutFor(cbProductLevel3,E_Product_Level.PRODUCTLEVEL3),
                                setUpHorizontalLayoutFor(cbProductLevel4,E_Product_Level.PRODUCTLEVEL4),
                                        setUpHorizontalLayoutFor(cbProductLevel5,E_Product_Level.PRODUCTLEVEL5)
                //setUpHorizontalLayoutFor(cbProductLevel6,bAddProductLevel6,E_Product_Level.PRODUCTLEVEL6),
                    //setUpHorizontalLayoutFor(cbProductLevel7,bAddProductLevel7,E_Product_Level.PRODUCTLEVEL7),
                );
        hLayout2.add(tfGeneralFilter,tfFolderFilter, createButtonLayout());

        editorDiv.add(hLayout1);
        editorDiv.add(hLayout2);

        return editorLayoutDiv;
    }

    private HorizontalLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        buttonLayout.setClassName("button-layout");
        buttonLayout.add(getActionMenu());
        return buttonLayout;
    }

    private Component getJumpToFolderIcon() {
        goToFolderButton = new Button(VaadinIcon.ARROW_CIRCLE_UP.create());
        goToFolderButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        goToFolderButton.addClickListener(event -> {

            String placeholder1 = cbProductLevel1.getPlaceholder();
            String placeholder2 = cbProductLevel2.getPlaceholder();
            String placeholder3 = cbProductLevel3.getPlaceholder();
            String placeholder4 = cbProductLevel4.getPlaceholder();
            String placeholder5 = cbProductLevel5.getPlaceholder();

            updating.set(true);
            cbProductLevel1.setItems(productLevel1Service.getAllProductLevel1().get());
            updating.set(false);
            if(placeholder1.length() > 0)
                cbProductLevel1.setValue(productLevel1Service.getProductLevel1ByName(placeholder1).get());
            if(placeholder2.length() > 0)
                cbProductLevel2.setValue(productLevel2Service.getProductLevel2ByName(placeholder2).get());
            if(placeholder3.length() > 0)
                cbProductLevel3.setValue(productLevel3Service.getProductLevel3ByName(placeholder3).get());
            if(placeholder4.length() > 0)
                cbProductLevel4.setValue(productLevel4Service.getProductLevel4ByName(placeholder4).get());
            if(placeholder5.length() > 0)
                cbProductLevel5.setValue(productLevel5Service.getProductLevel5ByName(placeholder5).get());
            Notification.show("Ga naar map");
        });
        return goToFolderButton;
    }

    private HorizontalLayout setUpHorizontalLayoutFor(ComboBox comboBox, E_Product_Level productLevel) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        horizontalLayout.setAlignItems(Alignment.BASELINE);
        horizontalLayout.add(comboBox);
        return horizontalLayout;
    }

    private MenuBar getActionMenu() {
        actionBar = new MenuBar();
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie");
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Bestel artikel", e -> orderProduct());
        return actionBar;
    }

    private void orderProduct(){
        Set<Product> selectedItems = grid.getSelectedItems();
        if(selectedItems.size() == 1){
            Product productToOrder = selectedItems.stream().findFirst().get();
            orderSubView.setSelectedProdcut(productToOrder);
            orderSubView.setCloseAction(() -> orderDialog.close());
            orderDialog.open();
        }
        else{
            Notification.show("Gelieve 1 artikel te selectern om te bestellen");
        }
    }

    private Div createSplitLayout() {
        Div wrapper = new Div();
        wrapper.setSizeFull();
        wrapper.setClassName("grid-wrapper");;
        wrapper.add(grid);
        return wrapper;
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Product value) {
        selectedProduct = value;
        try{
            productBinder.readBean(selectedProduct);
        }
        catch (Exception e){
            Notification.show(e.getMessage());
        }
    }

    private void tryToCalculateSellPriceAgro(Product product) {
        try{
            Optional<Double>optDoublePurchasePrice = Optional.of(df.parse(tfPurchasePrice.getValue()).doubleValue());
            Optional<Double>optDoubleSellMargin = Optional.of(df.parse(tfSellMargin.getValue()).doubleValue());

            if(optDoublePurchasePrice.isPresent()) {
                if(optDoubleSellMargin.isPresent()) {
                    product.setSellPrice(optDoublePurchasePrice.get()  *(optDoubleSellMargin.get()));
                    tfSellPrice.setValue(df.format(product.getSellPrice()));
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

    private void tryToCalculateSellPriceIndustry(Product product) {
        try{
            Optional<Double>optDoublePurchasePrice = Optional.of(df.parse(tfPurchasePrice.getValue()).doubleValue());
            Optional<Double>optDoubleSellMargin = Optional.of(df.parse(tfSellIndustryMargin.getValue()).doubleValue());

            if(optDoublePurchasePrice.isPresent()) {
                if(optDoubleSellMargin.isPresent()) {
                    product.setSellPriceIndustry(optDoublePurchasePrice.get()  *(optDoubleSellMargin.get()));
                    tfSellIndustryPrice.setValue(df.format(product.getSellPriceIndustry()));
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

    private void addItemsToGrid(List<Product> productList) {
        productListToShowInGrid.clear();
        productListToShowInGrid.addAll(productList);
        grid.setItems(productListToShowInGrid);
    }

    private void refreshItemsInGrid(){
        temporaryProductList.clear();
        temporaryProductList.addAll(productListToShowInGrid);

        productListToShowInGrid.clear();
        temporaryProductList.stream().forEach(item -> {
            productService.get(item.getId()).ifPresent(product -> {
                if(productServices.haveSameLevels(product, item)) {
                    productListToShowInGrid.add(product);
                }
            });
        });
        grid.setItems(productListToShowInGrid);
    }

    public void goToRightFolderAndSelectProduct(Product product) {
        updating.set(true);
        cbProductLevel1.setItems(productLevel1Service.getAllProductLevel1().get());
        updating.set(false);
        if((product.getProductLevel1() != null)&&(product.getProductLevel1().getName().length() > 0))
            cbProductLevel1.setValue(productLevel1Service.getProductLevel1ByName(product.getProductLevel1().getName()).get());
        if((product.getProductLevel2() != null)&&(product.getProductLevel2().getName().length() > 0))
            cbProductLevel2.setValue(productLevel2Service.getProductLevel2ByName(product.getProductLevel2().getName()).get());
        if((product.getProductLevel3() != null)&&(product.getProductLevel3().getName().length() > 0))
            cbProductLevel3.setValue(productLevel3Service.getProductLevel3ByName(product.getProductLevel3().getName()).get());
        if((product.getProductLevel4() != null)&&(product.getProductLevel4().getName().length() > 0))
            cbProductLevel4.setValue(productLevel4Service.getProductLevel4ByName(product.getProductLevel4().getName()).get());
        if((product.getProductLevel5() != null)&&(product.getProductLevel5().getName().length() > 0))
            cbProductLevel5.setValue(productLevel5Service.getProductLevel5ByName(product.getProductLevel5().getName()).get());
        //select product
        Optional<Product> itemToSelect = productsForGrid.stream().filter(item -> item.getProductCode().matches(product.getProductCode())).findFirst();
        if(itemToSelect.isPresent()) {
            grid.select(itemToSelect.get());
        }
        else{
            Notification.show("Geen match gevonden");
        }
    }

//    private Notification createReportChangePurchasePrice() {
//        changePurchasePriceNotification = new Notification();
//        changePurchasePriceNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);
//
//        Icon icon = VaadinIcon.WARNING.create();
//        Button retryBtn = new Button("Annuleer",
//                clickEvent -> changePurchasePriceNotification.close());
//        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");
//
//        var layout = new HorizontalLayout(icon,
//                new Text("Ben je zeker dat je de aankoopprijs van alle artikelen met deze code wilt wijzigen?"), retryBtn,
//                createRemoveProductBtn(changePurchasePriceNotification));
//        layout.setAlignItems(FlexComponent.Alignment.CENTER);
//
//        changePurchasePriceNotification.add(layout);
//
//        return changePurchasePriceNotification;
//    }
//
//    public Button createRemoveProductBtn(Notification notification) {
//        Button closeBtn = new Button(VaadinIcon.PENCIL.create(),
//                clickEvent -> {
//                    if((selectedProduct.getProductCode() != null) && (selectedProduct.getProductCode().length() > 1)){
//                        Optional<List<Product>> byProductCodeEqualCaseInsensitive = productService.findByProductCodeEqualCaseInsensitive(selectedProduct.getProductCode());
//                        if((byProductCodeEqualCaseInsensitive.isPresent() && (byProductCodeEqualCaseInsensitive.get().size() > 0))) {
//                            for(Product product : byProductCodeEqualCaseInsensitive.get()) {
//                                product.setPurchasePrice(selectedProduct.getPurchasePrice());
//                                tryToCalculateSellPrice(product);
//                                productService.save(product);
//                                try {
//                                    productBinder.writeBean(selectedProduct);
//                                } catch (ValidationException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                productService.save(selectedProduct);
//                            }
//                        }
//                        Notification.show("Artikel : " + byProductCodeEqualCaseInsensitive.get().size() + " dezelfde artikelen gevonden en aangepast in de Database!");
//                    }
//                });
//        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
//
//        return closeBtn;
//    }
}
