package com.app.domain.policy.abstract_factory.loader.txt;

import com.app.domain.policy.abstract_factory.loader.ProductDataLoader;
import com.app.domain.product_management.model.repository.ProductRepositoryTxt;
import com.app.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductDataLoaderTxtImpl implements ProductDataLoader {
    private final ProductRepositoryTxt productRepository;
    @Override
    public List<ProductEntity> load() {
        return productRepository.findAll();
    }
}
