package com.example.ddd.infrastructure.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单项数据库实体 - MyBatis-Plus Entity
 */
@Data
@NoArgsConstructor
@TableName("t_order_item")
public class OrderItemEntity {
    
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String orderId;
    
    private String productId;
    
    private String productName;
    
    private BigDecimal unitPrice;
    
    private Integer quantity;
}
