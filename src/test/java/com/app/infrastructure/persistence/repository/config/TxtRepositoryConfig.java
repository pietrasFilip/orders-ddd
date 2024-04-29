package com.app.infrastructure.persistence.repository.config;

import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.domain.orders_management.model.repository.OrderRepositoryTxt;
import com.app.domain.product_management.model.repository.ProductRepositoryTxt;
import com.app.infrastructure.persistence.repository.txt.CustomerRepositoryTxtImpl;
import com.app.infrastructure.persistence.repository.txt.OrderRepositoryTxtImpl;
import com.app.infrastructure.persistence.repository.txt.ProductRepositoryTxtImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TxtRepositoryConfig {
    @Bean
    public CustomerRepositoryTxt customerRepositoryTxt() {
        return new CustomerRepositoryTxtImpl();
    }

    @Bean
    public ProductRepositoryTxt productRepositoryTxt() {
        return new ProductRepositoryTxtImpl();
    }

    @Bean
    public OrderRepositoryTxt orderRepositoryTxt() {
        return new OrderRepositoryTxtImpl(customerRepositoryTxt(), productRepositoryTxt());
    }
}
