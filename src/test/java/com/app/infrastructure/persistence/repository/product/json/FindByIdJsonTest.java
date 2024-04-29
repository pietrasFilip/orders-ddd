package com.app.infrastructure.persistence.repository.product.json;

import com.app.domain.product_management.model.Product;
import com.app.domain.product_management.model.repository.ProductRepositoryJson;
import com.app.infrastructure.persistence.repository.config.JsonRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

import static com.app.domain.product_management.model.product_category.Category.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(JsonRepositoryConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class FindByIdJsonTest {

    @Autowired
    private ProductRepositoryJson productRepositoryJson;

    @Test
    @DisplayName("When there is product with given id")
    void test1() {
        var product = Product
                .builder()
                .id(1L)
                .name("A")
                .price(BigDecimal.valueOf(3600))
                .category(A)
                .build();

        var foundProduct = productRepositoryJson.findById(1L).toDomain();

        assertThat(foundProduct)
                .isEqualTo(product);
    }

    @Test
    @DisplayName("When there is no product with given id")
    void test2() {
        assertThatThrownBy(() -> productRepositoryJson.findById(5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product with id not found");
    }
}
