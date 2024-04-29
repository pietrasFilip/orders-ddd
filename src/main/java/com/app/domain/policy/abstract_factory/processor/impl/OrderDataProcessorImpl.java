package com.app.domain.policy.abstract_factory.processor.impl;

import com.app.domain.orders_management.model.Order;
import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.factory.OrderDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.processor.OrderDataProcessor;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.OrderEntity;

import java.util.List;

public class OrderDataProcessorImpl implements OrderDataProcessor {
    private final DataLoader<List<OrderEntity>> dataLoader;
    private final DataValidator<List<OrderEntity>> validator;
    private final Converter<List<OrderEntity>, List<Order>> converter;

    public OrderDataProcessorImpl(OrderDataFactory dataFactory) {
        this.dataLoader = dataFactory.createDataLoader();
        this.validator = dataFactory.createValidator();
        this.converter = dataFactory.createConverter();
    }

    @Override
    public List<Order> process() {
        var loadedData = dataLoader.load();
        var validatedData = validator.validate(loadedData);
        return converter.convert(validatedData);
    }
}
