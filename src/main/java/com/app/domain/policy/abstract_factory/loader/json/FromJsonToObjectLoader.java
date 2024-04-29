package com.app.domain.policy.abstract_factory.loader.json;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Service
@RequiredArgsConstructor
public abstract class FromJsonToObjectLoader<T> {
    private final Gson gson;
    private final Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private static final Logger logger = LogManager.getRootLogger();

    public T loadObject(String path) {
        try(var fileReader = new FileReader(path)) {
            return gson.fromJson(fileReader, type);
        } catch (Exception e) {
            logger.error("From JSON convert failed");
            throw new IllegalStateException(e.getMessage());
        }
    }
}
