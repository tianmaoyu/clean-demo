package com.example.ddd.domain.model;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 领域事件发布器（简化实现）
 * 实际项目中可以使用 Spring ApplicationEventPublisher
 */
@Slf4j
public class DomainEventPublisher {
    
    private static final List<DomainEvent> publishedEvents = new ArrayList<>();

    public static void publish(DomainEvent event) {
        log.info("Publishing domain event: {} for order: {}", 
                event.getClass().getSimpleName(),
                event instanceof com.example.ddd.domain.event.OrderConfirmedEvent ? 
                    ((com.example.ddd.domain.event.OrderConfirmedEvent) event).getOrderId() :
                event instanceof com.example.ddd.domain.event.OrderCancelledEvent ? 
                    ((com.example.ddd.domain.event.OrderCancelledEvent) event).getOrderId() :
                event instanceof com.example.ddd.domain.event.OrderShippedEvent ? 
                    ((com.example.ddd.domain.event.OrderShippedEvent) event).getOrderId() : "unknown");
        publishedEvents.add(event);
    }

    public static List<DomainEvent> getPublishedEvents() {
        return new ArrayList<>(publishedEvents);
    }

    public static void clearEvents() {
        publishedEvents.clear();
    }
}
