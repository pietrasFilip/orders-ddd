package com.app.domain.policy.abstract_factory.factory.json;

import com.app.domain.orders_management.model.Order;
import com.app.domain.orders_management.model.repository.OrderRepositoryJson;
import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.converter.impl.ToOrderConverterImpl;
import com.app.domain.policy.abstract_factory.factory.OrderDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.loader.json.OrderDataLoaderJsonImpl;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.domain.policy.abstract_factory.validator.OrderDataValidator;
import com.app.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromJsonToOrder implements OrderDataFactory {
    private final OrderRepositoryJson orderRepositoryJson;
    private final OrderDataValidator orderDataValidator;

    @Override
    public DataLoader<List<OrderEntity>> createDataLoader() {
        return new OrderDataLoaderJsonImpl(orderRepositoryJson);
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
