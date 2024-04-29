package com.app.domain.product_management.model.repository;

import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryDb {
    List<ProductEntity> findAll();
    Optional<ProductEntity> findById(Long id);
}
