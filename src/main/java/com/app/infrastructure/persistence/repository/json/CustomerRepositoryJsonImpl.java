package com.app.infrastructure.persistence.repository.json;

import com.app.domain.customer_management.model.repository.CustomerRepositoryJson;
import com.app.domain.policy.abstract_factory.loader.json.FromJsonToObjectLoader;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepositoryJsonImpl extends FromJsonToObjectLoader<List<CustomerEntity>> implements CustomerRepositoryJson {

    @Value("${repository.path.json.customers}")
    private String path;

    public CustomerRepositoryJsonImpl(Gson gson) {
        super(gson);
    }

    @Override
    public List<CustomerEntity> findAll() {
        return loadObject(path);
    }

    @Override
    public CustomerEntity findById(Long id) {
        return findAll()
                .stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Customer with id not found"));
    }
}
