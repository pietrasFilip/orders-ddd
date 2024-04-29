package com.app.infrastructure.persistence.repository.config;

import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import com.app.domain.product_management.model.repository.ProductRepositoryDb;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.domain.user_management.model.repository.VerificationTokenRepository;
import com.app.infrastructure.persistence.repository.db.*;
import com.app.infrastructure.persistence.repository.db.dao.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DbRepositoryConfig {
    @Bean
    public CustomerRepositoryDb customerRepositoryDb(CustomerEntityDao customerEntityDao) {
        return new CustomerRepositoryDbImpl(customerEntityDao);
    }

    @Bean
    public OrderRepositoryDb orderRepositoryDb(OrderEntityDao orderEntityDao) {
        return new OrderRepositoryDbImpl(orderEntityDao);
    }

    @Bean
    public ProductRepositoryDb productRepositoryDb(ProductEntityDao productEntityDao) {
        return new ProductRepositoryDbImpl(productEntityDao);
    }

    @Bean
    public UserRepository userRepository(UserEntityDao userEntityDao) {
        return new UserRepositoryDbImpl(userEntityDao);
    }

    @Bean
    public VerificationTokenRepository verificationTokenRepository(VerificationTokenEntityDao verificationTokenEntityDao) {
        return new VerificationTokenRepositoryDbImpl(verificationTokenEntityDao);
    }
}
