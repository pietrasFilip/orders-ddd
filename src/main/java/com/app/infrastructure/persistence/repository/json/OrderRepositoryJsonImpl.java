package com.app.infrastructure.persistence.repository.json;

import com.app.domain.customer_management.model.repository.CustomerRepositoryJson;
import com.app.domain.orders_management.model.repository.OrderRepositoryJson;
import com.app.domain.policy.abstract_factory.loader.json.FromJsonToObjectLoader;
import com.app.domain.product_management.model.repository.ProductRepositoryJson;
import com.app.infrastructure.persistence.entity.OrderEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.gson.JsonParser.parseReader;

@Repository
public class OrderRepositoryJsonImpl extends FromJsonToObjectLoader<List<OrderEntity>> implements OrderRepositoryJson {

    @Value("${repository.path.json.orders}")
    private String path;

    private final CustomerRepositoryJson customerRepository;
    private final ProductRepositoryJson productRepository;
    private static final Logger logger = LogManager.getRootLogger();

    public OrderRepositoryJsonImpl(Gson gson, CustomerRepositoryJson customerRepository, ProductRepositoryJson productRepository) {
        super(gson);
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderEntity> findAll() {
        try(var fileReader = new FileReader(path)) {
            var ordersJsonArr = parseReader(fileReader).getAsJsonArray();
            var ordersJsonObjectList = new ArrayList<JsonObject>();
            ordersJsonArr.forEach(o -> ordersJsonObjectList.add(o.getAsJsonObject()));
            return ordersJsonObjectList
                    .stream()
                    .map(order -> OrderEntity
                            .builder()
                            .id(order.get("id").getAsLong())
                            .customer(customerRepository.findById(order.get("customer").getAsLong()))
                            .product(productRepository.findById(order.get("product").getAsLong()))
                            .quantity(order.get("quantity").getAsInt())
                            .orderDate(order.get("orderDate").getAsString())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Order conversion from Json failed");
            throw new IllegalStateException(e.getMessage());
        }
    }
}
