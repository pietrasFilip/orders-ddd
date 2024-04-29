package com.app.domain.policy.abstract_factory.validator.impl;

import com.app.domain.policy.abstract_factory.validator.ProductDataValidator;
import com.app.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.app.domain.policy.abstract_factory.validator.DataValidator.*;

@RequiredArgsConstructor
public class ProductDataValidatorImpl implements ProductDataValidator {
    @Value("${validator.product.name.regex}")
    private String productNameRegex;
    @Override
    public List<ProductEntity> validate(List<ProductEntity> productEntities) {
        return productEntities
                .stream()
                .map(this::validateSingleProduct)
                .toList();
    }

    @Override
    public ProductEntity validateSingleProduct(ProductEntity productEntity) {
        return ProductEntity
                .builder()
                .id(productEntity.getId())
                .name(validateMatchesRegex(productNameRegex, productEntity.getName()))
                .price(validatePositiveDecimal(productEntity.getPrice()))
                .category(validateNull(productEntity.getCategory()))
                .build();
    }
}
