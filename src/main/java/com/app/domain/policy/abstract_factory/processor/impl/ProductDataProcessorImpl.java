package com.app.domain.policy.abstract_factory.processor.impl;

import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.factory.ProductDataFactory;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.processor.ProductDataProcessor;
import com.app.domain.policy.abstract_factory.validator.DataValidator;
import com.app.domain.product_management.model.Product;
import com.app.infrastructure.persistence.entity.ProductEntity;

import java.util.List;

public class ProductDataProcessorImpl implements ProductDataProcessor {
    private final DataLoader<List<ProductEntity>> dataLoader;
    private final DataValidator<List<ProductEntity>> validator;
    private final Converter<List<ProductEntity>, List<Product>> converter;

    public ProductDataProcessorImpl(ProductDataFactory dataFactory) {
        this.dataLoader = dataFactory.createDataLoader();
        this.validator = dataFactory.createValidator();
        this.converter = dataFactory.createConverter();
    }

    @Override
    public List<Product> process() {
        var loadedData = dataLoader.load();
        var validatedData = validator.validate(loadedData);
        return converter.convert(validatedData);
    }
}
