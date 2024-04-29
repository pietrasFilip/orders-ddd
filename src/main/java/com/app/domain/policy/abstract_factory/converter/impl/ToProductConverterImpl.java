package com.app.domain.policy.abstract_factory.converter.impl;

import com.app.domain.policy.abstract_factory.converter.ToProductConverter;
import com.app.domain.product_management.model.Product;
import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public class ToProductConverterImpl implements ToProductConverter {
    @Override
    public List<Product> convert(List<ProductEntity> data) {
        return data
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }
}
