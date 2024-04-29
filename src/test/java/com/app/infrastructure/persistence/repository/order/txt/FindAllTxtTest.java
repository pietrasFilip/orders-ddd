package com.app.infrastructure.persistence.repository.order.txt;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.orders_management.model.Order;
import com.app.domain.orders_management.model.repository.OrderRepositoryTxt;
import com.app.domain.product_management.model.Product;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.app.infrastructure.persistence.repository.config.TxtRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static com.app.domain.product_management.model.product_category.Category.A;
import static com.app.domain.product_management.model.product_category.Category.B;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(TxtRepositoryConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class FindAllTxtTest {

    @Autowired
    private OrderRepositoryTxt orderRepositoryTxt;

    @Test
    @DisplayName("When there are orders in txt file")
    void test1() {
        var order1 = Order
                .builder()
                .id(1L)
                .customer(
                        Customer
                                .builder()
                                .id(1L)
                                .name("A")
                                .surname("A")
                                .age(25)
                                .email("a@gmail.com")
                                .build()
                )
                .product(
                        Product
                                .builder()
                                .id(1L)
                                .name("A")
                                .price(BigDecimal.valueOf(3600))
                                .category(A)
                                .build()
                )
                .quantity(2)
                .orderDate(ZonedDateTime.parse("2023-12-03T10:15:30+01:00[Europe/Warsaw]"))
                .build();

        var order2 = Order
                .builder()
                .id(2L)
                .customer(
                        Customer
                                .builder()
                                .id(2L)
                                .name("B")
                                .surname("B")
                                .age(23)
                                .email("b@gmail.com")
                                .build()
                )
                .product(
                        Product
                                .builder()
                                .id(2L)
                                .name("B")
                                .price(BigDecimal.valueOf(4500))
                                .category(B)
                                .build()
                )
                .quantity(1)
                .orderDate(ZonedDateTime.parse("2023-12-03T10:15:32+01:00[Europe/Warsaw]"))
                .build();

        var expectedOrders = List.of(order1, order2);
        var foundOrders = orderRepositoryTxt.findAll()
                .stream()
                .map(OrderEntity::toDomain)
                .toList();

        assertThat(foundOrders)
                .isInstanceOf(List.class)
                .isEqualTo(expectedOrders);
    }
}
