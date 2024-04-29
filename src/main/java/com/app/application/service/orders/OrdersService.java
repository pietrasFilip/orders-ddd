package com.app.application.service.orders;

import com.app.application.dto.customer.GetCustomerDto;
import com.app.application.dto.email.CreateEmailDto;
import com.app.application.dto.email.GetEmailDto;
import com.app.application.dto.order.CreateOrderDto;
import com.app.application.dto.order.GetOrderDto;
import com.app.application.dto.product.GetProductDto;
import com.app.domain.product_management.model.product_category.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrdersService {
    BigDecimal getAverageOrdersPrice(ZonedDateTime from, ZonedDateTime to);
    Map<Category, List<GetProductDto>> getMaxPriceProductFromCategory();
    Map<GetProductDto, Integer> getProductsPurchasedByCustomer(String email);
    GetEmailDto sendPdfEmailWithProductsPurchasedByCustomer(CreateEmailDto createEmailDto);
    List<LocalDate> getDateOfMinAndMaxNumberOfOrders(boolean min);
    List<GetCustomerDto> getCustomerWithMostExpensiveOrders();
    Map<GetOrderDto, BigDecimal> getOrdersPriceAfterDiscount();
    Set<GetCustomerDto> getCustomersThatOrderedExactlyOrMoreThan(int quantity);
    Set<Category> getMostBoughtCategory();
    Map<String, Integer> getMonthsWithNumberOfBoughtProductsSortedDescending();
    Map<String, Set<Category>> getMonthsWithMostPopularCategory();
    GetOrderDto addOrder(CreateOrderDto createOrderDto);
}
