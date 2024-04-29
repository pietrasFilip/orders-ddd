package com.app.infrastructure.persistence.repository.product.txt;

import com.app.domain.product_management.model.Product;
import com.app.domain.product_management.model.repository.ProductRepositoryTxt;
import com.app.infrastructure.persistence.entity.ProductEntity;
import com.app.infrastructure.persistence.repository.config.TxtRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;

import static com.app.domain.product_management.model.product_category.Category.A;
import static com.app.domain.product_management.model.product_category.Category.B;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(TxtRepositoryConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class FindAllTxtTest {

    @Autowired
    private ProductRepositoryTxt productRepositoryTxt;

    @Test
    @DisplayName("When there are products in txt file")
    void test1() {
        var product1 = Product
                .builder()
                .id(1L)
                .name("A")
                .price(BigDecimal.valueOf(3600))
                .category(A)
                .build();

        var product2 = Product
                .builder()
                .id(2L)
                .name("B")
                .price(BigDecimal.valueOf(4500))
                .category(B)
                .build();

        var expectedProducts = List.of(product1, product2);
        var foundProducts = productRepositoryTxt.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .toList();

        assertThat(foundProducts)
                .isInstanceOf(List.class)
                .isEqualTo(expectedProducts);
    }
}
