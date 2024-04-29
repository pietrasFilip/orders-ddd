package com.app.domain.user_management.model.repository;

import com.app.infrastructure.persistence.entity.VerificationTokenEntity;

import java.util.Optional;

public interface VerificationTokenRepository {
    VerificationTokenEntity save(VerificationTokenEntity verificationTokenEntity);
    Optional<VerificationTokenEntity> findByToken(String token);
    void delete(VerificationTokenEntity verificationTokenEntity);
}
