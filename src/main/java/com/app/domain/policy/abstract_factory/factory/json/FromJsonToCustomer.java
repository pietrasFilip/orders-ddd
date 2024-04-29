package com.app.domain.policy.abstract_factory.factory.json;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.customer_management.model.repository.CustomerRepositoryJson;
import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.converter.impl.ToCustomerConverterImpl;
import com.app.domain.policy.abstract_factory.factory.CustomerDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.loader.json.CustomerDataLoaderJsonImpl;
import com.app.domain.policy.abstract_factory.validator.CustomerDataValidator;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromJsonToCustomer implements CustomerDataFactory {
    private final CustomerRepositoryJson customerRepositoryJson;
    private final CustomerDataValidator customerDataValidator;

    @Override
    public DataLoader<List<CustomerEntity>> createDataLoader() {
        return new CustomerDataLoaderJsonImpl(customerRepositoryJson);
    }

    @Override
    public DataValidator<List<CustomerEntity>> createValidator() {
        return customerDataValidator;
    }

    @Override
    public Converter<List<CustomerEntity>, List<Customer>> createConverter() {
        return new ToCustomerConverterImpl();
    }
}
