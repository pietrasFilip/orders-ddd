package com.app.domain.policy.abstract_factory.factory;

import com.app.domain.product_management.model.Product;
import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductDataFactory extends DataFactory<List<ProductEntity>, List<Product>> {
}
