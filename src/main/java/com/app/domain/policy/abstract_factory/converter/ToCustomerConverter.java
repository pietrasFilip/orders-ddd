package com.app.domain.policy.abstract_factory.converter;

import com.app.domain.customer_management.model.Customer;
import com.app.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;

public interface ToCustomerConverter extends Converter<List<CustomerEntity>, List<Customer>> {
}
