package com.app.domain.policy.abstract_factory.factory;

import com.app.domain.policy.abstract_factory.converter.Converter;
import com.app.domain.policy.abstract_factory.loader.DataLoader;
import com.app.domain.policy.abstract_factory.validator.DataValidator;

public interface DataFactory <T, U> {
    DataLoader<T> createDataLoader();
    DataValidator<T> createValidator();
    Converter<T, U> createConverter();
}
