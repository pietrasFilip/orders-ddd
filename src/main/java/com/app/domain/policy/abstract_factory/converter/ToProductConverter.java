package com.app.domain.policy.abstract_factory.converter;

import com.app.domain.product_management.model.Product;
import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public interface ToProductConverter extends Converter<List<ProductEntity>, List<Product>> {
}
