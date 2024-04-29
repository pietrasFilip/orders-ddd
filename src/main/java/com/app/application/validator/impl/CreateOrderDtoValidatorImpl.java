package com.app.application.validator.impl;

import com.app.application.dto.order.CreateOrderDto;
import com.app.application.validator.CreateOrderDtoValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.app.application.validator.Validator.validateNullOrEmpty;
import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateIntLowerThan;
import static com.app.domain.policy.abstract_factory.validator.DataValidator.validateMatchesRegex;
import static java.lang.Integer.parseInt;

@Service
public class CreateOrderDtoValidatorImpl implements CreateOrderDtoValidator {

    @Value("${validator.order.min.quantity}")
    private String minQuantity;
    @Value("${validator.customer.name.regex}")
    private String nameRegex;
    @Value("${validator.customer.surname.regex}")
    private String surnameRegex;
    @Value("${validator.customer.email.regex}")
    private String emailRegex;
    @Value("${validator.customer.min.age}")
    private String minAge;

    @Override
    public void validate(CreateOrderDto createOrderDto) {
        validateNullOrEmpty(createOrderDto);

        validateIntLowerThan(createOrderDto.quantity(), parseInt(minQuantity));
        validateMatchesRegex(nameRegex, createOrderDto.customerName());
        validateMatchesRegex(surnameRegex, createOrderDto.surname());
        validateMatchesRegex(emailRegex, createOrderDto.email());
        validateIntLowerThan(createOrderDto.age(), parseInt(minAge));
    }
}
