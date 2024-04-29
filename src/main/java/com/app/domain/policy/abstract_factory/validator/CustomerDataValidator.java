package com.app.domain.policy.abstract_factory.validator;

import com.app.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;

public interface CustomerDataValidator extends DataValidator<List<CustomerEntity>>{
    CustomerEntity validateSingleCustomer(CustomerEntity customerEntity);
}
