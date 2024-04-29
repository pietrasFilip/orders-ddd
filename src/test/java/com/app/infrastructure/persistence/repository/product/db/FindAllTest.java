package com.app.infrastructure.persistence.repository.product.db;

import com.app.domain.product_management.model.repository.ProductRepositoryDb;
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

import static com.app.domain.product_management.model.product_category.Category.A;
import static com.app.domain.product_management.model.product_category.Category.B;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbRepositoryConfig.class)
class FindAllTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepositoryDb productRepositoryDb;

    @Test
    @DisplayName("When there are products in database")
    void test1() {
        var product1 = ProductEntity
                .builder()
                .name("A")
                .price(BigDecimal.valueOf(10))
                .category(A)
                .build();

        var product2 = ProductEntity
                .builder()
                .name("B")
                .price(BigDecimal.valueOf(20))
                .category(B)
                .build();

        testEntityManager.persist(product1);
        testEntityManager.persist(product2);
        testEntityManager.flush();

        var foundProducts = productRepositoryDb.findAll();
        var expectedProducts = List.of(product1, product2);

        assertThat(foundProducts)
                .isInstanceOf(List.class)
                .isEqualTo(expectedProducts);
    }

    @Test
    @DisplayName("When there are no products in database")
    void test2() {
        var foundProducts = productRepositoryDb.findAll();

        assertThat(foundProducts)
                .isInstanceOf(List.class)
                .isEmpty();
    }
}
