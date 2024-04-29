package com.app.domain.product_management.model;

import com.app.domain.product_management.model.product_category.Category;

import java.math.BigDecimal;
import java.util.function.Function;

public interface ProductMapper {
    Function<Product, BigDecimal> toPrice = product -> product.price;
    Function<Product, Category> toCategory = product -> product.category;
    Function<Product, String> toProductName = product -> product.name;
}
