package com.app.domain.policy.abstract_factory.validator.impl;

import com.app.domain.policy.abstract_factory.validator.CustomerDataValidator;
import com.app.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.app.domain.policy.abstract_factory.validator.DataValidator.*;

@RequiredArgsConstructor
public class CustomerDataValidatorImpl implements CustomerDataValidator {

    @Value("${validator.customer.name.regex}")
    private String nameRegex;

    @Value("${validator.customer.surname.regex}")
    private String surnameRegex;

    @Value("${validator.customer.min.age}")
    private int minAge;

    @Value("${validator.customer.email.regex}")
    private String emailRegex;

    @Override
    public CustomerEntity validateSingleCustomer(CustomerEntity customerEntity) {
        return CustomerEntity
                .builder()
                .id(validateNull(customerEntity.getId()))
                .name(validateMatchesRegex(nameRegex, customerEntity.getName()))
                .surname(validateMatchesRegex(surnameRegex, customerEntity.getSurname()))
                .age(validateIntLowerThan(customerEntity.getAge(), minAge))
                .email(validateMatchesRegex(emailRegex, customerEntity.getEmail()))
                .build();
    }

    @Override
    public List<CustomerEntity> validate(List<CustomerEntity> customerEntities) {
        return customerEntities
                .stream()
                .map(this::validateSingleCustomer)
                .toList();
    }
}
