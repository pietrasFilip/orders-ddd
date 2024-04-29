package com.app.infrastructure.persistence.repository.db;

import com.app.domain.product_management.model.repository.ProductRepositoryDb;
import com.app.infrastructure.persistence.entity.ProductEntity;
import com.app.infrastructure.persistence.repository.db.dao.ProductEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryDbImpl implements ProductRepositoryDb {
    private final ProductEntityDao productEntityDao;
    @Override
    public List<ProductEntity> findAll() {
        return productEntityDao.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productEntityDao.findById(id);
    }
}
