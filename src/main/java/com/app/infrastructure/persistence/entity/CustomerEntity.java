package com.app.infrastructure.persistence.entity;

import com.app.application.dto.customer.GetCustomerDto;
import com.app.domain.customer_management.model.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity {
    private String name;
    private String surname;
    private int age;
    private String email;

    public Customer toDomain() {
        return Customer
                .builder()
                .id(getId())
                .name(name)
                .surname(surname)
                .age(age)
                .email(email)
                .build();
    }

    public boolean isNotTheSameAs(CustomerEntity customer) {
        return !name.equals(customer.name) || !surname.equals(customer.surname) ||
                age != customer.age;
    }

    public GetCustomerDto toGetCustomerDto() {
        return new GetCustomerDto(id, name, surname);
    }
}
