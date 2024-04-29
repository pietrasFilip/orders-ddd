package com.app.infrastructure.persistence.repository.customer.txt;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.infrastructure.persistence.repository.config.TxtRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(TxtRepositoryConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class FindByIdTxtTest {

    @Autowired
    private CustomerRepositoryTxt customerRepositoryTxt;

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

        assertThat(customerRepositoryTxt.findById(2L).toDomain())
                .isEqualTo(expectedCustomer);
    }

    @Test
    @DisplayName("When there is no customer with given id")
    void test2() {
        assertThatThrownBy(() -> customerRepositoryTxt.findById(10L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Customer with id not found");
    }
}
