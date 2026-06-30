package com.example.ddd.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Order Aggregate Root - 订单聚合根
 * 体现DDD核心概念：聚合根、实体、值对象
 */
@Getter
@NoArgsConstructor
public class Order {
    
    private String id;
    private String customerId;
    private OrderStatus status;
    private Money totalAmount;
    private List<OrderItem> items;
    private ShippingAddress shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 工厂方法创建订单 - 保证业务规则
     */
    public static Order create(String customerId, ShippingAddress shippingAddress, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        
        Order order = new Order();
        order.id = UUID.randomUUID().toString();
        order.customerId = customerId;
        order.status = OrderStatus.CREATED;
        order.shippingAddress = shippingAddress;
        order.items = new ArrayList<>(items);
        order.createdAt = LocalDateTime.now();
        order.updatedAt = LocalDateTime.now();
        
        // 计算总金额
        order.calculateTotalAmount();
        
        return order;
    }

    /**
     * 业务行为：添加订单项
     */
    public void addItem(Product product, int quantity) {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot add item to order with status: " + this.status);
        }
        
        OrderItem newItem = new OrderItem(product.getId(), product.getName(), 
                product.getPrice(), quantity);
        this.items.add(newItem);
        this.calculateTotalAmount();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 业务行为：确认订单
     */
    public void confirm() {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot confirm order with status: " + this.status);
        }
        if (this.items.isEmpty()) {
            throw new IllegalStateException("Cannot confirm empty order");
        }
        
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
        
        // 发布领域事件（简化版）
        DomainEventPublisher.publish(new OrderConfirmedEvent(this.id));
    }

    /**
     * 业务行为：取消订单
     */
    public void cancel() {
        if (this.status == OrderStatus.SHIPPED || this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel order with status: " + this.status);
        }
        
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
        
        // 发布领域事件
        DomainEventPublisher.publish(new OrderCancelledEvent(this.id));
    }

    /**
     * 业务行为：发货
     */
    public void ship() {
        if (this.status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot ship order with status: " + this.status);
        }
        
        this.status = OrderStatus.SHIPPED;
        this.updatedAt = LocalDateTime.now();
        
        DomainEventPublisher.publish(new OrderShippedEvent(this.id));
    }

    private void calculateTotalAmount() {
        BigDecimal total = this.items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalAmount = new Money(total);
    }

    // Getters for nested entities
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public OrderStatus getStatus() { return status; }
    public Money getTotalAmount() { return totalAmount; }
    public List<OrderItem> getItems() { return new ArrayList<>(items); }
    public ShippingAddress getShippingAddress() { return shippingAddress; }
}
