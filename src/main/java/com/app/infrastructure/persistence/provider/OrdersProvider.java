package com.app.infrastructure.persistence.provider;

import com.app.domain.orders_management.model.Order;

import java.util.List;

public interface OrdersProvider {
    List<Order> provide();
}
