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
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static com.app.domain.product_management.model.product_category.Category.A;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbRepositoryConfig.class)
class SaveDbTest {

    @Autowired
    private OrderRepositoryDb orderRepositoryDb;

    @Test
    @DisplayName("When order is saved to db")
    void test1() {
        var order = OrderEntity
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

        var savedOrder = orderRepositoryDb.save(order);
        assertThat(orderRepositoryDb.findAll())
                .contains(savedOrder);
    }
}
