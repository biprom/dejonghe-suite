package com.adverto.dejonghe.tabletdemo.tabletdemo.views.subViews;

import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.tabletdemo.tabletdemo.views.articles.ImportArticleViewNieuw;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Component
@Scope("prototype")
public class SetViewSimple extends Div {
    ProductService productService;

    Product selectedProduct;
    Dialog parentDialog;
    ImportArticleViewNieuw importArticleViewNieuw;
    Grid<Product>selectedProductGrid;
    Grid<Product>productGrid;
    GridListDataView<Product> productGridListDataView;

    VerticalLayout verticalLayout;
    H3 title2;

    public SetViewSimple(ProductService productService) {

        this.productService = productService;
        this.setHeightFull();
        setUpGrid();
        setUpSelectedProductGrid();

        H3 title1 = new H3("Geselecteerd artikel");
        title1.getStyle().set("text-align", "center");
        title1.setWidthFull();

        title2 = new H3("Gevonden in volgende sets");
        title2.getStyle().set("text-align", "center");
        title2.setWidthFull();

        verticalLayout = new VerticalLayout();
        verticalLayout.setHeightFull();
        verticalLayout.add(title1);
        verticalLayout.add(selectedProductGrid);
        verticalLayout.add(title2);
        verticalLayout.add(productGrid);
        this.add(verticalLayout);
    }

    private void setUpSelectedProductGrid() {
        selectedProductGrid = new Grid<>();
        selectedProductGrid.setAllRowsVisible(true);
        selectedProductGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        selectedProductGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        selectedProductGrid.addComponentColumn(item -> {
            if((item.getSet() != null) && (item.getSet() == true)){
                Button setButton = new Button("S");
                setButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return setButton;
            } else if ((item.getSetElement() != null) && (item.getSetElement() == true)) {
                Button setButton = new Button("O");
                setButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return setButton;
            }
            else {
                Button setButton = new Button("C");
                setButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return setButton;
            }

        }).setFlexGrow(1).setHeader(new Span("S/O/C")).setTextAlign(ColumnTextAlign.CENTER);
        selectedProductGrid.addColumn(item -> item.getProductCode()).setHeader("Code").setResizable(true).setWidth("190px").setFlexGrow(0);
        selectedProductGrid.addColumn(item -> item.getInternalName()).setSortable(true)
                .setHeader("Naam").setResizable(true).setFlexGrow(2);
        selectedProductGrid.addColumn(item -> {
//            String folderName = item.getProductLevel1().getName();
//            if((item.getProductLevel2() != null) && (!item.getProductLevel2().getName().isEmpty())) {
//                folderName = item.getProductLevel2().getName();
//            }
//            if((item.getProductLevel3() != null) && (!item.getProductLevel3().getName().isEmpty())) {
//                folderName = item.getProductLevel3().getName();
//            }
//            if((item.getProductLevel4() != null) && (!item.getProductLevel4().getName().isEmpty())) {
//                folderName = item.getProductLevel4().getName();
//            }
//            if((item.getProductLevel5() != null) && (!item.getProductLevel5().getName().isEmpty())) {
//                folderName =item.getProductLevel5().getName();
//            }
//            return folderName;
            return "  ";
        }).setHeader("").setResizable(true).setFlexGrow(2);
        selectedProductGrid.addColumn(item -> {
            return " ";
        }).setHeader("").setResizable(true).setFlexGrow(1);;
        selectedProductGrid.addComponentColumn(item -> {
            return new Span("   ");
        }).setFlexGrow(2);
    }

