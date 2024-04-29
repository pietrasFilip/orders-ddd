package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import com.app.application.dto.product.GetProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class GetProductsPurchasedByCustomerTest {
    @Autowired
    private OrdersService ordersService;

    @Test
    @DisplayName("When there is no customer with given mail")
    void test1() {
        assertThatThrownBy(() -> ordersService.getProductsPurchasedByCustomer("abcd@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No orders found");
    }

    @TestFactory
    @DisplayName("When customer purchased products")
    Stream<DynamicTest> test2() {
        var expected = Map.of(
                new GetProductDto(1L, "ZZ", BigDecimal.valueOf(20)), 5,
                new GetProductDto(3L, "WW", BigDecimal.valueOf(10)), 3
        );

        return Stream.of(ordersService.getProductsPurchasedByCustomer("a@gmail.com"))
                .map(n -> dynamicTest("Is equal to expected",
                        () -> assertThat(n).isEqualTo(expected)));
    }
}
