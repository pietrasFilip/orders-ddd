package com.app.domain.customer_management.model.repository;

import com.app.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;

public interface CustomerRepositoryTxt {
    List<CustomerEntity> findAll();
    CustomerEntity findById(Long id);
}
