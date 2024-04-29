package com.app.application.dto.token;


import com.app.domain.user_management.model.Role;

import static com.app.domain.user_management.model.Role.ROLE_ADMIN;
import static com.app.domain.user_management.model.Role.ROLE_USER;

public record AuthorizationDto(Role role) {
    public boolean isAuth() {
        return role != null;
    }

    public boolean isUser() {
        return role.equals(ROLE_USER);
    }

    public boolean isAdmin() {
        return role.equals(ROLE_ADMIN);
    }
}
