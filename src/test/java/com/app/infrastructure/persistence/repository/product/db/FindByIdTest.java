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
import java.util.Optional;

import static com.app.domain.product_management.model.product_category.Category.A;
import static com.app.domain.product_management.model.product_category.Category.B;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbRepositoryConfig.class)
class FindByIdTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepositoryDb productRepositoryDb;

    @Test
    @DisplayName("When there is product with given id")
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
        var productToFind = testEntityManager.persist(product2);
        testEntityManager.flush();

        assertThat(productRepositoryDb.findById(productToFind.getId()))
                .isEqualTo(Optional.of(product2));
    }

    @Test
    @DisplayName("When product with given id is not found")
    void test2() {
        assertThat(productRepositoryDb.findById(1L))
                .isNotPresent();
    }
}
