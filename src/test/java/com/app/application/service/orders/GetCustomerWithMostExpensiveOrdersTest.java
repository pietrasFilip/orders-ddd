package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import com.app.application.dto.customer.GetCustomerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class GetCustomerWithMostExpensiveOrdersTest {
    @Autowired
    private OrdersService ordersService;

    @Test
    @DisplayName("When customer does not have most expensive orders")
    void test1() {
        var expected = List.of(new GetCustomerDto(1L, "A", "A"));
        assertThat(ordersService.getCustomerWithMostExpensiveOrders())
                .isEqualTo(expected);
    }
}
