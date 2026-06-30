package com.example.ddd.infrastructure.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单数据库实体 - MyBatis-Plus Entity
 * 用于数据库持久化，与领域模型分离
 */
@Data
@NoArgsConstructor
@TableName("t_order")
public class OrderEntity {
    
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String customerId;
    
    private String status;
    
    private BigDecimal totalAmount;
    
    private String shippingStreet;
    private String shippingCity;
    private String shippingState;
    private String shippingZipCode;
    private String shippingCountry;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
