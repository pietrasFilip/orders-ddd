package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static com.app.domain.product_management.model.product_category.Category.A;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class GetMostBoughtCategoryTest {
    @Autowired
    private OrdersService ordersService;

    @Test
    @DisplayName("When there is wrong category")
    void test1() {
        var expected = Set.of(A);

        assertThat(ordersService.getMostBoughtCategory())
                .isEqualTo(expected);
    }
}
