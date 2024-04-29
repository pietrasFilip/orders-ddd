package com.app.application.service.orders;

import com.app.application.config.OrdersServiceConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DataJpaTest
@Import(OrdersServiceConfig.class)
@TestPropertySource(
        locations = "classpath:application-service-test.properties",
        properties = "repository.path.json.orders=src/test/java/com/app/application/service/orders/data/orders_different_dates.json"
)
class GetMonthsWithNumberOfBoughtProductsTest {

    @Autowired
    private OrdersService ordersService;

    @TestFactory
    @DisplayName("When is not sorted descending by number of products")
    Stream<DynamicTest> test1() {
        var expected = new LinkedHashMap<String, Integer>();
        expected.put("MAY", 15);
        expected.put("FEBRUARY", 12);

        return Stream.of(ordersService.getMonthsWithNumberOfBoughtProductsSortedDescending())
                .map(n -> dynamicTest("Is equal to expected",
                        () -> assertThat(n).containsExactlyEntriesOf(expected)));
    }
}
