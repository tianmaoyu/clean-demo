package com.example.ddd.infrastructure.mybatis.converter;

import com.example.ddd.domain.model.*;
import com.example.ddd.infrastructure.mybatis.entity.OrderEntity;
import com.example.ddd.infrastructure.mybatis.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 领域模型与数据库实体转换器
 * 体现 DDD 中基础设施层对领域层的依赖
 */
@Component
public class OrderConverter {

    /**
     * 领域模型 -> 数据库实体
     */
    public OrderEntity toEntity(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setStatus(order.getStatus().name());
        entity.setTotalAmount(order.getTotalAmount().getAmount());
        
        ShippingAddress address = order.getShippingAddress();
        if (address != null) {
            entity.setShippingStreet(address.getStreet());
            entity.setShippingCity(address.getCity());
            entity.setShippingState(address.getState());
            entity.setShippingZipCode(address.getZipCode());
            entity.setShippingCountry(address.getCountry());
        }
        
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());
        
        return entity;
    }

    /**
     * 数据库实体 -> 领域模型
     */
    public Order toDomain(OrderEntity entity, List<OrderItemEntity> itemEntities) {
        if (entity == null) {
            return null;
        }
        
        List<OrderItem> items = new ArrayList<>();
        if (itemEntities != null) {
            for (OrderItemEntity itemEntity : itemEntities) {
                OrderItem item = new OrderItem(
                    itemEntity.getProductId(),
                    itemEntity.getProductName(),
                    itemEntity.getUnitPrice(),
                    itemEntity.getQuantity()
                );
                items.add(item);
            }
        }
        
        ShippingAddress address = null;
        if (entity.getShippingStreet() != null) {
            address = new ShippingAddress(
                entity.getShippingStreet(),
                entity.getShippingCity(),
                entity.getShippingState(),
                entity.getShippingZipCode(),
                entity.getShippingCountry()
            );
        }
        
        Order order = new Order();
        // 使用反射或其他方式设置私有字段（简化处理）
        try {
            java.lang.reflect.Field idField = Order.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(order, entity.getId());
            
            java.lang.reflect.Field customerIdField = Order.class.getDeclaredField("customerId");
            customerIdField.setAccessible(true);
            customerIdField.set(order, entity.getCustomerId());
            
            java.lang.reflect.Field statusField = Order.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(order, OrderStatus.valueOf(entity.getStatus()));
            
            java.lang.reflect.Field totalAmountField = Order.class.getDeclaredField("totalAmount");
            totalAmountField.setAccessible(true);
            totalAmountField.set(order, new Money(entity.getTotalAmount()));
            
            java.lang.reflect.Field itemsField = Order.class.getDeclaredField("items");
            itemsField.setAccessible(true);
            itemsField.set(order, items);
            
            java.lang.reflect.Field addressField = Order.class.getDeclaredField("shippingAddress");
            addressField.setAccessible(true);
            addressField.set(order, address);
            
            java.lang.reflect.Field createdAtField = Order.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(order, entity.getCreatedAt());
            
            java.lang.reflect.Field updatedAtField = Order.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(order, entity.getUpdatedAt());
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert OrderEntity to Order", e);
        }
        
        return order;
    }

    /**
     * 订单项领域模型 -> 数据库实体
     */
    public List<OrderItemEntity> toItemEntities(String orderId, List<OrderItem> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        
        return items.stream().map(item -> {
            OrderItemEntity entity = new OrderItemEntity();
            entity.setId(java.util.UUID.randomUUID().toString());
            entity.setOrderId(orderId);
            entity.setProductId(item.getProductId());
            entity.setProductName(item.getProductName());
            entity.setUnitPrice(item.getUnitPrice());
            entity.setQuantity(item.getQuantity());
            return entity;
        }).collect(Collectors.toList());
    }
}
