package com.app.infrastructure.persistence.repository.db.dao;

import com.app.infrastructure.persistence.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenEntityDao extends JpaRepository<VerificationTokenEntity, Long> {
    Optional<VerificationTokenEntity> findByToken(String token);
}
