package com.example.ddd.domain.repository;

import com.example.ddd.domain.model.Order;
import java.util.Optional;

/**
 * 订单仓储接口 - 定义在领域层
 * 体现 DDD 依赖倒置原则：基础设施层依赖领域层
 */
public interface OrderRepository {
    
    /**
     * 保存订单
     */
    void save(Order order);
    
    /**
     * 根据 ID 查找订单
     */
    Optional<Order> findById(String id);
    
    /**
     * 删除订单
     */
    void delete(String id);
}
