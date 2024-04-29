package com.app.domain.policy.abstract_factory.validator.impl;

import com.app.domain.policy.abstract_factory.validator.CustomerDataValidator;
import com.app.domain.policy.abstract_factory.validator.OrderDataValidator;
import com.app.domain.policy.abstract_factory.validator.ProductDataValidator;
import com.app.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateIntLowerThan;
import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateNull;

@RequiredArgsConstructor
public class OrderDataValidatorImpl implements OrderDataValidator {
    private final CustomerDataValidator customerDataValidator;
    private final ProductDataValidator productDataValidator;
    @Value("${validator.order.min.quantity}")
    private int minQuantity;
    @Override
    public List<OrderEntity> validate(List<OrderEntity> orderEntities) {
        return orderEntities
                .stream()
                .map(this::validateSingleOrder)
                .toList();
    }

    @Override
    public OrderEntity validateSingleOrder(OrderEntity orderEntity) {
        return OrderEntity
                .builder()
                .id(validateNull(orderEntity.getId()))
                .customer(customerDataValidator.validateSingleCustomer(orderEntity.getCustomer()))
                .product(productDataValidator.validateSingleProduct(orderEntity.getProduct()))
                .quantity(validateIntLowerThan(orderEntity.getQuantity(), minQuantity))
                .orderDate(orderEntity.getOrderDate())
                .build();
    }
}
