package com.app.infrastructure.api.controllers;

import com.app.application.dto.order.GetOrderDto;
import com.app.application.dto.response.ResponseDto;
import com.app.domain.orders_management.model.Order;
import com.app.infrastructure.persistence.provider.OrdersProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/all/orders")
@RequiredArgsConstructor
public class OrdersUnauthorizedRouting {
    private final OrdersProvider ordersProvider;

    @GetMapping
    public ResponseDto<List<GetOrderDto>> getOrders() {
        return new ResponseDto<>(ordersProvider
                .provide()
                .stream()
                .map(Order::toGetOrderDto)
                .toList());
    }
}
