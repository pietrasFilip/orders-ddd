package com.app.infrastructure.api.controllers;

import com.app.application.dto.response.ResponseDto;
import com.app.application.dto.token.RefreshTokenDto;
import com.app.application.dto.token.TokensDto;
import com.app.application.dto.user.CreateUserDto;
import com.app.application.dto.user.UserActivationTokenDto;
import com.app.application.service.token.TokensService;
import com.app.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokensService tokensService;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseDto<Long> registerUser(@RequestBody CreateUserDto createUserDto) {
        return new ResponseDto<>(userService.registerUser(createUserDto));
    }

    @PostMapping("/activate")
    public ResponseEntity<ResponseDto<Long>> activateUser(@RequestBody UserActivationTokenDto userActivationTokenDto) {
        var id = userService.activateUser(userActivationTokenDto);
        return id == null ?
                new ResponseEntity<>(new ResponseDto<>(null), HttpStatus.INTERNAL_SERVER_ERROR) :
                new ResponseEntity<>(new ResponseDto<>(id), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokensDto> activateUser(@RequestBody RefreshTokenDto refreshTokenDto) {
        return new ResponseEntity<>(tokensService.refreshTokens(refreshTokenDto), HttpStatus.CREATED);
    }
}
