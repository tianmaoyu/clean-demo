package com.example.ddd.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 创建订单请求 DTO - 应用层
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    
    @NotNull(message = "Customer ID is required")
    private String customerId;
    
    @NotNull(message = "Shipping address is required")
    private ShippingAddressDTO shippingAddress;
    
    @NotEmpty(message = "Order must have at least one item")
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
        @NotNull(message = "Product ID is required")
        private String productId;
        
        @NotNull(message = "Product name is required")
        private String productName;
        
        @NotNull(message = "Unit price is required")
        private java.math.BigDecimal unitPrice;
        
        @NotNull(message = "Quantity is required")
        private Integer quantity;
    }
}
