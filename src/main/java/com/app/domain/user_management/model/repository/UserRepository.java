package com.app.domain.user_management.model.repository;

import com.app.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    UserEntity save(UserEntity userEntity);
}
