package com.app.domain.policy.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.app.domain.policy.abstract_factory.validator.DataValidator.validatePositiveDecimal;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatePositiveDecimalTest {

    static Stream<Arguments> argSource() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(-0.01)),
                Arguments.of(BigDecimal.valueOf(-10.01)),
                Arguments.of(BigDecimal.valueOf(-0.41)),
                Arguments.of(BigDecimal.valueOf(-110.98)),
                Arguments.of(BigDecimal.valueOf(-2000)));
    }
    @ParameterizedTest
    @MethodSource("argSource")
    @DisplayName("When its negative decimal")
    void test1(BigDecimal value) {
        assertThatThrownBy(() -> validatePositiveDecimal(value))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Value is zero or less");
    }
}
