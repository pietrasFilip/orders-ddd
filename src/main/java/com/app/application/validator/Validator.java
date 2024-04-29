package com.app.application.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Validator<T> {
    Logger logger = LogManager.getRootLogger();
    void validate (T t);

    static <T> T validateNullOrEmpty(T t) {
        if (t == null) {
            logger.error("Is null");
            throw new IllegalArgumentException("Is null");
        }

        if (t.toString().isEmpty()) {
            logger.error("Is empty");
            throw new IllegalArgumentException("Is empty");
        }

        return t;
    }
}
