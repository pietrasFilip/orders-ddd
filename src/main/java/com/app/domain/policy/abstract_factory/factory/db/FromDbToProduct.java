package com.app.domain.policy.abstract_factory.factory.db;

import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.converter.impl.ToProductConverterImpl;
import com.app.domain.policy.abstract_factory.factory.ProductDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.loader.db.ProductDataLoaderDbImpl;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.domain.policy.abstract_factory.validator.ProductDataValidator;
import com.app.domain.product_management.model.Product;
import com.app.domain.product_management.model.repository.ProductRepositoryDb;
import com.app.infrastructure.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromDbToProduct implements ProductDataFactory {
    private final ProductRepositoryDb productRepositoryDb;
    private final ProductDataValidator productDataValidator;
    @Override
    public DataLoader<List<ProductEntity>> createDataLoader() {
        return new ProductDataLoaderDbImpl(productRepositoryDb);
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
