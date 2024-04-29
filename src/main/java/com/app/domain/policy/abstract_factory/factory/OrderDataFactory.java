package com.app.domain.policy.abstract_factory.factory;

import com.app.domain.orders_management.model.Order;
import com.app.infrastructure.persistence.entity.OrderEntity;

import java.util.List;

public interface OrderDataFactory extends DataFactory<List<OrderEntity>, List<Order>> {
}
