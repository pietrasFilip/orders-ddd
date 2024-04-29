package com.app.domain.policy.abstract_factory.factory.json;

import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.converter.impl.ToProductConverterImpl;
import com.app.domain.policy.abstract_factory.factory.ProductDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.loader.json.ProductDataLoaderJsonImpl;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.domain.policy.abstract_factory.validator.ProductDataValidator;
import com.app.domain.product_management.model.Product;
import com.app.domain.product_management.model.repository.ProductRepositoryJson;
import com.app.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromJsonToProduct implements ProductDataFactory {
    private final ProductRepositoryJson productRepositoryJson;
    private final ProductDataValidator productDataValidator;
    @Override
    public DataLoader<List<ProductEntity>> createDataLoader() {
        return new ProductDataLoaderJsonImpl(productRepositoryJson);
    }

    @Override
    public DataValidator<List<ProductEntity>> createValidator() {
        return productDataValidator;
    }

    @Override
    public Converter<List<ProductEntity>, List<Product>> createConverter() {
        return new ToProductConverterImpl();
    }
}
