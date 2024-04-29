package com.app.application.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateNullOrEmptyTest {

    @Test
    @DisplayName("When object is null")
    void test1() {
        assertThatThrownBy(() -> Validator.validateNullOrEmpty(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Is null");
    }

    @Test
    @DisplayName("When object is empty")
    void test2() {
        assertThatThrownBy(() -> Validator.validateNullOrEmpty(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Is empty");
    }
}
