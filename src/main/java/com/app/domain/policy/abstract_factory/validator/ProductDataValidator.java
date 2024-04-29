package com.app.domain.policy.abstract_factory.validator;

import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductDataValidator extends DataValidator<List<ProductEntity>> {
    ProductEntity validateSingleProduct(ProductEntity productEntity);
}
