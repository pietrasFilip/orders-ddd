package com.app.infrastructure.persistence.repository.customer.txt;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.repository.config.TxtRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(TxtRepositoryConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class FindAllTxtTest {
    @Autowired
    private CustomerRepositoryTxt customerRepositoryTxt;

    @Test
    @DisplayName("When there are customers from json file")
    void test1() {
        var customer1 = Customer
                .builder()
                .id(1L)
                .name("A")
                .surname("A")
                .age(25)
                .email("a@gmail.com")
                .build();

        var customer2 = Customer
                .builder()
                .id(2L)
                .name("B")
                .surname("B")
                .age(23)
                .email("b@gmail.com")
                .build();
        var expectedCustomers = List.of(customer1, customer2);

        var foundCustomers = customerRepositoryTxt.findAll()
                .stream()
                .map(CustomerEntity::toDomain)
                .toList();

        assertThat(foundCustomers)
                .isInstanceOf(List.class)
                .isEqualTo(expectedCustomers);
    }
}
