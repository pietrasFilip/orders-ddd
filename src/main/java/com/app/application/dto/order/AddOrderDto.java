package com.app.application.dto.order;

import com.app.infrastructure.persistence.entity.OrderEntity;

public record AddOrderDto(OrderEntity orderEntity) {
}
