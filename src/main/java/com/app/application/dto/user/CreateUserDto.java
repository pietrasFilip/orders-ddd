package com.app.application.dto.user;

import com.app.domain.user_management.model.Role;
import com.app.infrastructure.persistence.entity.UserEntity;

public record CreateUserDto(
        String username,
        String email,
        String password,
        String passwordConfirmation,
        Role role
) {
    public UserEntity toUserEntity() {
        return UserEntity
                .builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(false)
                .build();
    }
}
