package com.app.infrastructure.persistence.repository.db;

import com.app.domain.user_management.model.repository.UserRepository;
import com.app.infrastructure.persistence.entity.UserEntity;
import com.app.infrastructure.persistence.repository.db.dao.UserEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryDbImpl implements UserRepository {
    private final UserEntityDao userEntityDao;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userEntityDao.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userEntityDao.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityDao.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userEntityDao.save(userEntity);
    }
}
