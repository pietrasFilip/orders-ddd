package com.app.application.service;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.orders_management.model.Order;
import com.app.domain.product_management.model.Product;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.app.domain.product_management.model.product_category.Category.*;

public interface Orders {
    Order ORDER_A_1 = Order
            .builder()
            .id(1L)
            .customer(Customer
                    .builder()
                    .id(1L)
                    .name("A")
                    .surname("A")
                    .age(28)
                    .email("a@gmail.com")
                    .build())
            .product(Product
                    .builder()
                    .id(1L)
                    .name("ZZ")
                    .price(BigDecimal.valueOf(20))
                    .category(A)
                    .build())
            .quantity(5)
            .orderDate(ZonedDateTime.of(2023, 2, 20, 7, 31, 15, 9000, ZoneId.systemDefault()))
            .build();

    Order ORDER_B =  Order
            .builder()
            .id(2L)
            .customer(Customer
                    .builder()
                    .id(2L)
                    .name("B")
                    .surname("B")
                    .age(18)
                    .email("b@gmail.com")
                    .build())
            .product(Product
                    .builder()
                    .id(2L)
                    .name("XX")
                    .price(BigDecimal.valueOf(100))
                    .category(B)
                    .build())
            .quantity(1)
            .orderDate(ZonedDateTime.of(2023, 2, 22, 12, 0, 15, 9000, ZoneId.systemDefault()))
            .build();

    Order ORDER_A_2 = Order
            .builder()
            .id(3L)
            .customer(Customer
                    .builder()
                    .id(1L)
                    .name("A")
                    .surname("A")
                    .age(28)
                    .email("a@gmail.com")
                    .build())
            .product(Product
                    .builder()
                    .id(3L)
                    .name("WW")
                    .price(BigDecimal.valueOf(10))
                    .category(A)
                    .build())
            .quantity(3)
            .orderDate(ZonedDateTime.of(2023, 2, 22, 21, 53, 15, 9000, ZoneId.systemDefault()))
            .build();

    Order ORDER_C =  Order
            .builder()
            .id(4L)
            .customer(Customer
                    .builder()
                    .id(3L)
                    .name("C")
                    .surname("C")
                    .age(40)
                    .email("c@gmail.com")
                    .build())
            .product(Product
                    .builder()
                    .id(4L)
                    .name("ZX")
                    .price(BigDecimal.valueOf(10))
                    .category(A)
                    .build())
            .quantity(10)
            .orderDate(ZonedDateTime.of(2023, 2, 20, 4, 31, 15, 9000, ZoneId.systemDefault()))
            .build();

    Order ORDER_D = Order
            .builder()
            .id(5L)
            .customer(Customer
                    .builder()
                    .id(4L)
                    .name("D")
                    .surname("D")
                    .age(28)
                    .email("d@gmail.com")
                    .build())
            .product(Product
                    .builder()
                    .id(5L)
                    .name("YY")
                    .price(BigDecimal.valueOf(5))
                    .category(C)
                    .build())
            .quantity(8)
            .orderDate(ZonedDateTime.of(2023, 2, 22, 14, 12, 15, 9000, ZoneId.systemDefault()))
            .build();
}