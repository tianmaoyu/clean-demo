package com.example.ddd.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单项实体 - 聚合根内的子实体
 */
@Getter
@NoArgsConstructor
public class OrderItem {
    
    private String productId;
    private String productName;
    private Money unitPrice;
    private int quantity;

    public OrderItem(String productId, String productName, BigDecimal unitPrice, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = new Money(unitPrice);
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice.getAmount();
    }
}
