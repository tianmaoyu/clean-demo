package com.example.ddd.domain.event;

import com.example.ddd.domain.model.DomainEvent;
import lombok.Getter;

import java.time.Instant;

/**
 * 订单确认事件
 */
@Getter
public class OrderConfirmedEvent implements DomainEvent {
    
    private final String orderId;
    private final long timestamp;

    public OrderConfirmedEvent(String orderId) {
        this.orderId = orderId;
        this.timestamp = Instant.now().toEpochMilli();
    }
}
