package com.app.application.service.user;

import com.app.application.config.UserServiceConfig;
import com.app.application.dto.user.UserActivationTokenDto;
import com.app.domain.user_management.model.Role;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.domain.user_management.model.repository.VerificationTokenRepository;
import com.app.infrastructure.persistence.entity.UserEntity;
import com.app.infrastructure.persistence.entity.VerificationTokenEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(UserServiceConfig.class)
@TestPropertySource("classpath:application-service-test.properties")
class ActivateUserTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Test
    @DisplayName("When UserActivationTokenDto is null")
    void test1() {
        assertThatThrownBy(() -> userService.activateUser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Token is null");
    }

    @Test
    @DisplayName("When user with token does not exist")
    void test2() {
        var userActivationToken = new UserActivationTokenDto("123");
        assertThatThrownBy(() -> userService.activateUser(userActivationToken))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User not found for this token");
    }

    @Test
    @DisplayName("When user is activated correctly")
    void test3() {
        var userToActivate = UserEntity
                .builder()
                .username("user")
                .email("user@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .isActive(false)
                .build();

        var verificationTokenEntity = VerificationTokenEntity
                .builder()
                .token("1234")
                .timestamp(System.nanoTime() + 300000000000L)
                .user(userToActivate)
                .build();

        var savedUser = userRepository.save(userToActivate);
        var tokenDto = new UserActivationTokenDto("1234");

        verificationTokenRepository.save(verificationTokenEntity);
        var activatedUserId = userService.activateUser(tokenDto);

        assertThat(activatedUserId)
                .isEqualTo(savedUser.getId());

        var activatedUser = userRepository.findById(activatedUserId).orElseThrow();

        assertThat(activatedUser.isActive())
                .isTrue();
    }

    @Test
    @DisplayName("When verification token is deleted from db")
    void test4() {
        var userToActivate = UserEntity
                .builder()
                .username("user")
                .email("user@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .isActive(false)
                .build();

        var verificationTokenEntity = VerificationTokenEntity
                .builder()
                .token("1234")
                .timestamp(System.nanoTime() + 300000000000L)
                .user(userToActivate)
                .build();

        var savedUser = userRepository.save(userToActivate);
        var tokenDto = new UserActivationTokenDto("1234");

        verificationTokenRepository.save(verificationTokenEntity);
        var activatedUserId = userService.activateUser(tokenDto);

        assertThat(verificationTokenRepository.findByToken(tokenDto.token()))
                .isNotPresent();
    }
}
