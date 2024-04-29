package com.app.infrastructure.persistence.entity;

import com.app.application.dto.order.GetOrderDto;
import com.app.domain.orders_management.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private int quantity;
    private String orderDate;

    public Order toDomain() {
        return Order
                .builder()
                .id(getId())
                .customer(customer.toDomain())
                .product(product.toDomain())
                .quantity(quantity)
                .orderDate(ZonedDateTime.parse(orderDate))
                .build();
    }

    public GetOrderDto toGetOrderDto() {
        return new GetOrderDto(customer.toGetCustomerDto(), product.toGetProductDto(), quantity, orderDate);
    }
}
