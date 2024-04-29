package com.app.infrastructure.persistence.repository.json;

import com.app.domain.policy.abstract_factory.loader.json.FromJsonToObjectLoader;
import com.app.domain.product_management.model.repository.ProductRepositoryJson;
import com.app.infrastructure.persistence.entity.ProductEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryJsonImpl extends FromJsonToObjectLoader<List<ProductEntity>> implements ProductRepositoryJson {

    @Value("${repository.path.json.products}")
    private String path;

    public ProductRepositoryJsonImpl(Gson gson) {
        super(gson);
    }

    @Override
    public List<ProductEntity> findAll() {
        return loadObject(path);
    }

    @Override
    public ProductEntity findById(Long id) {
        return findAll()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id not found"));
    }
}
