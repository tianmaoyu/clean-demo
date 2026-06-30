package com.example.ddd.domain.service;

import com.example.ddd.domain.model.Order;
import com.example.ddd.domain.model.OrderStatus;
import com.example.ddd.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 订单领域服务 - 处理跨聚合的业务逻辑
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;

    /**
     * 创建订单
     */
    @Transactional
    public Order createOrder(Order order) {
        orderRepository.save(order);
        return order;
    }

    /**
     * 获取订单
     */
    @Transactional(readOnly = true)
    public Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    /**
     * 确认订单
     */
    @Transactional
    public void confirmOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.confirm();
        orderRepository.save(order);
    }

    /**
     * 取消订单
     */
    @Transactional
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.cancel();
        orderRepository.save(order);
    }

    /**
     * 发货
     */
    @Transactional
    public void shipOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.ship();
        orderRepository.save(order);
    }

    /**
     * 获取订单状态
     */
    @Transactional(readOnly = true)
    public OrderStatus getOrderStatus(String orderId) {
        return orderRepository.findById(orderId)
                .map(Order::getStatus)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }
}
