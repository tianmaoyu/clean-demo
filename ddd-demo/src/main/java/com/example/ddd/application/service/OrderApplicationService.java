package com.example.ddd.application.service;

import com.example.ddd.application.dto.CreateOrderRequest;
import com.example.ddd.application.dto.OrderResponse;
import com.example.ddd.domain.model.*;
import com.example.ddd.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单应用服务 - 应用层
 * 协调领域服务完成业务用例
 */
@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderService orderService;

    /**
     * 创建订单应用服务
     */
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        // 转换 DTO 为领域模型
        List<OrderItem> items = request.getItems().stream()
                .map(item -> new OrderItem(
                        item.getProductId(),
                        item.getProductName(),
                        item.getUnitPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        ShippingAddress address = new ShippingAddress(
                request.getShippingAddress().getStreet(),
                request.getShippingAddress().getCity(),
                request.getShippingAddress().getState(),
                request.getShippingAddress().getZipCode(),
                request.getShippingAddress().getCountry()
        );

        // 使用工厂方法创建订单
        Order order = Order.create(request.getCustomerId(), address, items);

        // 调用领域服务保存订单
        Order savedOrder = orderService.createOrder(order);

        // 转换为响应 DTO
        return toResponse(savedOrder);
    }

    /**
     * 获取订单
     */
    @Transactional(readOnly = true)
    public OrderResponse getOrder(String orderId) {
        return orderService.getOrder(orderId)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }

    /**
     * 确认订单
     */
    @Transactional
    public void confirmOrder(String orderId) {
        orderService.confirmOrder(orderId);
    }

    /**
     * 取消订单
     */
    @Transactional
    public void cancelOrder(String orderId) {
        orderService.cancelOrder(orderId);
    }

    /**
     * 发货
     */
    @Transactional
    public void shipOrder(String orderId) {
        orderService.shipOrder(orderId);
    }

    /**
     * 获取订单状态
     */
    @Transactional(readOnly = true)
    public String getOrderStatus(String orderId) {
        return orderService.getOrderStatus(orderId).name();
    }

    /**
     * 领域模型转响应 DTO
     */
    private OrderResponse toResponse(Order order) {
        List<OrderResponse.OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(item -> new OrderResponse.OrderItemDTO(
                        item.getProductId(),
                        item.getProductName(),
                        item.getUnitPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        OrderResponse.ShippingAddressDTO addressDTO = new OrderResponse.ShippingAddressDTO(
                order.getShippingAddress().getStreet(),
                order.getShippingAddress().getCity(),
                order.getShippingAddress().getState(),
                order.getShippingAddress().getZipCode(),
                order.getShippingAddress().getCountry()
        );

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount().getAmount())
                .shippingAddress(addressDTO)
                .items(itemDTOs)
                .build();
    }
}
