package com.app.domain.product_management.model.repository;

import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductRepositoryTxt {
    List<ProductEntity> findAll();
    ProductEntity findById(Long id);
}
