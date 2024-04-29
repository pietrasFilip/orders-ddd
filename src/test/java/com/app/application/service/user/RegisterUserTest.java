package com.app.application.service.user;

import com.app.application.config.UserServiceConfig;
import com.app.application.dto.user.CreateUserDto;
import com.app.domain.user_management.model.Role;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.infrastructure.persistence.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class RegisterUserTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("When user is registered correctly with ROLE_USER")
    void test1() {
        var createUserDto = new CreateUserDto("aaa", "a@gmail.com", "1234", "1234", Role.ROLE_USER);

        var userId = userService.registerUser(createUserDto);
        var registeredUser = userRepository.findById(userId);

        assertThat(registeredUser)
                .isInstanceOf(Optional.class);

        assertThat(registeredUser.get().getUsername())
                .isEqualTo(createUserDto.username());

        assertThat(registeredUser.get().getEmail())
                .isEqualTo(createUserDto.email());

        assertThat(registeredUser.get().getRole())
                .isEqualTo(createUserDto.role());
    }

    @Test
    @DisplayName("When user is registered correctly with ROLE_ADMIN")
    void test2() {
        var createUserDto = new CreateUserDto("aaa", "a@gmail.com", "1234", "1234", Role.ROLE_ADMIN);

        var userId = userService.registerUser(createUserDto);
        var registeredUser = userRepository.findById(userId);

        assertThat(registeredUser)
                .isInstanceOf(Optional.class);

        assertThat(registeredUser.get().getUsername())
                .isEqualTo(createUserDto.username());

        assertThat(registeredUser.get().getEmail())
                .isEqualTo(createUserDto.email());

        assertThat(registeredUser.get().getRole())
                .isEqualTo(createUserDto.role());
    }

}
