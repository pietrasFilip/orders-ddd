package com.app.domain.user_management.model;

import com.app.application.dto.token.AuthorizationDto;
import com.app.application.dto.user.GetUserDto;
import com.app.infrastructure.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean isActive;

    /*public User withPassword(String password) {
        return User
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }

    public User withActiveState(boolean isActive) {
        return User
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }*/

    public UserEntity toEntity() {
        return UserEntity
                .builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .isActive(isActive)
                .build();
    }

    public boolean isActive() {
        return isActive;
    }

    public GetUserDto toGetUserDto() {
        return new GetUserDto(id, username, email);
    }

    public AuthorizationDto toAuthorizationDto() {
        return new AuthorizationDto(role);
    }
}
