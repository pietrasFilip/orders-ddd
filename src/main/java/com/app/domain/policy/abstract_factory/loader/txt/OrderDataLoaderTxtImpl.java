package com.app.domain.policy.abstract_factory.loader.txt;

import com.app.domain.orders_management.model.repository.OrderRepositoryTxt;
import com.app.domain.policy.abstract_factory.loader.OrderDataLoader;
import com.app.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDataLoaderTxtImpl implements OrderDataLoader {
    private final OrderRepositoryTxt orderRepository;
    @Override
    public List<OrderEntity> load() {
        return orderRepository.findAll();
    }
}
