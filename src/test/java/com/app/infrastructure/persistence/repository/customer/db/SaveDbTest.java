package com.app.infrastructure.persistence.repository.customer.db;

import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.repository.config.DbRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbRepositoryConfig.class)
class SaveDbTest {

    @Autowired
    private CustomerRepositoryDb customerRepositoryDb;

    @Test
    @DisplayName("When customer is saved to db")
    void test1() {
        var customer = CustomerEntity
                .builder()
                .name("A")
                .surname("A")
                .age(25)
                .email("a@gmail.com")
                .build();

        var savedCustomer = customerRepositoryDb.save(customer);
        assertThat(customerRepositoryDb.findAll())
                .contains(savedCustomer);
    }
}
