package com.app.infrastructure.persistence.repository.customer.json;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.customer_management.model.repository.CustomerRepositoryJson;
import com.app.infrastructure.persistence.repository.config.JsonRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(JsonRepositoryConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class FindByIdJsonTest {

    @Autowired
    private CustomerRepositoryJson customerRepositoryJson;

    @Test
    @DisplayName("When there is customer with given id")
    void test1() {
        var expectedCustomer = Customer
                .builder()
                .id(2L)
                .name("B")
                .surname("B")
                .age(23)
                .email("b@gmail.com")
                .build();

        assertThat(customerRepositoryJson.findById(2L).toDomain())
                .isEqualTo(expectedCustomer);
    }

    @Test
    @DisplayName("When there is no customer with given id")
    void test2() {
        assertThatThrownBy(() -> customerRepositoryJson.findById(10L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Customer with id not found");
    }
}
