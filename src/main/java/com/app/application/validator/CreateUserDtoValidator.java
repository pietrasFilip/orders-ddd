package com.app.application.validator;

import com.app.application.dto.user.CreateUserDto;

public interface CreateUserDtoValidator extends Validator<CreateUserDto> {
    void validatePassword(CreateUserDto createUserDto);
    void checkIfUserWithUsernameExists(String username);
    void checkIfUserWithEmailExists(String email);
}
