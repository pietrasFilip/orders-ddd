package com.app.infrastructure.config;

import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.domain.customer_management.model.repository.CustomerRepositoryJson;
import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import com.app.domain.orders_management.model.repository.OrderRepositoryJson;
import com.app.domain.orders_management.model.repository.OrderRepositoryTxt;
import com.app.domain.policy.abstract_factory.factory.CustomerDataFactory;
import com.app.domain.policy.abstract_factory.factory.OrderDataFactory;
import com.app.domain.policy.abstract_factory.factory.ProductDataFactory;
import com.app.domain.policy.abstract_factory.factory.db.FromDbToCustomer;
import com.app.domain.policy.abstract_factory.factory.db.FromDbToOrder;
import com.app.domain.policy.abstract_factory.factory.db.FromDbToProduct;
import com.app.domain.policy.abstract_factory.factory.json.FromJsonToCustomer;
import com.app.domain.policy.abstract_factory.factory.json.FromJsonToOrder;
import com.app.domain.policy.abstract_factory.factory.json.FromJsonToProduct;
import com.app.domain.policy.abstract_factory.factory.txt.FromTxtToCustomer;
import com.app.domain.policy.abstract_factory.factory.txt.FromTxtToOrder;
import com.app.domain.policy.abstract_factory.factory.txt.FromTxtToProduct;
import com.app.domain.policy.abstract_factory.processor.CustomerDataProcessor;
import com.app.domain.policy.abstract_factory.processor.OrderDataProcessor;
import com.app.domain.policy.abstract_factory.processor.ProductDataProcessor;
import com.app.domain.policy.abstract_factory.processor.impl.CustomerDataProcessorImpl;
import com.app.domain.policy.abstract_factory.processor.impl.OrderDataProcessorImpl;
import com.app.domain.policy.abstract_factory.processor.impl.ProductDataProcessorImpl;
import com.app.domain.policy.abstract_factory.validator.CustomerDataValidator;
import com.app.domain.policy.abstract_factory.validator.OrderDataValidator;
import com.app.domain.policy.abstract_factory.validator.ProductDataValidator;
import com.app.domain.policy.abstract_factory.validator.impl.CustomerDataValidatorImpl;
import com.app.domain.policy.abstract_factory.validator.impl.OrderDataValidatorImpl;
import com.app.domain.policy.abstract_factory.validator.impl.ProductDataValidatorImpl;
import com.app.domain.product_management.model.repository.ProductRepositoryDb;
import com.app.domain.product_management.model.repository.ProductRepositoryJson;
import com.app.domain.product_management.model.repository.ProductRepositoryTxt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecretKey secretKey() {
        return Jwts.SIG.HS512.key().build();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().enableComplexMapKeySerialization().create();
    }

    // ========================================================================
    // =========================== ABSTRACT_FACTORY ===========================
    // ========================================================================

    @Bean
    public CustomerDataValidator customerDataValidator() {
        return new CustomerDataValidatorImpl();
    }

    @Bean
    public ProductDataValidator productDataValidator() {
        return new ProductDataValidatorImpl();
    }

    // =========================== ORDER ===========================
    @Bean
    public OrderDataValidator orderDataValidator(CustomerDataValidator customerDataValidator,
                                                 ProductDataValidator productDataValidator) {
        return new OrderDataValidatorImpl(customerDataValidator, productDataValidator);
    }

    @Bean
    public OrderDataFactory fromDbToOrder(OrderRepositoryDb orderRepositoryDb,
                                          OrderDataValidator orderDataValidator) {
        return new FromDbToOrder(orderRepositoryDb, orderDataValidator);
    }

    @Bean
    public OrderDataFactory fromJsonToOrder(OrderRepositoryJson orderRepositoryJson,
                                            OrderDataValidator orderDataValidator) {
        return new FromJsonToOrder(orderRepositoryJson, orderDataValidator);
    }

    @Bean
    public OrderDataFactory fromTxtToOrder(OrderRepositoryTxt orderRepositoryTxt,
                                           OrderDataValidator orderDataValidator) {
        return new FromTxtToOrder(orderRepositoryTxt, orderDataValidator);
    }

    @Bean
    public OrderDataProcessor orderDataProcessorDb(OrderDataFactory fromDbToOrder) {
        return new OrderDataProcessorImpl(fromDbToOrder);
    }

    @Bean
    public OrderDataProcessor orderDataProcessorJson(OrderDataFactory fromJsonToOrder) {
        return new OrderDataProcessorImpl(fromJsonToOrder);
    }

    @Bean
    public OrderDataProcessor orderDataProcessorTxt(OrderDataFactory fromTxtToOrder) {
        return new OrderDataProcessorImpl(fromTxtToOrder);
    }

    // =========================== CUSTOMER ===========================

    @Bean
    public CustomerDataFactory fromDbToCustomer(CustomerRepositoryDb customerRepositoryDb, CustomerDataValidator customerDataValidator) {
        return new FromDbToCustomer(customerRepositoryDb, customerDataValidator);
    }

    @Bean
    public CustomerDataFactory fromJsonToCustomer(CustomerRepositoryJson customerRepositoryJson, CustomerDataValidator customerDataValidator) {
        return new FromJsonToCustomer(customerRepositoryJson, customerDataValidator);
    }

    @Bean
    public CustomerDataFactory fromTxtToCustomer(CustomerRepositoryTxt customerRepositoryTxt, CustomerDataValidator customerDataValidator) {
        return new FromTxtToCustomer(customerRepositoryTxt, customerDataValidator);
    }

    @Bean
    public CustomerDataProcessor customerDataProcessorDb(CustomerDataFactory fromDbToCustomer) {
        return new CustomerDataProcessorImpl(fromDbToCustomer);
    }

    @Bean
    public CustomerDataProcessor customerDataProcessorJson(CustomerDataFactory fromJsonToCustomer) {
        return new CustomerDataProcessorImpl(fromJsonToCustomer);
    }

    @Bean
    public CustomerDataProcessor customerDataProcessorTxt(CustomerDataFactory fromTxtToCustomer) {
        return new CustomerDataProcessorImpl(fromTxtToCustomer);
    }

    // =========================== PRODUCT ===========================

    @Bean
    public ProductDataFactory fromDbToProduct(ProductRepositoryDb productRepositoryDb, ProductDataValidator productDataValidator) {
        return new FromDbToProduct(productRepositoryDb, productDataValidator);
    }

    @Bean
    public ProductDataFactory fromJsonToProduct(ProductRepositoryJson productRepositoryJson, ProductDataValidator productDataValidator) {
        return new FromJsonToProduct(productRepositoryJson, productDataValidator);
    }

    @Bean
    public ProductDataFactory fromTxtToProduct(ProductRepositoryTxt productRepositoryTxt, ProductDataValidator productDataValidator) {
        return new FromTxtToProduct(productRepositoryTxt, productDataValidator);
    }

    @Bean
    public ProductDataProcessor productDataProcessorDb(ProductDataFactory fromDbToProduct) {
        return new ProductDataProcessorImpl(fromDbToProduct);
    }

    @Bean
    public ProductDataProcessor productDataProcessorJson(ProductDataFactory fromJsonToProduct) {
        return new ProductDataProcessorImpl(fromJsonToProduct);
    }

    @Bean
    public ProductDataProcessor productDataProcessorTxt(ProductDataFactory fromTxtToProduct) {
        return new ProductDataProcessorImpl(fromTxtToProduct);
    }

    // ========================================================================
    // ========================================================================
    // ========================================================================
}
