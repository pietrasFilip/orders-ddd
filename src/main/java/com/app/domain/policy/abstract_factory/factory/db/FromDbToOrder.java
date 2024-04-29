package com.app.domain.policy.abstract_factory.factory.db;

import com.app.domain.orders_management.model.Order;
import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.converter.impl.ToOrderConverterImpl;
import com.app.domain.policy.abstract_factory.factory.OrderDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.loader.db.OrderDataLoaderDbImpl;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.domain.policy.abstract_factory.validator.OrderDataValidator;
import com.app.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromDbToOrder implements OrderDataFactory {
    private final OrderRepositoryDb orderRepositoryDb;
    private final OrderDataValidator orderDataValidator;

    @Override
    public DataLoader<List<OrderEntity>> createDataLoader() {
        return new OrderDataLoaderDbImpl(orderRepositoryDb);
    }

    @Override
    public DataValidator<List<OrderEntity>> createValidator() {
        return orderDataValidator;
    }

    @Override
    public Converter<List<OrderEntity>, List<Order>> createConverter() {
        return new ToOrderConverterImpl();
    }
}
