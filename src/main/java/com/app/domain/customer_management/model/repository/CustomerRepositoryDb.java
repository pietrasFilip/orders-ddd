package com.app.domain.customer_management.model.repository;

import com.app.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryDb {
    List<CustomerEntity> findAll();
    Optional<CustomerEntity> findByEmail(String email);
    CustomerEntity save(CustomerEntity customer);
}
