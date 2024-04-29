package com.app.application.service.token;

import com.app.application.dto.token.RefreshTokenDto;
import com.app.application.dto.token.TokensDto;
import com.app.domain.user_management.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokensServiceImpl implements TokensService {
    @Value("${tokens.access.expiration_time_ms}")
    private Long accessTokenExpirationTimeMs;

    @Value("${tokens.refresh.expiration_time_ms}")
    private Long refreshTokenExpirationTimeMs;

    @Value("${tokens.refresh.access_token_expiration_time_ms_property}")
    private String refreshTokenProperty;

    @Value("${tokens.prefix}")
    private String tokensPrefix;

    private final UserRepository userRepository;
    private final SecretKey secretKey;

    @Override
    public TokensDto generateToken(Authentication authentication) {
        var userFromDb = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Authentication failed!"));

        var userId = userFromDb.getId();
        var currentDate = new Date();
        var accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        var refreshTokenExpirationDate = new Date(currentDate.getTime() + refreshTokenExpirationTimeMs);

        return createTokens(userId, accessTokenExpirationDate, refreshTokenExpirationDate, currentDate);
    }

    @Override
    public UsernamePasswordAuthenticationToken parseAccessToken(String header) {
        if (!header.startsWith(tokensPrefix)) {
            throw new IllegalArgumentException("Authorization header incorrect format");
        }

        var token = header.replaceAll(tokensPrefix, "");
        if (isTokenNotValid(token)) {
            throw new IllegalArgumentException("Authorization header is not valid");
        }

        var userId = id(token);
        return userRepository
                .findById(userId)
                .map(userFromDb -> new UsernamePasswordAuthenticationToken(
                        userFromDb.getUsername(),
                        null,
                        List.of(new SimpleGrantedAuthority(userFromDb.getRole().toString()))
                ))
                .orElseThrow();
    }

    @Override
    public TokensDto refreshTokens(RefreshTokenDto refreshTokenDto) {
        if (refreshTokenDto == null) {
            throw new IllegalArgumentException("Refresh token dto is null");
        }

        var refreshToken = refreshTokenDto.token();

        if (isTokenNotValid(refreshToken)) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        var accessTokenExpTime = accessTokenExpirationTimeMsFromRefreshToken(refreshToken);
        if (accessTokenExpTime < System.currentTimeMillis()) {
            throw new IllegalStateException("Cannot create new access token - old access token expired");
        }

        var userId = id(refreshToken);
        var currentDate = new Date();
        var newAccessTokenExpirationTime = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        var refreshTokenExpirationTime = expirationDate(refreshToken);

        return createTokens(userId, newAccessTokenExpirationTime, refreshTokenExpirationTime, currentDate);
    }

    private TokensDto createTokens(Long userId, Date newAccessTokenExpirationTime, Date refreshTokenExpirationTime,
                                   Date currentDate) {
        var newAccessToken = Jwts
                .builder()
                .subject(userId + "")
                .expiration(newAccessTokenExpirationTime)
                .issuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var updatedRefreshToken = Jwts
                .builder()
                .subject(userId + "")
                .expiration(refreshTokenExpirationTime)
                .issuedAt(currentDate)
                .claim(refreshTokenProperty, newAccessTokenExpirationTime.getTime())
                .signWith(secretKey)
                .compact();

        return new TokensDto(newAccessToken, updatedRefreshToken);
    }

    private Claims claims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Long id(String token) {
        return Long.parseLong(claims(token).getSubject());
    }

    private Date expirationDate(String token) {
        return claims(token).getExpiration();
    }

    private boolean isTokenNotValid(String token) {
        return expirationDate(token).before(new Date());
    }

    private Long accessTokenExpirationTimeMsFromRefreshToken(String token) {
        return claims(token).get(refreshTokenProperty, Long.class);
    }
}
