package com.app.application.validator;

import com.app.application.validator.impl.CreateUserDtoValidatorImpl;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.infrastructure.persistence.repository.db.UserRepositoryDbImpl;
import com.app.infrastructure.persistence.repository.db.dao.UserEntityDao;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ValidatorConfig {

    @Bean
    public UserRepository userRepository(UserEntityDao userEntityDao) {
        return new UserRepositoryDbImpl(userEntityDao);
    }

    @Bean
    public CreateUserDtoValidator createUserDtoValidator(UserRepository userRepository) {
        return new CreateUserDtoValidatorImpl(userRepository);
    }
}
