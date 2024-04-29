package com.app.domain.policy.abstract_factory.converter.impl;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.policy.abstract_factory.converter.ToCustomerConverter;
import com.app.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;

public class ToCustomerConverterImpl implements ToCustomerConverter {
    @Override
    public List<Customer> convert(List<CustomerEntity> data) {
        return data
                .stream()
                .map(CustomerEntity::toDomain)
                .toList();
    }
}
