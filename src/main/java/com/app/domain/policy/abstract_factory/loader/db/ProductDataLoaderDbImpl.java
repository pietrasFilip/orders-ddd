package com.app.domain.policy.abstract_factory.loader.db;

import com.app.domain.policy.abstract_factory.loader.ProductDataLoader;
import com.app.domain.product_management.model.repository.ProductRepositoryDb;
import com.app.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductDataLoaderDbImpl implements ProductDataLoader {
    private final ProductRepositoryDb productRepository;
    @Override
    public List<ProductEntity> load() {
        return productRepository.findAll();
    }
}
