package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.services.SetService;
import com.adverto.dejonghe.server.customEvents.AddProductEventListener;
import com.adverto.dejonghe.server.customEvents.AddRemoveProductEvent;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.dbservices.SupplierService;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.springframework.context.annotation.Scope;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component
@Scope("prototype")
public class SetView extends VerticalLayout implements BeforeEnterObserver {
    SelectProductSubView selectProductSubView;
    ProductService productService;
    AddProductEventListener listener;
    SetService setService;

    Consumer<AddRemoveProductEvent> myAddRemoveConsumer;


    SupplierService supplierService;

    SplitLayout mainSplitLayout;
    SplitLayout secondarySplitLayout;
    Grid<Product> setGrid = new Grid<>();
    Grid<Product> selectedProductGrid = new Grid<>();
    Grid<Product> selectedWorkhoursGrid = new Grid<>();
    Editor<Product> setEditor = setGrid.getEditor();
    Editor<Product> selectedProductEditor = selectedProductGrid.getEditor();
    Binder<Product> setBinder;
    Binder<Product> selectedProductBinder;

    Product selectedSet;
    Product selectedProductToRemove;

    Grid.Column setNameColumn;
    Grid.Column setSellColumn;
    Grid.Column setCommentColumn;

    Grid.Column<Product> purchaseWorkhoursColumn;
    Grid.Column<Product> purchaseProductColumn;
    Grid.Column<Product> sellPriceWorkhoursAColumn;
    Grid.Column<Product> sellPriceWorkhoursIColumn;
    Grid.Column<Product> sellPriceProductAColumn;
    Grid.Column<Product> sellPriceProductIColumn;

    Checkbox cbSet;
    TextField tfCode;
    TextField tfEditName;
    TextField tfEditDescription;
    TextField tfEditPurchasePrice;
    TextField tfEditMargin;
    TextField tfEditPrice;

    Span groupSpan = new Span();

    FooterRow footerTotalMaterialsA;
    FooterRow footerTotalWorkHoursA;

