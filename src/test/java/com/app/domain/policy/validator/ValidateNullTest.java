package com.app.domain.policy.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateNullTest {
    @Test
    @DisplayName("When Category is null")
    void test1() {
        assertThatThrownBy(() -> validateNull(null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Is null");
    }
}
