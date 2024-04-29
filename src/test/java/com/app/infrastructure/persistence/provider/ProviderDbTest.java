package com.app.infrastructure.persistence.provider;

import com.app.domain.orders_management.model.Order;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.app.infrastructure.persistence.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static com.app.domain.product_management.model.product_category.Category.A;
import static com.app.domain.product_management.model.product_category.Category.B;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ProviderConfig.class)
@TestPropertySource("classpath:application-test.properties")
class ProviderDbTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrdersProvider ordersProvider;

    @Test
    @DisplayName("When processor.orders.type is db")
    void test1() {
        var expectedOrders = loadToDbAndGetExpected();
        var loadedOrders = ordersProvider.provide();

        assertThat(loadedOrders)
                .isInstanceOf(List.class)
                .isEqualTo(expectedOrders);
    }

    private List<Order> loadToDbAndGetExpected() {
        var order1 = OrderEntity
                .builder()
                .customer(CustomerEntity
                        .builder()
                        .name("A")
                        .surname("A")
                        .age(25)
                        .email("a@gmail.com")
                        .build())
                .product(ProductEntity
                        .builder()
                        .name("A")
                        .price(BigDecimal.valueOf(10))
                        .category(A)
                        .build())
                .quantity(2)
                .orderDate("2023-12-03T10:15:32+01:00[Europe/Warsaw]")
                .build();

        var order2 = OrderEntity
                .builder()
                .customer(CustomerEntity
                        .builder()
                        .name("B")
                        .surname("B")
                        .age(25)
                        .email("b@gmail.com")
                        .build())
                .product(ProductEntity
                        .builder()
                        .name("B")
                        .price(BigDecimal.valueOf(25))
                        .category(B)
                        .build())
                .quantity(1)
                .orderDate("2023-12-03T10:15:36+01:00[Europe/Warsaw]")
                .build();

        testEntityManager.persist(order1);
        testEntityManager.persist(order2);
        testEntityManager.flush();

        return List.of(order1.toDomain(), order2.toDomain());
    }
}
