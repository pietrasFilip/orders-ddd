package com.app.infrastructure.persistence.repository.txt;

import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.Long.valueOf;

@Repository
public class CustomerRepositoryTxtImpl implements CustomerRepositoryTxt {

    @Value("${repository.path.txt.customers}")
    private String path;

    @Override
    public List<CustomerEntity> findAll() {
        try (var lines = Files.lines(Paths.get(path))){
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        return CustomerEntity
                                .builder()
                                .id(valueOf(items[0]))
                                .name(items[1])
                                .surname(items[2])
                                .age(parseInt(items[3]))
                                .email(items[4])
                                .build();
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public CustomerEntity findById(Long id) {
        return findAll()
                .stream()
                .filter(customer -> id.equals(customer.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Customer with id not found"));
    }
}
