package com.app.infrastructure.persistence.entity;

import com.app.application.dto.product.GetProductDto;
import com.app.domain.product_management.model.Product;
import com.app.domain.product_management.model.product_category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    private String name;
    private BigDecimal price;
    private Category category;

    public Product toDomain() {
        return Product
                .builder()
                .id(getId())
                .name(name)
                .price(price)
                .category(category)
                .build();
    }

    public GetProductDto toGetProductDto() {
        return new GetProductDto(id, name, price);
    }
}
