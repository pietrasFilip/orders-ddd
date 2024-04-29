package com.app.domain.policy.abstract_factory.converter;

public interface Converter<T, U> {
    U convert(T data);
}
