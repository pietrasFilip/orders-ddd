package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class GetDateOfMinAndMaxNumberOfOrdersTest {
    @Autowired
    private OrdersService ordersService;

    @Test
    @DisplayName("When is min date")
    void test1() {
        var expected = List.of(LocalDate.of(2023, 2, 20));

        assertThat(ordersService.getDateOfMinAndMaxNumberOfOrders(true))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When is max date")
    void test2() {
        var expected = List.of(LocalDate.of(2023, 2, 22));

        assertThat(ordersService.getDateOfMinAndMaxNumberOfOrders(false))
                .isEqualTo(expected);
    }
}
