package com.app.domain.policy.abstract_factory.loader.txt;

import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.domain.policy.abstract_factory.loader.CustomerDataLoader;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomerDataLoaderTxtImpl implements CustomerDataLoader {
    private final CustomerRepositoryTxt customerRepository;

    @Override
    public List<CustomerEntity> load() {
        return customerRepository.findAll();
    }
}
