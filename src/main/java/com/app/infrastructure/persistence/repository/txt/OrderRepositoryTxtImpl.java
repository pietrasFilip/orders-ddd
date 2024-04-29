package com.app.infrastructure.persistence.repository.txt;

import com.app.domain.customer_management.model.repository.CustomerRepositoryTxt;
import com.app.domain.orders_management.model.repository.OrderRepositoryTxt;
import com.app.domain.product_management.model.repository.ProductRepositoryTxt;
import com.app.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.Long.valueOf;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryTxtImpl implements OrderRepositoryTxt {

    @Value("${repository.path.txt.orders}")
    private String path;

    private final CustomerRepositoryTxt customerRepositoryTxt;
    private final ProductRepositoryTxt productRepositoryTxt;

    @Override
    public List<OrderEntity> findAll() {
        try (var lines = Files.lines(Paths.get(path))){
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        return OrderEntity
                                .builder()
                                .id(valueOf(items[0]))
                                .customer(customerRepositoryTxt.findById(valueOf(items[1])))
                                .product(productRepositoryTxt.findById(valueOf(items[2])))
                                .quantity(parseInt(items[3]))
                                .orderDate(items[4])
                                .build();
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
