package com.example.ddd.domain.event;

import com.example.ddd.domain.model.DomainEvent;
import lombok.Getter;

import java.time.Instant;

/**
 * 订单取消事件
 */
@Getter
public class OrderCancelledEvent implements DomainEvent {
    
    private final String orderId;
    private final long timestamp;

    public OrderCancelledEvent(String orderId) {
        this.orderId = orderId;
        this.timestamp = Instant.now().toEpochMilli();
    }
}
