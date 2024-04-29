package com.app.domain.policy.abstract_factory.loader.json;

import com.app.domain.orders_management.model.repository.OrderRepositoryJson;
import com.app.domain.policy.abstract_factory.loader.OrderDataLoader;
import com.app.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDataLoaderJsonImpl implements OrderDataLoader {
    private final OrderRepositoryJson orderRepository;
    @Override
    public List<OrderEntity> load() {
        return orderRepository.findAll();
    }
}
