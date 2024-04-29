package com.app.infrastructure.persistence.provider.impl;

import com.app.domain.orders_management.model.Order;
import com.app.domain.policy.abstract_factory.processor.OrderDataProcessor;
import com.app.domain.policy.abstract_factory.processor.type.ProcessorType;
import com.app.infrastructure.persistence.provider.OrdersProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrdersProviderImpl implements OrdersProvider {

    @Value("${processor.orders.type}")
    private String processorType;

    private final OrderDataProcessor orderDataProcessorDb;
    private final OrderDataProcessor orderDataProcessorJson;
    private final OrderDataProcessor orderDataProcessorTxt;

    @Override
    public List<Order> provide() {
        return switch (ProcessorType.valueOf(processorType)) {
            case DB -> orderDataProcessorDb.process();
            case JSON -> orderDataProcessorJson.process();
            case TXT -> orderDataProcessorTxt.process();
        };
    }
}
