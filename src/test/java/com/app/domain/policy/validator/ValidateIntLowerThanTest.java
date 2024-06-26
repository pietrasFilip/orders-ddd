package com.app.domain.policy.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateIntLowerThan;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateIntLowerThanTest {

    static Stream<Arguments> argSource() {
        return Stream.of(
                Arguments.of(20, 21), Arguments.of(89, 200), Arguments.of(1, 2), Arguments.of(4, 100)
        );
    }
    @ParameterizedTest
    @MethodSource("argSource")
    @DisplayName("When value is lower than min value")
    void test1(int value, int minValue) {
        assertThatThrownBy(() -> validateIntLowerThan(value, minValue))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Value is lower than min value");
    }
}
