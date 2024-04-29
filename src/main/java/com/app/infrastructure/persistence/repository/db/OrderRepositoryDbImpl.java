package com.app.infrastructure.persistence.repository.db;

import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.app.infrastructure.persistence.repository.db.dao.OrderEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryDbImpl implements OrderRepositoryDb {
    private final OrderEntityDao orderEntityDao;
    @Override
    public List<OrderEntity> findAll() {
        return orderEntityDao.findAll();
    }

    @Override
    public OrderEntity save(OrderEntity order) {
        return orderEntityDao.save(order);
    }
}