    private void setUpGrid() {
        productGrid = new Grid<>();
        productGrid.setHeightFull();
        productGrid.addComponentColumn(item -> {
            if((item.getSet() != null) && (item.getSet() == true)){
                Button setButton = new Button("S");
                setButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return setButton;
            } else if ((item.getSetElement() != null) && (item.getSetElement() == true)) {
                Button setButton = new Button("O");
                setButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return setButton;
            }
            else {
                Button setButton = new Button("C");
                setButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                return setButton;
            }

        }).setFlexGrow(1).setHeader(new Span("S/O/C")).setTextAlign(ColumnTextAlign.CENTER);
        productGrid.addColumn(item -> item.getProductCode()).setHeader("Code").setResizable(true).setWidth("190px").setFlexGrow(0);
        productGrid.addColumn(item -> item.getInternalName()).setSortable(true)
                .setHeader("Naam").setResizable(true).setFlexGrow(2);
        productGrid.addColumn(item -> {
            String folderName = item.getProductLevel1().getName();
            if((item.getProductLevel2() != null) && (!item.getProductLevel2().getName().isEmpty())) {
                folderName = item.getProductLevel2().getName();
            }
            if((item.getProductLevel3() != null) && (!item.getProductLevel3().getName().isEmpty())) {
                folderName = item.getProductLevel3().getName();
            }
            if((item.getProductLevel4() != null) && (!item.getProductLevel4().getName().isEmpty())) {
                folderName = item.getProductLevel4().getName();
            }
            if((item.getProductLevel5() != null) && (!item.getProductLevel5().getName().isEmpty())) {
                folderName =item.getProductLevel5().getName();
            }
            return folderName;
        }).setHeader("Map").setResizable(true).setFlexGrow(2);
        productGrid.addColumn(item -> {
            if((selectedProduct.getSetList() != null) && (selectedProduct.getSetList().size() > 0)){
                Optional<Product> optProduct = selectedProduct.getSetList().stream().filter(element -> element.getProductCode().matches(item.getProductCode())).findFirst();
                if(optProduct.isPresent()) {
                    return optProduct.get().getSelectedAmount();
                }
                else{
                    return "N/A";
                }
            }
            else{
                Double totalAmount = 0.0;
                Optional<List<Product>>setsContainingProdcut = productService.getAllSetsContaining(item);
                if(setsContainingProdcut.isPresent()) {
                    long totalAmountSum =
                            setsContainingProdcut.get().stream()
                                    .mapToLong(element ->
                                            element.getSetList().stream()
                                                    .filter(x -> x.getProductCode().equals(item.getProductCode()))
                                                    .count()
                                    )
                                    .sum();
                    return totalAmountSum;
                }
                else{
                    return "N/A";
                }
            }
        }).setHeader("Aantal in set(s)").setResizable(true).setFlexGrow(1);
        productGrid.addComponentColumn(item -> {
            Button goToButton = new Button(VaadinIcon.ARROW_RIGHT.create());
            goToButton.addClickListener(event -> {
                navigateToSelectedElement(item);
            });
            return goToButton;
        }).setHeader("Ga naar").setResizable(true).setFlexGrow(2);
        productGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        productGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void selectItem(Product item) {

        GridListDataView<Product> selectedProductDataView = selectedProductGrid.setItems(item);
        if((item.getSetElement() != null) && (item.getSetElement() == true)){
            title2.setText("Gevonden in volgende sets");
            Optional<List<Product>> setsWithThisElement = productService.findSetsWithThisElement(item);
            if(setsWithThisElement.isPresent()) {
                GridListDataView<Product> productGridListDataView = productGrid.setItems(setsWithThisElement.get());
            }
        }
        else{
            //If selected product is a copy
            title2.setText("Gevonden onderdelen in set");
            if(item.getSetList() != null) {
                GridListDataView<Product> productGridListDataView = productGrid.setItems(item.getSetList());
            }
        }
    }

    private void navigateToSelectedElement(Product product) {
        importArticleViewNieuw.goToRightFolderAndSelectProduct(product);
        parentDialog.close();
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        selectItem(selectedProduct);
    }

    public void setParentDialog(Dialog parentDialog) {
        this.parentDialog = parentDialog;
    }

    public void setImportArticleViewNieuw(ImportArticleViewNieuw importArticleViewNieuw) {
        this.importArticleViewNieuw = importArticleViewNieuw;
    }
}
