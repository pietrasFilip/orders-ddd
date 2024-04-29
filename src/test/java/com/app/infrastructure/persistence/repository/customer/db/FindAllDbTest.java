package com.app.infrastructure.persistence.repository.customer.db;

import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.repository.config.DbRepositoryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbRepositoryConfig.class)
class FindAllDbTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepositoryDb customerRepositoryDb;

    @Test
    @DisplayName("When there are customers in database")
    void test1() {
        var customer1 = CustomerEntity
                .builder()
                .name("A")
                .surname("A")
                .age(25)
                .email("a@gmail.com")
                .build();

        var customer2 = CustomerEntity
                .builder()
                .name("B")
                .surname("B")
                .age(23)
                .email("b@gmail.com")
                .build();

        testEntityManager.persist(customer1);
        testEntityManager.persist(customer2);
        testEntityManager.flush();

        var foundCustomers = customerRepositoryDb.findAll();
        var expectedCustomers = List.of(customer1, customer2);

        assertThat(foundCustomers)
                .isInstanceOf(List.class)
                .isEqualTo(expectedCustomers);
    }

    @Test
    @DisplayName("When there are no customers in database")
    void test2() {
        var foundCustomers = customerRepositoryDb.findAll();

        assertThat(foundCustomers)
                .isInstanceOf(List.class)
                .isEmpty();
    }
}
