package com.app.domain.policy.converter;

import com.app.domain.policy.abstract_factory.converter.impl.ToProductConverterImpl;
import com.app.domain.product_management.model.Product;
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

class ToProductConverterTest {

    @TestFactory
    @DisplayName("When there is conversion from ProductEntity to Product")
    Stream<DynamicNode> test1() {
        var productEntities = getProductEntities();

        var converter = new ToProductConverterImpl();
        return Stream.of(converter.convert(productEntities))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of Product",
                                        () -> n.forEach(o -> assertThat(o)
                                                .isInstanceOf(Product.class))),
                                dynamicTest("Has size of 2",
                                        () -> assertThat(n).hasSize(2))
                        )
                ));
    }

    private List<ProductEntity> getProductEntities() {
        var product1 = ProductEntity
                .builder()
                .id(1L)
                .name("A")
                .price(BigDecimal.valueOf(10))
                .category(A)
                .build();

        var product2 = ProductEntity
                .builder()
                .id(2L)
                .name("B")
                .price(BigDecimal.valueOf(25))
                .category(B)
                .build();

        return List.of(product1, product2);
    }
}
