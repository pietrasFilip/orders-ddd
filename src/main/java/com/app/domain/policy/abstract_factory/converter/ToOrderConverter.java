package com.app.domain.policy.abstract_factory.converter;

import com.app.domain.orders_management.model.Order;
import com.app.infrastructure.persistence.entity.OrderEntity;

import java.util.List;

public interface ToOrderConverter extends Converter<List<OrderEntity>, List<Order>> {
}
