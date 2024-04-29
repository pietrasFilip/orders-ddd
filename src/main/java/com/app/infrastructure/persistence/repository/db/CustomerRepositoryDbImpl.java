package com.app.infrastructure.persistence.repository.db;

import com.app.domain.customer_management.model.repository.CustomerRepositoryDb;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.app.infrastructure.persistence.repository.db.dao.CustomerEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryDbImpl implements CustomerRepositoryDb {
    private final CustomerEntityDao customerEntityDao;
    @Override
    public List<CustomerEntity> findAll() {
        return customerEntityDao.findAll();
    }

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        return customerEntityDao.findByEmail(email);
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        return customerEntityDao.save(customer);
    }
}
