package com.app.infrastructure.persistence.provider;

import com.app.domain.orders_management.model.Order;
import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import com.app.domain.orders_management.model.repository.OrderRepositoryJson;
import com.app.domain.orders_management.model.repository.OrderRepositoryTxt;
import com.app.domain.policy.abstract_factory.factory.OrderDataFactory;
import com.app.domain.policy.abstract_factory.factory.db.FromDbToOrder;
import com.app.domain.policy.abstract_factory.factory.json.FromJsonToOrder;
import com.app.domain.policy.abstract_factory.factory.txt.FromTxtToOrder;
import com.app.domain.policy.abstract_factory.processor.OrderDataProcessor;
import com.app.domain.policy.abstract_factory.processor.impl.OrderDataProcessorImpl;
import com.app.domain.policy.abstract_factory.validator.CustomerDataValidator;
import com.app.domain.policy.abstract_factory.validator.OrderDataValidator;
import com.app.domain.policy.abstract_factory.validator.ProductDataValidator;
import com.app.domain.policy.abstract_factory.validator.impl.CustomerDataValidatorImpl;
import com.app.domain.policy.abstract_factory.validator.impl.OrderDataValidatorImpl;
import com.app.domain.policy.abstract_factory.validator.impl.ProductDataValidatorImpl;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.app.infrastructure.persistence.entity.ProductEntity;
import com.app.infrastructure.persistence.provider.impl.OrdersProviderImpl;
import com.app.infrastructure.persistence.repository.config.DbRepositoryConfig;
import com.app.infrastructure.persistence.repository.config.JsonRepositoryConfig;
import com.app.infrastructure.persistence.repository.config.TxtRepositoryConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static com.app.domain.product_management.model.product_category.Category.A;
import static com.app.domain.product_management.model.product_category.Category.B;

@TestConfiguration
@Import(value = {DbRepositoryConfig.class, JsonRepositoryConfig.class, TxtRepositoryConfig.class})
public class ProviderConfig {

    @Bean
    public ProductDataValidator productDataValidator() {
        return new ProductDataValidatorImpl();
    }

    @Bean
    public CustomerDataValidator customerDataValidator() {
        return new CustomerDataValidatorImpl();
    }

    @Bean
    public OrderDataValidator orderDataValidator() {
        return new OrderDataValidatorImpl(customerDataValidator(), productDataValidator());
    }

    @Bean
    public OrderDataFactory orderDataFactoryDb(OrderRepositoryDb orderRepositoryDb, OrderDataValidator orderDataValidator) {
        return new FromDbToOrder(orderRepositoryDb, orderDataValidator);
    }

    @Bean
    public OrderDataFactory orderDataFactoryJson(OrderRepositoryJson orderRepositoryJson, OrderDataValidator orderDataValidator) {
        return new FromJsonToOrder(orderRepositoryJson, orderDataValidator);
    }

    @Bean
    public OrderDataFactory orderDataFactoryTxt(OrderRepositoryTxt orderRepositoryTxt, OrderDataValidator orderDataValidator) {
        return new FromTxtToOrder(orderRepositoryTxt, orderDataValidator);
    }

    @Bean
    public OrderDataProcessor orderDataProcessorDb(OrderDataFactory orderDataFactoryDb) {
        return new OrderDataProcessorImpl(orderDataFactoryDb);
    }

    @Bean
    public OrderDataProcessor orderDataProcessorJson(OrderDataFactory orderDataFactoryJson) {
        return new OrderDataProcessorImpl(orderDataFactoryJson);
    }

    @Bean
    public OrderDataProcessor orderDataProcessorTxt(OrderDataFactory orderDataFactoryTxt) {
        return new OrderDataProcessorImpl(orderDataFactoryTxt);
    }

    @Bean
    public OrdersProvider ordersProvider(OrderDataProcessor orderDataProcessorDb, OrderDataProcessor orderDataProcessorJson,
                                         OrderDataProcessor orderDataProcessorTxt) {
        return new OrdersProviderImpl(orderDataProcessorDb, orderDataProcessorJson, orderDataProcessorTxt);
    }
}
