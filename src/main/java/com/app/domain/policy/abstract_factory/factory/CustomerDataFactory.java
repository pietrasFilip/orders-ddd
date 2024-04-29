package com.app.domain.policy.abstract_factory.factory;

import com.app.domain.customer_management.model.Customer;
import com.app.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;

public interface CustomerDataFactory extends DataFactory<List<CustomerEntity>, List<Customer>> {
}
