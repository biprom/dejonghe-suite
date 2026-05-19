package com.adverto.dejonghe.server.customEvents;

import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Component
@UIScope
public class AddProductEventListener {

    // Houd een lijst bij van alle geregistreerde consumers
    private final List<Consumer<AddRemoveProductEvent>> consumers = new CopyOnWriteArrayList<>();

    /**
     * Registreer een nieuwe consumer
     */
    public Registration addEventConsumer(Consumer<AddRemoveProductEvent> consumer) {
        consumers.add(consumer);
        return () -> consumers.remove(consumer);
    }

    /**
     * Verwijder een consumer als die niet meer nodig is
     */
    public void removeEventConsumer(Consumer<AddRemoveProductEvent> consumer) {
        consumers.remove(consumer);
    }

    /**
     * Event listener: roept alle geregistreerde consumers aan
     */
    @EventListener
    public void handleMyCustomEvent(AddRemoveProductEvent event) {
        for (Consumer<AddRemoveProductEvent> consumer : consumers) {
            consumer.accept(event);
        }
    }
}
