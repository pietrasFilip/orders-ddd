package com.app.domain.orders_management.model;

import com.app.domain.customer_management.model.Customer;
import com.app.domain.product_management.model.Product;
import com.app.domain.product_management.model.product_category.Category;

import java.time.LocalDate;
import java.util.function.Function;

import static com.app.domain.product_management.model.ProductMapper.toCategory;

public interface OrderMapper {
    Function<Order, Product> toProduct = order -> order.product;
    Function<Order, Integer> toQuantity = order -> order.quantity;
    Function<Order, LocalDate> toOrderLocalDate = order -> order.orderDate.toLocalDate();
    Function<Order, Customer> toCustomer = order -> order.customer;
    Function<Order, Category> orderToCategory = order -> toCategory.apply(order.product);
    Function<Order, String> toOrderMonthName = order -> order.orderDate.toLocalDate().getMonth().name();
}
