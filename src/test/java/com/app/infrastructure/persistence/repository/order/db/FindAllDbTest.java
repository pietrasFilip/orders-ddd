package com.app.infrastructure.persistence.repository.order.db;

import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.app.infrastructure.persistence.entity.ProductEntity;
import com.app.infrastructure.persistence.repository.config.DbRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static com.app.domain.product_management.model.product_category.Category.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbRepositoryConfig.class)
class FindAllDbTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderRepositoryDb orderRepositoryDb;

    @Test
    @DisplayName("When there are customers in database")
    void test1() {
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

        var foundOrders = orderRepositoryDb.findAll();
        var expectedOrders = List.of(order1, order2);

        assertThat(foundOrders)
                .isInstanceOf(List.class)
                .isEqualTo(expectedOrders);
    }

    @Test
    @DisplayName("When there are no customers in database")
    void test2() {
        var foundOrders = orderRepositoryDb.findAll();

        assertThat(foundOrders)
                .isInstanceOf(List.class)
                .isEmpty();
    }
}
