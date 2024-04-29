package com.app.domain.product_management.model;

import com.app.application.dto.product.GetProductDto;
import com.app.domain.product_management.model.product_category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Product {
    Long id;
    String name;
    BigDecimal price;
    Category category;

    public GetProductDto toGetProductDto() {
        return new GetProductDto(id, name, price);
    }
}
