package com.app.application.service.user;

import com.app.application.dto.user.CreateUserDto;
import com.app.application.dto.user.UserActivationDto;
import com.app.application.dto.user.UserActivationTokenDto;
import com.app.application.validator.CreateUserDtoValidator;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.domain.user_management.model.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CreateUserDtoValidator createUserDtoValidator;
    Logger logger = LogManager.getRootLogger();

    @Override
    public Long registerUser(CreateUserDto createUserDto) {
        createUserDtoValidator.validate(createUserDto);

        var userToInsert = createUserDto
                .toUserEntity()
                .withPassword(passwordEncoder.encode(createUserDto.password()));

        var insertedUser = userRepository.save(userToInsert);

        var id = insertedUser.getId();
        applicationEventPublisher.publishEvent(new UserActivationDto(id));
        return id;
    }

    @Override
    public Long activateUser(UserActivationTokenDto userActivationTokenDto) {
        if (userActivationTokenDto == null) {
            throw new IllegalArgumentException("Token is null");
        }

        var token = userActivationTokenDto.token();
        var verificationTokenEntity = verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("User not found for this token"));

        var userToActivate = verificationTokenEntity.validate().orElse(null);
        verificationTokenRepository.delete(verificationTokenEntity);
        if (userToActivate != null) {
            return userRepository.save(verificationTokenEntity.getUser().activate()).getId();
        }
        return null;
    }
}
