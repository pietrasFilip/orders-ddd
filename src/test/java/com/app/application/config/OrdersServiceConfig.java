package com.app.application.config;

import com.app.application.service.orders.OrdersService;
import com.app.application.service.orders.OrdersServiceImpl;
import com.app.application.validator.CreateOrderDtoValidator;
import com.app.application.validator.impl.CreateOrderDtoValidatorImpl;
import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.domain.product_management.model.repository.ProductRepositoryDb;
import com.app.infrastructure.persistence.provider.OrdersProvider;
import com.app.infrastructure.persistence.provider.ProviderConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(ProviderConfig.class)
public class OrdersServiceConfig {

    @Bean
    public CreateOrderDtoValidator createOrderDtoValidator() {
        return new CreateOrderDtoValidatorImpl();
    }

    @Bean
    public OrdersService ordersService(OrdersProvider ordersProvider, CustomerRepositoryDb customerRepositoryDb,
                                       ProductRepositoryDb productRepositoryDb, CreateOrderDtoValidator createOrderDtoValidator,
                                       ApplicationEventPublisher applicationEventPublisher) {
        return new OrdersServiceImpl(ordersProvider, customerRepositoryDb, productRepositoryDb, createOrderDtoValidator, applicationEventPublisher);
    }
}
