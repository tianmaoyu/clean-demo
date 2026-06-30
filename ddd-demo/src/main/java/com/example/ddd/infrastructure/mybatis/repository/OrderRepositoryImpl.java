package com.example.ddd.infrastructure.mybatis.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ddd.domain.model.Order;
import com.example.ddd.domain.repository.OrderRepository;
import com.example.ddd.infrastructure.mybatis.converter.OrderConverter;
import com.example.ddd.infrastructure.mybatis.entity.OrderEntity;
import com.example.ddd.infrastructure.mybatis.entity.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 订单仓储实现 - 基础设施层
 * 实现领域层定义的 OrderRepository 接口
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderConverter converter;

    @Override
    @Transactional
    public void save(Order order) {
        // 转换领域模型为数据库实体
        OrderEntity orderEntity = converter.toEntity(order);
        
        // 检查是新增还是更新
        OrderEntity existing = orderMapper.selectById(order.getId());
        if (existing == null) {
            orderMapper.insert(orderEntity);
            
            // 保存订单项
            List<OrderItemEntity> itemEntities = converter.toItemEntities(order.getId(), order.getItems());
            for (OrderItemEntity itemEntity : itemEntities) {
                orderItemMapper.insert(itemEntity);
            }
        } else {
            orderMapper.updateById(orderEntity);
            
            // 先删除旧的订单项，再插入新的（简化处理）
            orderItemMapper.deleteByOrderId(order.getId());
            List<OrderItemEntity> itemEntities = converter.toItemEntities(order.getId(), order.getItems());
            for (OrderItemEntity itemEntity : itemEntities) {
                orderItemMapper.insert(itemEntity);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(String id) {
        OrderEntity orderEntity = orderMapper.selectById(id);
        if (orderEntity == null) {
            return Optional.empty();
        }
        
        // 查询订单项
        List<OrderItemEntity> itemEntities = orderItemMapper.selectByOrderId(id);
        
        // 转换为领域模型
        Order order = converter.toDomain(orderEntity, itemEntities);
        return Optional.of(order);
    }

    @Override
    @Transactional
    public void delete(String id) {
        // 先删除订单项
        orderItemMapper.deleteByOrderId(id);
        // 再删除订单
        orderMapper.deleteById(id);
    }
}
