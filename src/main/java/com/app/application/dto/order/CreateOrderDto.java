package com.app.application.dto.order;

import com.app.infrastructure.persistence.entity.CustomerEntity;

public record CreateOrderDto(
        String customerName,
        String surname,
        int age,
        String email,
        Long productId,
        int quantity
) {
    public CustomerEntity toCustomerEntity() {
        return CustomerEntity.builder()
                .name(customerName)
                .surname(surname)
                .age(age)
                .email(email)
                .build();
    }
}
