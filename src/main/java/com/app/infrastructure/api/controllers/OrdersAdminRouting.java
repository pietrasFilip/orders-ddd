package com.app.infrastructure.api.controllers;

import com.app.application.dto.response.ResponseDto;
import com.app.application.service.orders.OrdersService;
import com.app.domain.product_management.model.product_category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class OrdersAdminRouting {
    private final OrdersService ordersService;

    @GetMapping("/categories/most_bought")
    public ResponseDto<Set<Category>> getMostBoughtCategory() {
        return new ResponseDto<>(ordersService.getMostBoughtCategory());
    }

    @GetMapping("/months/products")
    public ResponseDto<Map<String, Integer>> getMonthsWithNumberOfBoughtProductsSortedDescending() {
        return new ResponseDto<>(ordersService.getMonthsWithNumberOfBoughtProductsSortedDescending());
    }

    @GetMapping("/categories/most_popular")
    public ResponseDto<Map<String, Set<Category>>> getMonthsWithMostPopularCategory() {
        return new ResponseDto<>(ordersService.getMonthsWithMostPopularCategory());
    }
}
