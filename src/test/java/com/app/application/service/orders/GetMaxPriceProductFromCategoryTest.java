package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import com.app.application.dto.product.GetProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.app.domain.product_management.model.product_category.Category.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class GetMaxPriceProductFromCategoryTest {
    @Autowired
    private OrdersService ordersService;

    @Test
    @DisplayName("When there is no max price product")
    void test1() {
        var expected = Map.of(
                A, List.of(new GetProductDto(1L, "ZZ", BigDecimal.valueOf(20))),
                B, List.of(new GetProductDto(2L, "XX", BigDecimal.valueOf(100))),
                C, List.of(new GetProductDto(5L, "YY", BigDecimal.valueOf(5)))
                );

        assertThat(ordersService.getMaxPriceProductFromCategory())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }
}
