package com.example.ddd.domain.model;

/**
 * 订单状态枚举 - 值对象
 */
public enum OrderStatus {
    CREATED,      // 已创建
    CONFIRMED,    // 已确认
    SHIPPED,      // 已发货
    DELIVERED,    // 已送达
    CANCELLED     // 已取消
}
