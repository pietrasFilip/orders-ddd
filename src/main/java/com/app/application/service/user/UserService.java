package com.app.application.service.user;

import com.app.application.dto.user.CreateUserDto;
import com.app.application.dto.user.UserActivationTokenDto;

public interface UserService {
    Long registerUser(CreateUserDto createUserDto);
    Long activateUser(UserActivationTokenDto userActivationTokenDto);
}
