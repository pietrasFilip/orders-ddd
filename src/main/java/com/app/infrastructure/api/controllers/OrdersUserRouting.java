package com.app.infrastructure.api.controllers;

import com.app.application.dto.customer.GetCustomerDto;
import com.app.application.dto.email.CreateEmailDto;
import com.app.application.dto.email.GetEmailDto;
import com.app.application.dto.order.CreateOrderDto;
import com.app.application.dto.order.GetOrderDto;
import com.app.application.dto.product.GetProductDto;
import com.app.application.dto.response.ResponseDto;
import com.app.application.service.orders.OrdersService;
import com.app.domain.product_management.model.product_category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/user/orders")
@RequiredArgsConstructor
public class OrdersUserRouting {
    private final OrdersService ordersService;

    @GetMapping("/average_price/{timeFrom}/{timeTo}")
    public ResponseDto<BigDecimal> getAverageOrdersPrice(@PathVariable ZonedDateTime timeFrom, @PathVariable ZonedDateTime timeTo) {
        return new ResponseDto<>(ordersService.getAverageOrdersPrice(timeFrom, timeTo));
    }

    @GetMapping("/max_price")
    public ResponseDto<Map<Category, List<GetProductDto>>> getMaxPriceProductFromCategory() {
        return new ResponseDto<>(ordersService.getMaxPriceProductFromCategory());
    }

    @GetMapping("/purchased/{email}")
    public ResponseDto<Map<GetProductDto, Integer>> getProductsPurchasedByCustomer(@PathVariable String email) {
        return new ResponseDto<>(ordersService.getProductsPurchasedByCustomer(email));
    }

    @GetMapping("/quantity/date/{isMin}")
    public ResponseDto<List<LocalDate>> getDateOfMinAndMaxNumberOfOrders(@PathVariable boolean isMin) {
        return new ResponseDto<>(ordersService.getDateOfMinAndMaxNumberOfOrders(isMin));
    }

    @GetMapping("/most_expensive/customer")
    public ResponseDto<List<GetCustomerDto>> getCustomerWithMostExpensiveOrders() {
        return new ResponseDto<>(ordersService.getCustomerWithMostExpensiveOrders());
    }

    @GetMapping("/quantity/{numberOfOrders}/customer")
    public ResponseDto<Set<GetCustomerDto>> getCustomersThatOrderedExactlyOrMoreThan(@PathVariable int numberOfOrders) {
        return new ResponseDto<>(ordersService.getCustomersThatOrderedExactlyOrMoreThan(numberOfOrders));
    }

    @GetMapping("/price/discount")
    public ResponseDto<Map<GetOrderDto, BigDecimal>> getOrdersPriceAfterDiscount() {
        return new ResponseDto<>(ordersService.getOrdersPriceAfterDiscount());
    }

    @PostMapping("/add")
    public ResponseDto<GetOrderDto> addOrder(@RequestBody CreateOrderDto createOrderDto) {
        return new ResponseDto<>(ordersService.addOrder(createOrderDto));
    }

    @GetMapping("/document")
    public ResponseDto<GetEmailDto> sendPdfEmailWithProductsPurchasedByCustomer(@RequestBody CreateEmailDto createEmailDto) {
        return new ResponseDto<>(ordersService.sendPdfEmailWithProductsPurchasedByCustomer(createEmailDto));
    }
}

