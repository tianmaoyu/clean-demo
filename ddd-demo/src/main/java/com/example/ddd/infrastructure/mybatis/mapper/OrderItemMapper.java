package com.example.ddd.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ddd.infrastructure.mybatis.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单项 Mapper 接口 - MyBatis-Plus
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItemEntity> {
    
    /**
     * 根据订单 ID 查询订单项
     */
    List<OrderItemEntity> selectByOrderId(@Param("orderId") String orderId);
    
    /**
     * 删除订单的所有订单项
     */
    void deleteByOrderId(@Param("orderId") String orderId);
}
