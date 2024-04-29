package com.app.infrastructure.api.events.listener;

import com.app.application.dto.order.AddOrderDto;
import com.app.domain.orders_management.model.repository.OrderRepositoryDb;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddOrderListener {
    private final OrderRepositoryDb orderRepository;
    private static final Logger logger = LogManager.getRootLogger();


    @EventListener
    public void addOrder(AddOrderDto addOrderDto) {
        var orderEntity = addOrderDto.orderEntity();
        logger.info("Adding order with id {}...", orderEntity.getId());

        orderRepository.save(orderEntity);
        logger.info("Order with id {} added successfully.", orderEntity.getId());
    }
}
