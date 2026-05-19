package com.adverto.dejonghe.tablet.views.subViews;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.adverto.dejonghe.common.entities.product.product.ProductLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class ShowLinkSubVieuw extends VerticalLayout implements BeforeEnterObserver {

    List<ProductLink>linkList;
    Span title = new Span();

    @Autowired
    public ShowLinkSubVieuw() {
      setUpLinks();
    }



    private void setUpLinks() {
        this.removeAll();
        add(title);
        if((linkList != null) && (linkList.size() > 0)) {
            for (ProductLink link : linkList) {
                Anchor anchor = new Anchor(link.getLink(), link.getLink());
                anchor.setTarget("_blank");
                add(anchor);
            }
        }
    }

    public void setSelectedWorkOrder(List<ProductLink> linkList) {
        this.linkList = linkList;
        setUpLinks();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    }
}
