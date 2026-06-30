package com.example.ddd.domain.model;

/**
 * 领域事件基类
 */
public interface DomainEvent {
    /**
     * 事件发生的时间戳
     */
    long getTimestamp();
}
