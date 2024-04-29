package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import com.app.application.dto.customer.GetCustomerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class GetCustomersThatOrderedExactlyOrMoreThanTest {
    @Autowired
    private OrdersService ordersService;

    static Stream<Arguments> argSource() {
        return Stream.of(
                Arguments.of(-1), Arguments.of(-5), Arguments.of(-200), Arguments.of(-10)
                );
    }

    @ParameterizedTest
    @MethodSource("argSource")
    @DisplayName("When number of ordered items is negative")
    void test1(int quantity) {
        assertThatThrownBy(() -> ordersService.getCustomersThatOrderedExactlyOrMoreThan(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product quantity is negative number");
    }

    @TestFactory
    @DisplayName("When there is no customer that meets requirements")
    Stream<DynamicTest> test2() {
        var quantity = 12;
        var expected = Collections.<GetCustomerDto>emptySet();

        assertThat(ordersService.getCustomersThatOrderedExactlyOrMoreThan(quantity))
                .isEqualTo(expected);
        return Stream.of(ordersService.getCustomersThatOrderedExactlyOrMoreThan(quantity))
                .map(n -> dynamicTest("Returns empty set", () -> assertThat(n).isEqualTo(expected)));
    }

    @TestFactory
    @DisplayName("When there are wrong customers")
    Stream<DynamicTest> test3() {
        var quantity = 3;
        var expected = Set.of(
                new GetCustomerDto(1L, "A", "A"),
                new GetCustomerDto(3L, "C", "C"),
                new GetCustomerDto(4L, "D", "D")
        );

        return Stream.of(ordersService.getCustomersThatOrderedExactlyOrMoreThan(quantity))
                .map(n -> dynamicTest("Is equal to expected", () -> assertThat(n).isEqualTo(expected)));
    }
}
