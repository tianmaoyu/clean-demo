package com.example.ddd.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 产品实体 - 用于业务操作
 */
@Getter
@RequiredArgsConstructor
public class Product {
    
    private final String id;
    private final String name;
    private final java.math.BigDecimal price;
}
