package com.app.domain.policy.abstract_factory.converter.impl;

import com.app.domain.orders_management.model.Order;
import com.app.domain.policy.abstract_factory.converter.ToOrderConverter;
import com.app.infrastructure.persistence.entity.OrderEntity;

import java.util.List;

public class ToOrderConverterImpl implements ToOrderConverter {
    @Override
    public List<Order> convert(List<OrderEntity> data) {
        return data
                .stream()
                .map(OrderEntity::toDomain)
                .toList();
    }
}
