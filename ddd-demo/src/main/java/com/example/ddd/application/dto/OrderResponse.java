package com.example.ddd.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单响应 DTO - 应用层
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    
    private String id;
    private String customerId;
    private String status;
    private BigDecimal totalAmount;
    private ShippingAddressDTO shippingAddress;
    private List<OrderItemDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingAddressDTO {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDTO {
        private String productId;
        private String productName;
        private BigDecimal unitPrice;
        private int quantity;
    }
}
