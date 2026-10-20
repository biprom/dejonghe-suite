package com.adverto.dejonghe.server.views.customers.workAddress.workAddressImages;

import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.server.views.subViews.CurrentDocumentSubView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

import java.util.Optional;

@Component
@Scope("prototype")
public class WorkImageCard extends Card {

    private final CurrentDocumentSubView currentDocumentSubView;

    private Boolean containsDocuments = Boolean.FALSE;

    private final Dialog workOrdersDialog = new Dialog();

    private MenuBar actionBar;


    @Autowired
    public WorkImageCard(CurrentDocumentSubView currentDocumentSubView) {

        this.currentDocumentSubView = currentDocumentSubView;

        this.getStyle().set("background-color", "#fbf6ea");

        configureDialog();
        setUpThisCard();
    }

    private void configureDialog() {

        workOrdersDialog.setWidth("80vw");
        workOrdersDialog.setHeight("80vh");

        workOrdersDialog.setCloseOnEsc(true);
        workOrdersDialog.setCloseOnOutsideClick(true);

        Div dialogTitle = new Div(new Text("Documenten"));
        dialogTitle.getStyle()
                .set("font-size", "22px")
                .set("font-weight", "600");

        Button closeButton = new Button(
                new Icon(VaadinIcon.CLOSE),
                event -> workOrdersDialog.close()
        );

        closeButton.getStyle()
                .set("margin-left", "auto");

        HorizontalLayout header = new HorizontalLayout(
                dialogTitle,
                closeButton
        );

        header.setWidthFull();
        header.setAlignItems(HorizontalLayout.Alignment.CENTER);

        workOrdersDialog.getHeader().add(header);

        currentDocumentSubView.setWidthFull();
        currentDocumentSubView.setHeightFull();

        workOrdersDialog.add(currentDocumentSubView);
    }

    private void setUpThisCard() {

        this.removeAll();

        this.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL
        );

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Div title = new Div(new Text("Documenten"));
        title.addClassName("card-title");

        title.getStyle()
                .set("cursor", "pointer");

        title.addSingleClickListener(event -> openDialog());

        horizontalLayout.add(title);

        this.setTitle(horizontalLayout);

    }

    private HorizontalLayout getActionMenu() {

        HorizontalLayout actionBarLayout =
                new HorizontalLayout();

        actionBarLayout.setWidthFull();

        VerticalLayout actionVLayout =
                new VerticalLayout();

        actionVLayout.setWidth("30%");

        actionVLayout.setAlignItems(
                FlexComponent.Alignment.CENTER
        );

        actionBar = new MenuBar();

        actionBar.addClassName("large-menubar");

        actionBar.addThemeVariants(
                MenuBarVariant.LUMO_DROPDOWN_INDICATORS
        );

        MenuItem actie =
                actionBar.addItem("Actie");

        actie.getElement()
                .getClassList()
                .add("menu-as-button");

        actie.getSubMenu()
                .addItem(
                        "Open documenten",
                        openDevices()
                );

        actionVLayout.add(actionBar);

        VerticalLayout layout =
                new VerticalLayout();

        actionBarLayout.addToStart(layout);
        actionBarLayout.addToEnd(actionVLayout);

        return actionBarLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openDevices() {
        return event -> openDialog();
    }

    private Div checkIfThereAreImages() {

        Div subtitle;

        if (containsDocuments) {
            subtitle = new Div(
                    new Text("Dit werfadres bevat documenten")
            );
        } else {
            subtitle = new Div(
                    new Text("Dit werfadres bevat geen documenten")
            );
        }

        subtitle.getStyle()
                .set("font-size", "20px");


        subtitle.getStyle()
                .set("cursor", "pointer")
                .set("text-decoration", "underline");

        subtitle.addSingleClickListener(event -> openDialog());


        return subtitle;
    }

    private void openDialog() {

        workOrdersDialog.open();
    }

    public void setData(
            Optional<Address> workAddress,
            Consumer<Address> saveHandler) {

        currentDocumentSubView.setSaveHandler(saveHandler);

        currentDocumentSubView.addItemsToGrid(workAddress);

        currentDocumentSubView.setAuthorisation(UserFunction.ADMIN);

        containsDocuments = workAddress
                .map(Address::getCoupledDocumentBundle)
                .filter(documents -> !documents.isEmpty())
                .isPresent();

        this.add(checkIfThereAreImages());
        this.add(getActionMenu());
    }
}