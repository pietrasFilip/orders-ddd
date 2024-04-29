package com.app.application.service.token;

import com.app.application.dto.token.RefreshTokenDto;
import com.app.application.dto.token.TokensDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface TokensService {
    TokensDto generateToken(Authentication authentication);
    UsernamePasswordAuthenticationToken parseAccessToken(String token);
    TokensDto refreshTokens(RefreshTokenDto refreshTokenDto);
}
