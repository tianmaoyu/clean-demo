package com.example.ddd.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ddd.infrastructure.mybatis.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单 Mapper 接口 - MyBatis-Plus
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
}
