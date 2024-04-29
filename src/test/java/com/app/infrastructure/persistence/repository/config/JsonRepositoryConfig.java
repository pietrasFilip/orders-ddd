package com.app.infrastructure.persistence.repository.config;

import com.app.domain.customer_management.model.repository.CustomerRepositoryJson;
import com.app.domain.orders_management.model.repository.OrderRepositoryJson;
import com.app.domain.product_management.model.repository.ProductRepositoryJson;
import com.app.infrastructure.persistence.repository.json.CustomerRepositoryJsonImpl;
import com.app.infrastructure.persistence.repository.json.OrderRepositoryJsonImpl;
import com.app.infrastructure.persistence.repository.json.ProductRepositoryJsonImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class JsonRepositoryConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().enableComplexMapKeySerialization().create();
    }
    @Bean
    public CustomerRepositoryJson customerRepositoryJson() {
        return new CustomerRepositoryJsonImpl(gson());
    }

    @Bean
    public ProductRepositoryJson productRepositoryJson() {
        return new ProductRepositoryJsonImpl(gson());
    }

    @Bean
    public OrderRepositoryJson orderRepositoryJson() {
        return new OrderRepositoryJsonImpl(gson(), customerRepositoryJson(), productRepositoryJson());
    }
}
