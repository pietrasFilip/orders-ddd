package com.app.application.dto.order;

import com.app.application.dto.customer.GetCustomerDto;
import com.app.application.dto.product.GetProductDto;

public record GetOrderDto(
        GetCustomerDto customerDto,
        GetProductDto productDto,
        int quantity,
        String orderDate
) {
}
