package com.app.domain.policy.abstract_factory.loader.db;

import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import com.app.domain.policy.abstract_factory.loader.OrderDataLoader;
import com.app.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDataLoaderDbImpl implements OrderDataLoader {
    private final OrderRepositoryDb orderRepository;
    @Override
    public List<OrderEntity> load() {
        return orderRepository.findAll();
    }
}
