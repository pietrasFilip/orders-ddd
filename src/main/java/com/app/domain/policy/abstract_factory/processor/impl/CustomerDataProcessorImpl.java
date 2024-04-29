package com.app.domain.policy.abstract_factory.processor.impl;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.factory.CustomerDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.processor.CustomerDataProcessor;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;

public class CustomerDataProcessorImpl implements CustomerDataProcessor {
    private final DataLoader<List<CustomerEntity>> dataLoader;
    private final DataValidator<List<CustomerEntity>> validator;
    private final Converter<List<CustomerEntity>, List<Customer>> converter;

    public CustomerDataProcessorImpl(CustomerDataFactory dataFactory) {
        this.dataLoader = dataFactory.createDataLoader();
        this.validator = dataFactory.createValidator();
        this.converter = dataFactory.createConverter();
    }

    @Override
    public List<Customer> process() {
        var loadedData = dataLoader.load();
        var validatedData = validator.validate(loadedData);
        return converter.convert(validatedData);
    }
}
