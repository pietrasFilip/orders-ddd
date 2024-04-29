package com.app.infrastructure.persistence.entity;

import com.app.domain.user_management.model.Role;
import com.app.domain.user_management.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive;

    public User toDomain() {
        return User
                .builder()
                .id(getId())
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }

    public UserEntity withPassword(String password) {
        return UserEntity
                .builder()
                .id(getId())
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }

    public UserEntity activate() {
        return UserEntity
                .builder()
                .id(getId())
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(true)
                .build();
    }

    public User toDomainWithActiveState(boolean isActive) {
        return User
                .builder()
                .id(getId())
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }
}
