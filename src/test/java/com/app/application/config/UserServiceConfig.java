package com.app.application.config;

import com.app.application.service.user.UserService;
import com.app.application.service.user.UserServiceImpl;
import com.app.application.validator.CreateUserDtoValidator;
import com.app.application.validator.impl.CreateUserDtoValidatorImpl;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.domain.user_management.model.repository.VerificationTokenRepository;
import com.app.infrastructure.persistence.repository.db.UserRepositoryDbImpl;
import com.app.infrastructure.persistence.repository.db.VerificationTokenRepositoryDbImpl;
import com.app.infrastructure.persistence.repository.db.dao.UserEntityDao;
import com.app.infrastructure.persistence.repository.db.dao.VerificationTokenEntityDao;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class UserServiceConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserRepository userRepository(UserEntityDao userEntityDao) {
        return new UserRepositoryDbImpl(userEntityDao);
    }

    @Bean
    VerificationTokenRepository verificationTokenRepository(VerificationTokenEntityDao verificationTokenEntityDao) {
        return new VerificationTokenRepositoryDbImpl(verificationTokenEntityDao);
    }

    @Bean
    CreateUserDtoValidator createUserDtoValidator(UserRepository userRepository) {
        return new CreateUserDtoValidatorImpl(userRepository);
    }

    @Bean
    public UserService userService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                   VerificationTokenRepository verificationTokenRepository, ApplicationEventPublisher applicationEventPublisher,
                                   CreateUserDtoValidator createUserDtoValidator) {
        return new UserServiceImpl(passwordEncoder, userRepository, verificationTokenRepository,
                applicationEventPublisher, createUserDtoValidator);
    }
}
