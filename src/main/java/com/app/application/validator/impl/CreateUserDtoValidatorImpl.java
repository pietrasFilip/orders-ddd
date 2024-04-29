package com.app.application.validator.impl;

import com.app.application.dto.user.CreateUserDto;
import com.app.application.validator.CreateUserDtoValidator;
import com.app.domain.user_management.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.app.application.validator.Validator.validateNullOrEmpty;

@Service
@RequiredArgsConstructor
public class CreateUserDtoValidatorImpl implements CreateUserDtoValidator {
    private final UserRepository userRepository;
    Logger logger = LogManager.getRootLogger();

    @Override
    public void validate(CreateUserDto createUserDto) {
        validateNullOrEmpty(createUserDto.username());
        validateNullOrEmpty(createUserDto.email());
        validateNullOrEmpty(createUserDto.password());
        validateNullOrEmpty(createUserDto.passwordConfirmation());

        validatePassword(createUserDto);
        checkIfUserWithUsernameExists(createUserDto.username());
        checkIfUserWithEmailExists(createUserDto.email());
    }

    @Override
    public void validatePassword(CreateUserDto createUserDto) {
        if (!createUserDto.password().equals(createUserDto.passwordConfirmation())) {
            logger.error("Passwords are not the same");
            throw new IllegalArgumentException("Passwords are not correct");
        }
    }

    @Override
    public void checkIfUserWithUsernameExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            logger.error("User with username {} already exists", username);
            throw new IllegalArgumentException("Username already exists");
        }
    }

    @Override
    public void checkIfUserWithEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            logger.error("User with email {} already exists", email);
            throw new IllegalArgumentException("Email already exists");
        }
    }
}
