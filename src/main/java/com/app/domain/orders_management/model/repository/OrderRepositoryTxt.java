package com.app.domain.orders_management.model.repository;

import com.app.infrastructure.persistence.entity.OrderEntity;

import java.util.List;

public interface OrderRepositoryTxt {
    List<OrderEntity> findAll();
}
