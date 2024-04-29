package com.app.domain.orders_management.model;

import com.app.application.dto.order.GetOrderDto;
import com.app.domain.customer_management.model.Customer;
import com.app.domain.product_management.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

import static com.app.domain.product_management.model.ProductMapper.toPrice;
import static java.math.BigDecimal.valueOf;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Order {
    final Long id;
    final Customer customer;
    final Product product;
    final int quantity;
    final ZonedDateTime orderDate;
    private static final BigDecimal ORDER_DATE_DISCOUNT_PERCENT = BigDecimal.valueOf(0.98);
    private static final BigDecimal AGE_DISCOUNT_PERCENT = BigDecimal.valueOf(0.97);
    private static final int DISCOUNT_AGE = 25;

    // Methods that give information about order

    /**
     *
     * @param email Email address to check.
     * @return True when customer has given mail or false when not.
     */
    public boolean containsEmail(String email) {
        return customer.hasEmail(email);
    }

    /**
     *
     * @param from Starting date.
     * @param to Ending date.
     * @return True when order date is between given dates or false when not.
     */
    public boolean hasDateBetween(ZonedDateTime from, ZonedDateTime to) {
        return orderDate.isAfter(from) && orderDate.isBefore(to);
    }

    /**
     *
     * @return Total price of order.
     */
    public BigDecimal totalPrice() {
        return toPrice.apply(product).multiply(valueOf(quantity));
    }

    /**
     *
     * @return Total price of order with discount.
     */
    public BigDecimal totalPriceWithDiscount() {
        if (customer.hasDiscountAge(DISCOUNT_AGE)) {
            return totalPrice().multiply(AGE_DISCOUNT_PERCENT);
        }
        if (orderDate.isBefore(ZonedDateTime.now().plusDays(2))){
            return totalPrice().multiply(ORDER_DATE_DISCOUNT_PERCENT);
        }
        return totalPrice();
    }

    // Convert methods

    public GetOrderDto toGetOrderDto() {
        return new GetOrderDto(this.customer.toGetCustomerDto(), this.product.toGetProductDto(), quantity,
                orderDate.toString());
    }
    public Map.Entry<String, Customer> toCustomerMailEntry() {
        return customer.toEntry();
    }

    public Map.Entry<Product, Integer> toNumberOfProductsInOrderEntry() {
        return Map.entry(this.product, this.quantity);
    }
}
