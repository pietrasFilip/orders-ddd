package com.app.application.dto.product;

import java.math.BigDecimal;

public record GetProductDto(Long id, String name, BigDecimal price) {
}
