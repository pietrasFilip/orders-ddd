package com.app.domain.policy.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateMatchesRegex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateMatchesRegexTest {
    @Test
    @DisplayName("When model is null or empty")
    void test1() {
        var regex = "[A-Z]";
        var model = "";
        assertThatThrownBy(() -> validateMatchesRegex(regex, model))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Model is null or empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ala", "Ala", "aLa", "alA", "ala9", "9@lA"})
    @DisplayName("When model does not match regex")
    void test2(String model) {
        var regex = "[A-Z]";
        assertThatThrownBy(() -> validateMatchesRegex(regex, model))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Model does not match regex");
    }
}
