package com.adverto.dejonghe.tabletdemo.tabletdemo.customEvents;

import org.springframework.context.ApplicationEvent;

public class ReloadProductListEvent extends ApplicationEvent {
    private final String message;

    public ReloadProductListEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
