package com.app.application.validator;

import com.app.application.dto.user.CreateUserDto;
import com.app.domain.user_management.model.Role;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(ValidatorConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class ValidatePasswordTest {

    @Autowired
    CreateUserDtoValidator createUserDtoValidator;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("When passwords are not the same")
    void test1() {
        var createUserDto = new CreateUserDto("a", "a@gmail.com", "1234",
                "123", Role.ROLE_USER);

        assertThatThrownBy(() -> createUserDtoValidator.validatePassword(createUserDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Passwords are not correct");
    }

    @Test
    @DisplayName("When user with username exists")
    void test2() {
        var userEntity = UserEntity
                .builder()
                .username("a")
                .email("b@gmail.com")
                .password("1234")
                .role(Role.ROLE_USER)
                .isActive(true)
                .build();

        var username = userRepository.save(userEntity).getUsername();

        assertThatThrownBy(() -> createUserDtoValidator.checkIfUserWithUsernameExists(username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username already exists");
    }

    @Test
    @DisplayName("When user with email exists")
    void test3() {
        var userEntity = UserEntity
                .builder()
                .username("a")
                .email("b@gmail.com")
                .password("1234")
                .role(Role.ROLE_USER)
                .isActive(true)
                .build();

        var email = userRepository.save(userEntity).getEmail();

        assertThatThrownBy(() -> createUserDtoValidator.checkIfUserWithEmailExists(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already exists");
    }
}
