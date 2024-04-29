package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static com.app.application.service.Orders.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class GetOrdersPriceAfterDiscountTest {
    @Autowired
    private OrdersService ordersService;
    @Test
    @DisplayName("When total price is other than expected")
    void test1() {
        var expectedTotalPrice = Map.of(
                ORDER_A_1.toGetOrderDto(),BigDecimal.valueOf(98.00).setScale(2, RoundingMode.CEILING),
                ORDER_B.toGetOrderDto(),BigDecimal.valueOf(97.00).setScale(2, RoundingMode.CEILING),
                ORDER_A_2.toGetOrderDto(),BigDecimal.valueOf(29.40).setScale(2, RoundingMode.CEILING),
                ORDER_C.toGetOrderDto(),BigDecimal.valueOf(98.00).setScale(2, RoundingMode.CEILING),
                ORDER_D.toGetOrderDto(),BigDecimal.valueOf(39.20).setScale(2, RoundingMode.CEILING)
        );

        assertThat(ordersService.getOrdersPriceAfterDiscount())
                .containsExactlyInAnyOrderEntriesOf(expectedTotalPrice);
    }
}
