package com.app.domain.policy.converter;

import com.app.domain.orders_management.model.Order;
import com.app.domain.policy.abstract_factory.converter.impl.ToOrderConverterImpl;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.app.infrastructure.persistence.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static com.app.domain.product_management.model.product_category.Category.A;
import static com.app.domain.product_management.model.product_category.Category.B;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ToOrderConverterTest {

    @TestFactory
    @DisplayName("When there is conversion from OrderEntity to Order")
    Stream<DynamicNode> test1() {
        var orderEntities = getOrderEntities();

        var converter = new ToOrderConverterImpl();
        return Stream.of(converter.convert(orderEntities))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of Order",
                                        () -> n.forEach(o -> assertThat(o)
                                                .isInstanceOf(Order.class))),
                                dynamicTest("Has size of 2",
                                        () -> assertThat(n).hasSize(2))
                        )
                ));
    }

    private List<OrderEntity> getOrderEntities() {
        var order1 = OrderEntity
                .builder()
                .id(1L)
                .customer(CustomerEntity
                        .builder()
                        .id(1L)
                        .name("A")
                        .surname("A")
                        .age(25)
                        .email("a@gmail.com")
                        .build())
                .product(ProductEntity
                        .builder()
                        .id(1L)
                        .name("A")
                        .price(BigDecimal.valueOf(10))
                        .category(A)
                        .build())
                .quantity(2)
                .orderDate("2023-12-03T10:15:32+01:00[Europe/Warsaw]")
                .build();

        var order2 = OrderEntity
                .builder()
                .id(2L)
                .customer(CustomerEntity
                        .builder()
                        .id(2L)
                        .name("B")
                        .surname("B")
                        .age(25)
                        .email("b@gmail.com")
                        .build())
                .product(ProductEntity
                        .builder()
                        .id(2L)
                        .name("B")
                        .price(BigDecimal.valueOf(25))
                        .category(B)
                        .build())
                .quantity(1)
                .orderDate("2023-12-03T10:15:36+01:00[Europe/Warsaw]")
                .build();
        return List.of(order1, order2);
    }
}
