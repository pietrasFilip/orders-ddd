package com.app.infrastructure.persistence.repository.txt;

import com.app.domain.product_management.model.product_category.Category;
import com.app.domain.product_management.model.repository.ProductRepositoryTxt;
import com.app.infrastructure.persistence.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@Repository
public class ProductRepositoryTxtImpl implements ProductRepositoryTxt {

    @Value("${repository.path.txt.products}")
    private String path;

    @Override
    public List<ProductEntity> findAll() {
        try (var lines = Files.lines(Paths.get(path))){
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        return ProductEntity
                                .builder()
                                .id(valueOf(items[0]))
                                .name(items[1])
                                .price(new BigDecimal(items[2]))
                                .category(Category.valueOf(items[3]))
                                .build();
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public ProductEntity findById(Long id) {
        return findAll()
                .stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id not found"));
    }
}
