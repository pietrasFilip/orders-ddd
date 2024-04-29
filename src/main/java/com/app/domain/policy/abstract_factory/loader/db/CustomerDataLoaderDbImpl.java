package com.app.domain.policy.abstract_factory.loader.db;

import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.domain.policy.abstract_factory.loader.CustomerDataLoader;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomerDataLoaderDbImpl implements CustomerDataLoader {
    private final CustomerRepositoryDb customerRepository;
    @Override
    public List<CustomerEntity> load() {
        return customerRepository.findAll();
    }
}
