package com.app.infrastructure.persistence.repository.db;

import com.app.domain.user_management.model.repository.VerificationTokenRepository;
import com.app.infrastructure.persistence.entity.VerificationTokenEntity;
import com.app.infrastructure.persistence.repository.db.dao.VerificationTokenEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VerificationTokenRepositoryDbImpl implements VerificationTokenRepository {
    private final VerificationTokenEntityDao verificationTokenEntityDao;
    @Override
    public VerificationTokenEntity save(VerificationTokenEntity verificationTokenEntity) {
        return verificationTokenEntityDao.save(verificationTokenEntity);
    }

    @Override
    public Optional<VerificationTokenEntity> findByToken(String token) {
        return verificationTokenEntityDao.findByToken(token);
    }

    @Override
    public void delete(VerificationTokenEntity verificationTokenEntity) {
        verificationTokenEntityDao.delete(verificationTokenEntity);
    }
}
