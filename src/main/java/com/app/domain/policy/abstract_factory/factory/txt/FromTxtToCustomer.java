package com.app.domain.policy.abstract_factory.factory.txt;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.converter.impl.ToCustomerConverterImpl;
import com.app.domain.policy.abstract_factory.factory.CustomerDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.loader.txt.CustomerDataLoaderTxtImpl;
import com.app.domain.policy.abstract_factory.validator.CustomerDataValidator;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromTxtToCustomer implements CustomerDataFactory {
    private final CustomerRepositoryTxt customerRepositoryTxt;
    private final CustomerDataValidator customerDataValidator;

    @Override
    public DataLoader<List<CustomerEntity>> createDataLoader() {
        return new CustomerDataLoaderTxtImpl(customerRepositoryTxt);
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
