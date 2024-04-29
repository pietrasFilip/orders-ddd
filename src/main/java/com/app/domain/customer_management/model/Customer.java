package com.app.domain.customer_management.model;

import com.app.application.dto.customer.GetCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Customer {
    Long id;
    String name;
    String surname;
    int age;
    String email;

    // Methods that give information about client

    /**
     *
     * @param referenceAge Age given by user.
     * @return True when customer age is lower than reference age or false when not.
     */
    public boolean hasDiscountAge(int referenceAge) {
        return this.age <= referenceAge;
    }

    /**
     *
     * @param email Email adres given by user.
     * @return True when email given by user is the same as customer email or false when not.
     */
    public boolean hasEmail(String email) {
        return Objects.equals(this.email, email);
    }

    // Convert methods

    public GetCustomerDto toGetCustomerDto() {
        return new GetCustomerDto(id, name, surname);
    }

    public Map.Entry<String, Customer> toEntry() {
        return Map.entry(this.email, this);
    }
}
