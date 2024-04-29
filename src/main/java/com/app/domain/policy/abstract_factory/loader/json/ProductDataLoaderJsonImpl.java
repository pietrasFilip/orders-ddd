package com.app.domain.policy.abstract_factory.loader.json;

import com.app.domain.policy.abstract_factory.loader.ProductDataLoader;
import com.app.domain.product_management.model.repository.ProductRepositoryJson;
import com.app.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductDataLoaderJsonImpl implements ProductDataLoader {
    private final ProductRepositoryJson productRepository;
    @Override
    public List<ProductEntity> load() {
        return productRepository.findAll();
    }
}
