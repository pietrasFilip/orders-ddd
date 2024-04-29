package com.app.domain.policy.abstract_factory.validator;

import com.app.infrastructure.persistence.entity.OrderEntity;

import java.util.List;

public interface OrderDataValidator extends DataValidator<List<OrderEntity>> {
    OrderEntity validateSingleOrder(OrderEntity orderEntity);
}
