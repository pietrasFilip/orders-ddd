package com.app.domain.policy.abstract_factory.loader.json;

import com.app.domain.customer_management.model.repository.CustomerRepositoryJson;
import com.app.domain.policy.abstract_factory.loader.CustomerDataLoader;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomerDataLoaderJsonImpl implements CustomerDataLoader {
    private final CustomerRepositoryJson customerRepository;

    @Override
    public List<CustomerEntity> load() {
        return customerRepository.findAll();
    }
}
