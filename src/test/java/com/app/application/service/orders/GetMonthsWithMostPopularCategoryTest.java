package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.Set;

import static com.app.domain.product_management.model.product_category.Category.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource(
        locations = "classpath:application-service-test.properties",
        properties = "repository.path.json.orders=src/test/java/com/app/application/service/orders/data/orders_different_dates.json"
)
class GetMonthsWithMostPopularCategoryTest {
    @Autowired
    private OrdersService ordersService;

    @Test
    @DisplayName("When there is wrong category")
    void test1() {
        var expected = Map.of(
                "MAY", Set.of(A),
                "FEBRUARY", Set.of(A, B, C)
        );

        System.out.println("================");
        System.out.println(ordersService.getMonthsWithMostPopularCategory());
        System.out.println("================");

        assertThat(ordersService.getMonthsWithMostPopularCategory())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }
}
