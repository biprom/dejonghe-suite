package com.adverto.dejonghe.server.views.customers.workAddress.workAddressImages;

import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.server.views.subViews.CurrentWorkOrdersSubView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Scope("prototype")
public class WorkImageCard extends Card {

    private final CurrentWorkOrdersSubView currentWorkOrdersSubView;

    private Boolean containsImages = Boolean.FALSE;

    private final Dialog workOrdersDialog = new Dialog();

    @Autowired
    public WorkImageCard(CurrentWorkOrdersSubView currentWorkOrdersSubView) {

        this.currentWorkOrdersSubView = currentWorkOrdersSubView;

        this.getStyle().set("background-color", "#fbf6ea");

        configureDialog();
        setUpThisCard();
    }

    private void configureDialog() {

        workOrdersDialog.setWidth("80vw");
        workOrdersDialog.setHeight("80vh");

        workOrdersDialog.setCloseOnEsc(true);
        workOrdersDialog.setCloseOnOutsideClick(true);

        Div dialogTitle = new Div(new Text("Foto's werkbonnen"));
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

        currentWorkOrdersSubView.setWidthFull();
        currentWorkOrdersSubView.setHeightFull();

        workOrdersDialog.add(currentWorkOrdersSubView);
    }

    private void setUpThisCard() {

        this.removeAll();

        this.addThemeVariants(
                CardVariant.LUMO_OUTLINED,
                CardVariant.LUMO_ELEVATED,
                CardVariant.LUMO_HORIZONTAL
        );

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Div title = new Div(new Text("Foto's"));
        title.addClassName("card-title");

        title.getStyle()
                .set("cursor", "pointer");

        title.addSingleClickListener(event -> openDialog());

        horizontalLayout.add(title);

        this.setTitle(horizontalLayout);
    }

    private Div checkIfThereAreImages() {

        Div subtitle;

        if (containsImages) {
            subtitle = new Div(
                    new Text("Dit werfadres bevat foto's")
            );
        } else {
            subtitle = new Div(
                    new Text("Dit werfadres bevat geen foto's")
            );
        }

        subtitle.getStyle()
                .set("font-size", "20px");

        if (containsImages) {

            subtitle.getStyle()
                    .set("cursor", "pointer")
                    .set("text-decoration", "underline");

            subtitle.addSingleClickListener(event -> openDialog());
        }

        return subtitle;
    }

    private void openDialog() {

        if (!containsImages) {
            return;
        }

        workOrdersDialog.open();
    }

    public void setData(
            Optional<List<WorkOrder>> allStartersByWorkAddressName
    ) {

        List<WorkOrder> workOrders =
                allStartersByWorkAddressName.orElse(List.of());

        currentWorkOrdersSubView
                .addItemsToPendingWorkOrderGrid(workOrders);

        currentWorkOrdersSubView
                .setAuthorisation(UserFunction.ADMIN);

        containsImages = !workOrders.isEmpty();

        this.add(checkIfThereAreImages());
    }
}