    private boolean sidebarCollapsed;
    Button slideButton;
    Icon leftArrowIcon;
    Icon rightArrowIcon;


    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));

    public SetView(ProductService productService,
                   SelectProductSubView selectProductSubView,
                   AddProductEventListener listener,
                   SupplierService supplierService,
                   SetService setService) {

        this.productService = productService;
        this.selectProductSubView = selectProductSubView;
        this.listener = listener;
        this.supplierService = supplierService;
        this.setService = setService;

        selectProductSubView.setUserFunctionAndDocumentDate(UserFunction.MAKE_SETS, LocalDate.now());
        //setUpConfirmDialog();
        setUpSlideButton();
        setUpSplitLayouts();
        groupSpan.getStyle()
                .set("color", "black")
                .set("font-size", "1.5em")
                .set("font-weight", "bold");
        mainSplitLayout.addToPrimary(selectProductSubView.getLayout());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.setWidth("100%");
        horizontalLayout.addToMiddle(groupSpan);
        VerticalLayout verticalLayout =  new VerticalLayout(slideButton,horizontalLayout,getProductDetail());
        verticalLayout.setWidth("100%");
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setMargin(false);
        secondarySplitLayout.addToPrimary(verticalLayout);
        secondarySplitLayout.addToSecondary(getSelectedProductGridForSets(), getSelectedWorkhoursGridForSets());
        mainSplitLayout.addToSecondary(secondarySplitLayout);
        this.setHeightFull();
        add(mainSplitLayout);
        setUpBinder();
        setUpNumberFormat();
        updateSidebar();
    }

    private void setUpSlideButton() {
        slideButton = new Button();
        leftArrowIcon = VaadinIcon.ARROW_LEFT.create();
        rightArrowIcon = VaadinIcon.ARROW_RIGHT.create();

        sidebarCollapsed = true;

        slideButton.addClickListener(event -> {
            sidebarCollapsed = !sidebarCollapsed;
            updateSidebar();
        });
        slideButton.setAriaLabel("Expand/collapse sidebar");
        slideButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        slideButton.getStyle().set("float", "right");

    }

    private void updateSidebar() {
        slideButton.setIcon(sidebarCollapsed ? rightArrowIcon : leftArrowIcon);
        mainSplitLayout.setSplitterPosition(sidebarCollapsed ? 0 : 50);
    }

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    private Grid<Product> getSelectedWorkhoursGridForSets() {

        selectedWorkhoursGrid = new Grid<>();
        selectedWorkhoursGrid.setWidth("100%");
        selectedWorkhoursGrid.setAllRowsVisible(true);
        selectedWorkhoursGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
//
        Grid.Column<Product> codeColumn = selectedWorkhoursGrid.addComponentColumn(item -> {
            if (item.getProductCode() != null) {
                if (item.isBoldMode() == false) {
                    return new Span(item.getProductCode());
                } else {
                    Span span = new Span(item.getProductCode());
                    span.getStyle().set("font-weight", "bold");
                    span.addClassName("boxed-text");
                    return span;
                }
            } else {
                return new Span("");
            }
        }).setHeader("Code").setAutoWidth(true).setFlexGrow(1).setResizable(true);

        Grid.Column<Product> productInternalNameColumn = selectedWorkhoursGrid.addComponentColumn(item -> {
                    TextField tfInternalName = new TextField();
                    tfInternalName.addThemeVariants(TextFieldVariant.LUMO_SMALL);
                    tfInternalName.setWidthFull();
                    if (item.getInternalName() != null) {
                        tfInternalName.setValue(item.getInternalName());
                    } else {
                        item.setInternalName("");
                        tfInternalName.setValue("");
                    }
                    tfInternalName.addValueChangeListener(value -> {
                        try {
                            item.setInternalName(value.getValue());
                        } catch (NumberFormatException e) {
                            Notification.show("Kon de naam niet bewaren");
                        }
                    });
                    return tfInternalName;
                }).setHeader("Naam").setSortable(true)
                .setComparator((p1, p2) -> {
                    // 1️⃣ Boolean eerst (true bovenaan)
                    int boolCompare = Boolean.compare(
                            p1.getBWorkHour(), // true eerst → p2 vs p1
                            p2.getBWorkHour()
                    );

                    if (boolCompare != 0) {
                        return boolCompare;
                    }

                    // 2️⃣ Daarna alfabetisch sorteren
                    return compareOnderdeel(p1.getInternalName(), p2.getInternalName());
                }).setAutoWidth(true).setFlexGrow(6).setResizable(true);

        selectedWorkhoursGrid.addComponentColumn(item -> {
            TextField tfAmount = new TextField();
            tfAmount.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfAmount.setWidthFull();
            if (item.getSelectedAmount() != null) {
                tfAmount.setValue(item.getSelectedAmount().toString());
            } else {
                item.setSelectedAmount(0.0);
                tfAmount.setValue("0.0");
            }
            tfAmount.addValueChangeListener(value -> {
                try {
                    item.setSelectedAmount(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedAmount(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfAmount;
        }).setHeader("Aantal").setWidth("100px").setAutoWidth(false).setFlexGrow(1);

        purchaseWorkhoursColumn = selectedWorkhoursGrid.addColumn(item -> df.format(item.getPurchasePrice())).setHeader("A/P").setAutoWidth(true).setResizable(true);
        Grid.Column<Product> productMarginColumn = selectedWorkhoursGrid.addComponentColumn(item -> {
            TextField tfSellMargin = new TextField();
            tfSellMargin.setEnabled(false);
            tfSellMargin.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellMargin.setWidthFull();
            tfSellMargin.setEnabled(false);
            if (item.getSellMargin() != null) {
                tfSellMargin.setValue(df.format(item.getSellMargin()).toString());
            } else {
                item.setSellMargin(0.0);
                tfSellMargin.setValue("0.0");
            }
            tfSellMargin.addValueChangeListener(value -> {
                try {
                    item.setSellMargin(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedMargin(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellMargin;
        }).setHeader("Marge A").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        sellPriceWorkhoursAColumn = selectedWorkhoursGrid.addComponentColumn(item -> {
            TextField tfSellPriceA = new TextField();
            tfSellPriceA.setEnabled(false);
            tfSellPriceA.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellPriceA.setWidthFull();
            if (item.getSellPrice() != null) {
                tfSellPriceA.setValue(df.format(item.getSellPrice()).toString());
            } else {
                item.setSellPrice(0.0);
                tfSellPriceA.setValue("0.0");
            }
            tfSellPriceA.addValueChangeListener(value -> {
                try {
                    item.setSellPrice(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedSellPrice(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellPriceA;
        }).setHeader("V/P A").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        selectedWorkhoursGrid.addComponentColumn(item -> {
            TextField tfSellMarginIndustry = new TextField();
            tfSellMarginIndustry.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellMarginIndustry.setWidthFull();
            tfSellMarginIndustry.setEnabled(false);
            if (item.getSellMarginIndustry() != null) {
                tfSellMarginIndustry.setValue(df.format(item.getSellMarginIndustry()).toString());
            } else {
                item.setSellMarginIndustry(0.0);
                tfSellMarginIndustry.setValue("0.0");
            }
            tfSellMarginIndustry.addValueChangeListener(value -> {
                try {
                    item.setSellMarginIndustry(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedMarginIndustry(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellMarginIndustry;
        }).setHeader("Marge I").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        sellPriceWorkhoursIColumn = selectedWorkhoursGrid.addComponentColumn(item -> {
            TextField tfSellPriceI = new TextField();
            tfSellPriceI.setEnabled(false);
            tfSellPriceI.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellPriceI.setWidthFull();
            if (item.getSellPriceIndustry() != null) {
                tfSellPriceI.setValue(df.format(item.getSellPriceIndustry()).toString());
            } else {
                item.setSellPriceIndustry(0.0);
                tfSellPriceI.setValue("0.0");
            }
            tfSellPriceI.addValueChangeListener(value -> {
                try {
                    item.setSellPriceIndustry(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedSellPriceIndustry(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellPriceI;
        }).setHeader("V/P I").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        selectedWorkhoursGrid.sort(Collections.singletonList(
                new GridSortOrder<>(productInternalNameColumn, SortDirection.ASCENDING)
        ));

        selectedWorkhoursGrid.addColumn(Product::getUnit).setHeader("EH").setAutoWidth(true).setFlexGrow(1).setFrozenToEnd(true);

        selectedWorkhoursGrid.addComponentColumn(item -> {
            Button closeButton = new Button(new Icon(VaadinIcon.TRASH));
            closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            closeButton.addClickListener(event -> {
                selectedProductToRemove = item;
                selectedSet.getSetList().remove(item);
                selectedWorkhoursGrid.setItems(selectedSet.getSetList().stream().filter(product -> product.getBWorkHour()).collect(Collectors.toList()));
                updateFooter.run();
                Optional<List<Product>> allSetsContaining = productService.getAllSetsContaining(item);
                if (allSetsContaining.isPresent()) {
                    if(allSetsContaining.get().size() == 0){
                        Optional<Product> sameProduct = productService.get(item.getId());
                        if(sameProduct.isPresent()){
                            sameProduct.get().setSetElement(false);
                            productService.save(sameProduct.get());
                        }
                    }
                }
            });
            return closeButton;
        }).setAutoWidth(false).setAutoWidth(false).setFlexGrow(1).setFrozenToEnd(true);

        selectedProductBinder = new Binder<>(Product.class);
        selectedProductEditor = selectedWorkhoursGrid.getEditor();
        selectedProductEditor.setBinder(selectedProductBinder);


        selectedProductBinder.addValueChangeListener(event -> {
            Product productToChange = selectedProductEditor.getItem();
            selectedWorkhoursGrid.getDataProvider().refreshItem(productToChange);
        });

        selectedWorkhoursGrid.addItemClickListener(e -> {
            selectedProductEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });


        footerTotalWorkHoursA = selectedWorkhoursGrid.appendFooterRow();

        Span spanTotalWorkhours = new Span("Totaal werkuren");
        spanTotalWorkhours.getStyle().set("font-weight", "bold").set("font-size", "1.2em");
        footerTotalWorkHoursA.getCell(codeColumn).setComponent(spanTotalWorkhours);
        footerTotalWorkHoursA.getCell(sellPriceWorkhoursAColumn).setText("" );
        footerTotalWorkHoursA.getCell(sellPriceWorkhoursIColumn).setText("");

        return selectedWorkhoursGrid;
    }

    private Grid<Product> getSelectedProductGridForSets() {

        selectedProductGrid = new Grid<>();
        selectedProductGrid.setWidth("100%");
        selectedProductGrid.setAllRowsVisible(true);
        selectedProductGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        Grid.Column<Product> codeColumn = selectedProductGrid.addComponentColumn(item -> {
            if (item.getProductCode() != null) {
                if (item.isBoldMode() == false) {
                    return new Span(item.getProductCode());
                } else {
                    Span span = new Span(item.getProductCode());
                    span.getStyle().set("font-weight", "bold");
                    span.addClassName("boxed-text");
                    return span;
                }
            } else {
                return new Span("");
            }
        }).setHeader("Code").setAutoWidth(true).setFlexGrow(1).setResizable(true);

        Grid.Column<Product> productInternalNameColumn = selectedProductGrid.addComponentColumn(item -> {
                    TextField tfInternalName = new TextField();
                    tfInternalName.addThemeVariants(TextFieldVariant.LUMO_SMALL);
                    tfInternalName.setWidthFull();
                    if (item.getInternalName() != null) {
                        tfInternalName.setValue(item.getInternalName());
                    } else {
                        item.setInternalName("");
                        tfInternalName.setValue("");
                    }
                    tfInternalName.addValueChangeListener(value -> {
                        try {
                            item.setInternalName(value.getValue());
                        } catch (NumberFormatException e) {
                            Notification.show("Kon de naam niet bewaren");
                        }
                    });
                    return tfInternalName;
                }).setHeader("Naam").setAutoWidth(true).setFlexGrow(6).setResizable(true).setSortable(true)
                .setComparator((p1, p2) -> {
                    // 1️⃣ Boolean eerst (true bovenaan)
                    int boolCompare = Boolean.compare(
                            p1.getBWorkHour(), // true eerst → p2 vs p1
                            p2.getBWorkHour()
                    );

                    if (boolCompare != 0) {
                        return boolCompare;
                    }

                    // 2️⃣ Daarna alfabetisch sorteren
                    return compareOnderdeel(p1.getInternalName(), p2.getInternalName());
                }).setResizable(true);

        selectedProductGrid.addComponentColumn(item -> {
            TextField tfAmount = new TextField();
            tfAmount.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfAmount.setWidthFull();
            if (item.getSelectedAmount() != null) {
                tfAmount.setValue(item.getSelectedAmount().toString());
            } else {
                item.setSelectedAmount(0.0);
                tfAmount.setValue("0.0");
            }
            tfAmount.addValueChangeListener(value -> {
                try {
                    item.setSelectedAmount(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedAmount(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfAmount;
        }).setHeader("Aantal").setWidth("100px").setAutoWidth(false).setFlexGrow(1);

        purchaseProductColumn = selectedProductGrid.addColumn(item -> df.format(item.getPurchasePrice())).setHeader("A/P").setAutoWidth(true).setResizable(true);
        Grid.Column<Product> productMarginColumn = selectedProductGrid.addComponentColumn(item -> {
            TextField tfSellMargin = new TextField();
            tfSellMargin.setEnabled(false);
            tfSellMargin.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellMargin.setWidthFull();
            if (item.getSellMargin() != null) {
                tfSellMargin.setValue(df.format(item.getSellMargin()).toString());
            } else {
                item.setSellMargin(0.0);
                tfSellMargin.setValue("0.0");
            }
            tfSellMargin.addValueChangeListener(value -> {
                try {
                    item.setSellMargin(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedMargin(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellMargin;
        }).setHeader("Marge A").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        sellPriceProductAColumn = selectedProductGrid.addComponentColumn(item -> {
            TextField tfSellPriceA = new TextField();
            tfSellPriceA.setEnabled(false);
            tfSellPriceA.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellPriceA.setWidthFull();
            if (item.getSellPrice() != null) {
                tfSellPriceA.setValue(df.format(item.getSellPrice()).toString());
            } else {
                item.setSellPrice(0.0);
                tfSellPriceA.setValue("0.0");
            }
            tfSellPriceA.addValueChangeListener(value -> {
                try {
                    item.setSellPrice(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedSellPrice(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellPriceA;
        }).setHeader("V/P A").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        selectedProductGrid.addComponentColumn(item -> {
            TextField tfSellMarginIndustry = new TextField();
            tfSellMarginIndustry.setEnabled(false);
            tfSellMarginIndustry.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellMarginIndustry.setWidthFull();
            tfSellMarginIndustry.setEnabled(false);
            if (item.getSellMarginIndustry() != null) {
                tfSellMarginIndustry.setValue(df.format(item.getSellMarginIndustry()).toString());
            } else {
                item.setSellMarginIndustry(0.0);
                tfSellMarginIndustry.setValue("0.0");
            }
            tfSellMarginIndustry.addValueChangeListener(value -> {
                try {
                    item.setSellMarginIndustry(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedMarginIndustry(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellMarginIndustry;
        }).setHeader("Marge I").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        sellPriceProductIColumn = selectedProductGrid.addComponentColumn(item -> {
            TextField tfSellPriceI = new TextField();
            tfSellPriceI.setEnabled(false);
            tfSellPriceI.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            tfSellPriceI.setWidthFull();
            if (item.getSellPriceIndustry() != null) {
                tfSellPriceI.setValue(df.format(item.getSellPriceIndustry()).toString());
            } else {
                item.setSellPriceIndustry(0.0);
                tfSellPriceI.setValue("0.0");
            }
            tfSellPriceI.addValueChangeListener(value -> {
                try {
                    item.setSellPriceIndustry(Double.parseDouble(value.getValue().toString()));
                    calcNewPriceWithChangedSellPriceIndustry(item);
                    updateFooter.run();
                } catch (NumberFormatException e) {
                    Notification.show("Kon de waarde niet herkennen");
                }
            });
            return tfSellPriceI;
        }).setHeader("V/P I").setAutoWidth(false).setFlexGrow(1).setResizable(true);

        selectedProductGrid.sort(Collections.singletonList(
                new GridSortOrder<>(productInternalNameColumn, SortDirection.ASCENDING)
        ));

        selectedProductGrid.addColumn(Product::getUnit).setHeader("EH").setAutoWidth(true).setFlexGrow(1).setFrozenToEnd(true);

        selectedProductGrid.addComponentColumn(item -> {
            Button closeButton = new Button(new Icon(VaadinIcon.TRASH));
            closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            closeButton.addClickListener(event -> {
                selectedProductToRemove = item;
                selectedSet.getSetList().remove(selectedProductToRemove);
                selectedProductGrid.setItems(selectedSet.getSetList().stream().filter(product -> product.getBWorkHour() == false).collect(Collectors.toList()));
                updateFooter.run();
                Optional<List<Product>> allSetsContaining = productService.getAllSetsContaining(item);
                if (allSetsContaining.isPresent()) {
                    if(allSetsContaining.get().size() == 0){
                        Optional<Product> sameProduct = productService.get(item.getId());
                        if(sameProduct.isPresent()){
                            sameProduct.get().setSetElement(false);
                            productService.save(sameProduct.get());
                        }
                    }
                }
            });
            return closeButton;
        }).setAutoWidth(false).setFlexGrow(1).setFrozenToEnd(true);

        selectedProductBinder = new Binder<>(Product.class);
        selectedProductEditor = selectedProductGrid.getEditor();
        selectedProductEditor.setBinder(selectedProductBinder);


        selectedProductBinder.addValueChangeListener(event -> {
            Product productToChange = selectedProductEditor.getItem();
            selectedProductGrid.getDataProvider().refreshItem(productToChange);
        });

        selectedProductGrid.addItemClickListener(e -> {
            selectedProductEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });

        footerTotalMaterialsA = selectedProductGrid.appendFooterRow();

        Span spanTotalWorkhours = new Span("Totaal materiaal");
        spanTotalWorkhours.getStyle().set("font-weight", "bold").set("font-size", "1.2em");

        footerTotalMaterialsA.getCell(codeColumn).setComponent(spanTotalWorkhours);
        footerTotalMaterialsA.getCell(sellPriceProductAColumn).setText("");
        footerTotalMaterialsA.getCell(sellPriceProductIColumn).setText("");


        return selectedProductGrid;
    }

    private void calcNewPriceWithChangedAmount(Product item) {
        if((item.getSellMargin() != null) && (item.getPurchasePrice() != null)) {
            item.setSellPrice(item.getSelectedAmount() * (item.getPurchasePrice() * item.getSellMargin()));
        }
        else{
            Notification.show("Gelieve de aankoopprijs en marge in te vullen");
        }
        if((item.getSellMarginIndustry() != null) && (item.getPurchasePrice() != null)) {
            item.setSellPriceIndustry(item.getSelectedAmount() * (item.getPurchasePrice() * item.getSellMarginIndustry()));
        }
        else{
            Notification.show("Gelieve de aankoopprijs en marge in te vullen");
        }
        selectedProductGrid.getDataProvider().refreshItem(item);
    }

    private void calcNewPriceWithChangedMargin(Product item) {
        if((item.getSelectedAmount() != null) && (item.getPurchasePrice() != null)) {
            item.setSellPrice(item.getSelectedAmount() * (item.getPurchasePrice() * item.getSellMargin()));
        }
        else{
            Notification.show("Gelieve de aankoopprijs en marge in te vullen");
        }
        if((item.getSellMarginIndustry() != null) && (item.getPurchasePrice() != null)) {
            item.setSellPriceIndustry(item.getSelectedAmount() * (item.getPurchasePrice() * item.getSellMarginIndustry()));
        }
        else{
            Notification.show("Gelieve de aankoopprijs en marge in te vullen");
        }
        selectedProductGrid.getDataProvider().refreshItem(item);
    }

    private void calcNewPriceWithChangedMarginIndustry(Product item) {
        if((item.getSelectedAmount() != null) && (item.getPurchasePrice() != null)) {
            item.setSellPriceIndustry(item.getSelectedAmount() * (item.getPurchasePrice() * item.getSellMarginIndustry()));
        }
        else{
            Notification.show("Gelieve de aankoopprijs en marge in te vullen");
        }
        selectedProductGrid.getDataProvider().refreshItem(item);
    }

    private void calcNewPriceWithChangedSellPrice(Product item) {
        if((item.getSelectedAmount() != null) && (item.getPurchasePrice() != null)) {
            item.setSellMargin(item.getSellPrice() / (item.getSelectedAmount() * item.getPurchasePrice()));
        }
        else{
            Notification.show("Gelieve de aankoopprijs en marge in te vullen");
        }
        selectedProductGrid.getDataProvider().refreshItem(item);
    }

    private void calcNewPriceWithChangedSellPriceIndustry(Product item) {
        if((item.getSelectedAmount() != null) && (item.getPurchasePrice() != null)) {
            item.setSellMarginIndustry(item.getSellPriceIndustry() / (item.getSelectedAmount() * item.getPurchasePrice()));
        }
        else{
            Notification.show("Gelieve de aankoopprijs en marge in te vullen");
        }
        selectedProductGrid.getDataProvider().refreshItem(item);
    }

    private void setUpBinder() {

        setBinder = new Binder<>(Product.class);
        setEditor.setBinder(setBinder);
        setEditor.setBuffered(false);

        cbSet = new Checkbox();

        tfCode = new TextField();
        tfCode.setWidthFull();
        tfCode.setEnabled(false);

        tfEditName = new TextField();
        tfEditName.setWidthFull();

        tfEditDescription = new TextField();
        tfEditDescription.setWidthFull();

        tfEditPurchasePrice = new TextField();
        tfEditPurchasePrice.setWidthFull();
        tfEditPurchasePrice.addValueChangeListener(event -> {
            try{
                selectedSet.setSellPrice(Double.parseDouble(tfEditPurchasePrice.getValue()) * (Double.parseDouble(tfEditMargin.getValue())));
                setGrid.getDataProvider().refreshAll();
            }
            catch (NumberFormatException e){

            }
        });

        tfEditMargin = new TextField();
        tfEditMargin.setWidthFull();
        tfEditMargin.addValueChangeListener(event -> {
           try{
               selectedSet.setSellPrice(Double.parseDouble(tfEditPurchasePrice.getValue()) * (Double.parseDouble(tfEditMargin.getValue())));
               setGrid.getDataProvider().refreshAll();
           }
           catch (NumberFormatException e){

           }
        });

        tfEditPrice = new TextField();
        tfEditPrice.setWidthFull();
        tfEditPrice.setEnabled(false);


//        setBinder.forField(tfCode)
//                //.asRequired("Mag niet leeg zijn")
//                .withNullRepresentation("")
//                .bind(Product::getProductCode, Product::setProductCode);
//        setNameColumn.setEditorComponent(tfCode);

//        setBinder.forField(tfEditName)
//                //.asRequired(Mag niet leeg zijn")
//                .withNullRepresentation("")
//                .bind(Product::getInternalName, Product::setInternalName);
//        setNameColumn.setEditorComponent(tfEditName);

//        setBinder.forField(tfEditPrice)
//                //.asRequired("Mag niet leeg zijn")
//                .withConverter(new StringToDoubleConverter(String.valueOf(0.0)))
//                .bind(Product::getSellPrice, Product::setSellPrice);
//        setSellColumn.setEditorComponent(tfEditPrice);
        setBinder.addValueChangeListener(event -> {
            productService.save(selectedSet);
            Notification.show("Set is aangepast");
        });
    }


    private Component getProductDetail() {

        setGrid.setWidth("100%");
        setGrid.removeAllColumns();
        setGrid.addColumn(item -> item.getProductCode()).setAutoWidth(true).setFlexGrow(1).setResizable(true).setHeader("Code");
        setNameColumn = setGrid.addColumn(item -> item.getInternalName()).setAutoWidth(true).setFlexGrow(6).setResizable(true).setHeader("Naam");
        setGrid.addColumn(item -> {
            return "";
        }).setHeader("").setAutoWidth(false).setFlexGrow(2);
        setGrid.addColumn(item -> df.format(item.getPurchasePrice())).setHeader("A/P").setAutoWidth(true).setResizable(true);
        setGrid.addColumn(item -> df.format(item.getSellMargin())).setHeader("Marge A").setAutoWidth(false).setFlexGrow(1).setResizable(true);
        setSellColumn = setGrid.addColumn(item -> df.format(item.getSellPrice())).setHeader("V/P A").setAutoWidth(false).setFlexGrow(1).setResizable(true);
        setGrid.addColumn(item -> df.format(item.getSellMarginIndustry())).setHeader("Marge I").setAutoWidth(false).setFlexGrow(1).setResizable(true);
        setSellColumn = setGrid.addColumn(item -> df.format(item.getSellPriceIndustry())).setHeader("V/P I").setAutoWidth(false).setFlexGrow(1).setResizable(true);
        setGrid.addColumn(Product::getUnit).setHeader("EH").setAutoWidth(true).setFlexGrow(1).setFrozenToEnd(true);
        setGrid.addComponentColumn(item -> {
            Button closeButton = new Button("S");
            closeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            closeButton.addClickListener(event -> {
            });
            return closeButton;
        }).setAutoWidth(false).setAutoWidth(false).setFlexGrow(1).setFrozenToEnd(true);

        setGrid.getHeaderRows().clear();

        return setGrid;
    }

    private String getGroupFromSelectedSet(Product set) {
        String groupString = "";
        if(set.getProductLevel1() != null){
            groupString = groupString + set.getProductLevel1().getName() + " ";
        }
        if(set.getProductLevel2() != null){
            groupString = groupString + set.getProductLevel2().getName() + " ";
        }
        if(set.getProductLevel3() != null){
            groupString = groupString + set.getProductLevel3().getName() + " ";
        }
        if(set.getProductLevel4() != null){
            groupString = groupString + set.getProductLevel4().getName() + " ";
        }
        if(set.getProductLevel5() != null){
            groupString = groupString + set.getProductLevel5().getName() + " ";
        }
        if(set.getProductLevel6() != null){
            groupString = groupString + set.getProductLevel6().getName() + " ";
        }
        if(set.getProductLevel7() != null){
            groupString = groupString + set.getProductLevel7().getName() + " ";
        }
        return groupString;
    }

    private void selectItem(Product item) {
        selectedSet = item;

        if((selectedSet.getSetList() != null) && (selectedSet.getSetList().size() > 0)){
            selectProductSubView.setSelectedProductList(selectedSet.getSetList());
            selectProductSubView.setSelectedSet(selectedSet);
            setBinder.readBean(selectedSet);
            groupSpan.setText(getGroupFromSelectedSet(item));
        }
        else{
            List<Product>productList = new ArrayList<>();
            selectedSet.setSetList(productList);
            selectProductSubView.setSelectedProductList(productList);
            selectProductSubView.setSelectedSet(selectedSet);
            setBinder.readBean(selectedSet);
            groupSpan.setText(getGroupFromSelectedSet(item));
        }
        //add Items to productGrid from same folder as selectedSet
        selectProductSubView.setItemsToProductGridToEditSet(item);
    }

    private void setUpSplitLayouts() {
        mainSplitLayout = new SplitLayout();
        mainSplitLayout.setSizeFull();
        mainSplitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);

        secondarySplitLayout = new SplitLayout();
        secondarySplitLayout.setSizeFull();
        secondarySplitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        secondarySplitLayout.setSplitterPosition(30);
        selectProductSubView.setSplitPosition(35.0);
        secondarySplitLayout.getElement()
                .addEventListener("splitter-dragend", event -> {
                    selectProductSubView.setSplitPosition(
                            secondarySplitLayout.getSplitterPosition()
                    );
                });
    }

    public void setSelectedProductForSet(Product selectedProduct) {
        if(selectedProduct != null) {
            selectedSet = selectedProduct;
            setGrid.setItems(selectedProduct);
            if((selectedProduct.getSetList() != null) && (!selectedProduct.getSetList().isEmpty())){
                selectedProductGrid.setItems(selectedProduct.getSetList().stream().filter(item -> item.getBWorkHour() == false).collect(Collectors.toList()));
            }
            else{
                selectedProductGrid.setItems(new ArrayList<>());
            }
            if((selectedProduct.getSetList() != null) && (!selectedProduct.getSetList().isEmpty())){
                selectedWorkhoursGrid.setItems(selectedProduct.getSetList().stream().filter(item -> item.getBWorkHour() == true).collect(Collectors.toList()));
            }
            else{
                selectedWorkhoursGrid.setItems(new ArrayList<>());
            }
            selectItem(selectedProduct);
        }
        else{
            Notification.show("Gelieve eerst een product aan te duiden.");
        }
        updateFooter.run();
    }

    public void setSetList(List<Product> collect) {
        selectedSet = null;
        setGrid.setItems(collect);
        if(collect.size() > 0){
            selectedSet = collect.get(0);
            selectItem(collect.get(0));
            setGrid.select(selectedSet);
            selectedProductGrid.setItems(selectedSet.getSetList().stream().filter(item -> item.getBWorkHour() == false).collect(Collectors.toList()));
            selectedWorkhoursGrid.setItems(selectedSet.getSetList().stream().filter(item -> item.getBWorkHour() == true).collect(Collectors.toList()));
            updateFooter.run();
        }
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

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        myAddRemoveConsumer = event -> {
            UI.getCurrent().access(() -> {
                try {
                    //calc purchase Price
                    setBinder.writeBean(selectedSet);
                    selectedSet.setPurchasePrice(setService.tryToCalculatePurchasePrice(selectedSet));
                    selectedSet.setSellPrice(setService.tryToCalculateSellAgroPrice(selectedSet));
                    selectedSet.setSellPriceIndustry(setService.tryToCalculateSellIndustryPrice(selectedSet));
                    productService.save(selectedSet);
                    setGrid.getDataProvider().refreshItem(selectedSet);
                    setGrid.getDataProvider().refreshAll();
                    selectedProductGrid.setItems(selectedSet.getSetList().stream().filter(item -> item.getBWorkHour() == false).collect(Collectors.toList()));
                    selectedWorkhoursGrid.setItems(selectedSet.getSetList().stream().filter(item -> item.getBWorkHour() == true).collect(Collectors.toList()));
                    updateFooter.run();
                    Notification.show("Set is bewaard.");
                } catch (Exception e) {
                    //Notification notification = Notification.show("Gelieve eerst een set aan te duiden.");
                    //notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });
        };
        listener.addEventConsumer(myAddRemoveConsumer);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        listener.removeEventConsumer(myAddRemoveConsumer);
    }

    Runnable updateFooter = () -> {
        double totalProductA = selectedSet.getSetList().stream().filter(product -> product.getBWorkHour() == false)
                .mapToDouble(product -> product.getSelectedAmount() * product.getSellPrice())
                .sum();

        Span spanTotalProductA = new Span(df.format(totalProductA) + " €");
        spanTotalProductA.getStyle().set("font-weight", "bold").set("font-size", "1.2em");

        footerTotalMaterialsA.getCell(sellPriceProductAColumn)
                .setComponent(spanTotalProductA);

        double totalProductI = selectedSet.getSetList().stream().filter(product -> product.getBWorkHour() == false)
                .mapToDouble(product -> {
                    if(product.getSellPriceIndustry() != null && product.getSellPriceIndustry() > 0){
                        return product.getSelectedAmount() * product.getSellPriceIndustry();
                    }
                    else{
                        return product.getSelectedAmount() * product.getSellPrice();
                    }
                })
                .sum();

        Span spanTotalProductI = new Span(df.format(totalProductI) + " €");
        spanTotalProductI.getStyle().set("font-weight", "bold").set("font-size", "1.2em");

        footerTotalMaterialsA.getCell(sellPriceProductIColumn)
                .setComponent(spanTotalProductI);

        double totalWorkhoursA = selectedSet.getSetList().stream().filter(product -> product.getBWorkHour() == true)
                .mapToDouble(product -> {
                        return product.getSelectedAmount() * product.getSellPrice();
                })
                .sum();

        Span spanTotalWorkhoursA = new Span(df.format(totalWorkhoursA) + " €");
        spanTotalWorkhoursA.getStyle().set("font-weight", "bold").set("font-size", "1.2em");


        footerTotalWorkHoursA.getCell(sellPriceWorkhoursAColumn)
                .setComponent(spanTotalWorkhoursA);

        double totalWorkhoursI = selectedSet.getSetList().stream().filter(product -> product.getBWorkHour() == true)
                .mapToDouble(product -> product.getSelectedAmount() * product.getSellPriceIndustry())
                .sum();

        Span spanTotalWorkhoursI = new Span(df.format(totalWorkhoursI) + " €");
        spanTotalWorkhoursI.getStyle().set("font-weight", "bold").set("font-size", "1.2em");

        footerTotalWorkHoursA.getCell(sellPriceWorkhoursIColumn)
                .setComponent(spanTotalWorkhoursI);

        double purchaseWorkhour = selectedSet.getSetList().stream().filter(product -> product.getBWorkHour() == true)
                .mapToDouble(product -> product.getSelectedAmount() * product.getPurchasePrice())
                .sum();

        Span spanTotalPurchaseWorkhour = new Span(df.format(purchaseWorkhour) + " €");
        spanTotalPurchaseWorkhour.getStyle().set("font-weight", "bold").set("font-size", "1.2em");

        footerTotalWorkHoursA.getCell(purchaseWorkhoursColumn)
                .setComponent(spanTotalPurchaseWorkhour);

        double purchaseProduct = selectedSet.getSetList().stream().filter(product -> product.getBWorkHour() == false)
                .mapToDouble(product -> product.getSelectedAmount() * product.getPurchasePrice())
                .sum();

        Span spanTotalPurchaseProduct = new Span(df.format(purchaseProduct) + " €");
        spanTotalPurchaseProduct.getStyle().set("font-weight", "bold").set("font-size", "1.2em");

        footerTotalMaterialsA.getCell(purchaseProductColumn)
                .setComponent(spanTotalPurchaseProduct);

        selectedSet.setSellPrice(totalProductA + totalWorkhoursA);
        selectedSet.setSellPriceIndustry(totalProductI + totalWorkhoursI);
        selectedSet.setPurchasePrice(purchaseProduct + purchaseWorkhour);
        selectedSet.setSellMargin((totalProductA + totalWorkhoursA) / (purchaseProduct + purchaseWorkhour));
        selectedSet.setSellMarginIndustry((totalProductI + totalWorkhoursI) / (purchaseProduct + purchaseWorkhour));

        //check if every element is set as Element in the DB
        if((selectedSet.getSetList() != null) && (selectedSet.getSetList().size() > 0)){
            for(Product product : selectedSet.getSetList()){
                Optional<List<Product>> sameCodeList = productService.findByProductCodeEqualCaseInsensitive(product.getProductCode());
                if(sameCodeList.isPresent()){
                    for(Product dbProduct : sameCodeList.get()){
                        if(productService.matchesLevel(dbProduct,product.getProductLevel1(),
                                product.getProductLevel2(),
                                product.getProductLevel3(),
                                product.getProductLevel4(),
                                product.getProductLevel5(),
                                product.getProductLevel6(),
                                product.getProductLevel7())){
                            dbProduct.setSetElement(true);
                            productService.save(dbProduct);
                        }
                    }
                }
            }
        }

        //check if no Elements are in Set -> then this is no set anymore...
        if((selectedSet.getSetList() != null) && (selectedSet.getSetList().size() > 0)){
            selectedSet.setSet(true);
        }
        else{
            selectedSet.setSet(false);
        }

        setGrid.getDataProvider().refreshItem(selectedSet);
        productService.save(selectedSet);
    };

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
