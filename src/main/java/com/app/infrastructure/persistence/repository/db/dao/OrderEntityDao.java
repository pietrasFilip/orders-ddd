package com.app.infrastructure.persistence.repository.db.dao;

import com.app.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntityDao extends JpaRepository<OrderEntity, Long> {
}
