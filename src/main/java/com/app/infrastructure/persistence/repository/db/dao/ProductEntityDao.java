package com.app.infrastructure.persistence.repository.db.dao;

import com.app.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEntityDao extends JpaRepository<ProductEntity, Long> {
}
