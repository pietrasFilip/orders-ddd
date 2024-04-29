package com.app.infrastructure.api.events.listener;

import com.app.application.dto.user.UserActivationDto;
import com.app.application.service.email.EmailService;
import com.app.domain.user_management.model.repository.UserRepository;
import com.app.domain.user_management.model.repository.VerificationTokenRepository;
import com.app.infrastructure.persistence.entity.VerificationTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserActivationListener {

    @Value("${mail.activation-mail.expiration.time}")
    private Long activationEmailExpirationTime;

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendActivationMail(UserActivationDto userActivationDto) {
        var userToActivate = userRepository
                .findById(userActivationDto.userId())
                .orElseThrow();
        var token = UUID.randomUUID().toString().replaceAll("\\W", "");
        var timestamp = System.nanoTime() + activationEmailExpirationTime;
        var verificationToken = VerificationTokenEntity
                .builder()
                .token(token)
                .timestamp(timestamp)
                .user(userToActivate)
                .build();
        verificationTokenRepository.save(verificationToken);
        emailService.send(
                userToActivate.getEmail(),
                "Activation link",
                "Use code to activate " + token
        );
    }
}
