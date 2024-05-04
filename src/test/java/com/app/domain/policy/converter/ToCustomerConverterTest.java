package com.app.domain.policy.converter;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.policy.abstract_factory.converter.impl.ToCustomerConverterImpl;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ToCustomerConverterTest {

    @TestFactory
    @DisplayName("When there is conversion from CustomerEntity to Order")
    Stream<DynamicNode> test1() {
        var customerEntities = getCustomerEntities();

        var converter = new ToCustomerConverterImpl();
        return Stream.of(converter.convert(customerEntities))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of Customer",
                                        () -> n.forEach(o -> assertThat(o)
                                                .isInstanceOf(Customer.class))),
                                dynamicTest("Has size of 2",
                                        () -> assertThat(n).hasSize(2))
                        )
                ));
    }

    private List<CustomerEntity> getCustomerEntities() {
        var customer1 = CustomerEntity
                .builder()
                .id(1L)
                .name("A")
                .surname("A")
                .age(25)
                .email("a@gmail.com")
                .build();

        var customer2 = CustomerEntity
                .builder()
                .id(2L)
                .name("B")
                .surname("B")
                .age(25)
                .email("b@gmail.com")
                .build();

        return List.of(customer1, customer2);
    }
}
