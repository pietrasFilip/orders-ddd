package com.app.infrastructure.api.filter;

import com.app.application.dto.error.AuthenticationErrorDto;
import com.app.application.dto.token.AuthenticationDto;
import com.app.application.service.token.TokensService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final TokensService tokensService;
    private final AuthenticationManager authenticationManager;

    public AppAuthenticationFilter(TokensService tokensService, AuthenticationManager authenticationManager) {
        this.tokensService = tokensService;
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        var jsonBody = new ObjectMapper()
                .readValue(request.getInputStream(), AuthenticationDto.class);

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                jsonBody.username(),
                jsonBody.password(),
                Collections.emptyList()
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var tokens = tokensService.generateToken(authResult);
        Cookie accesTokenCookie = new Cookie("AccessToken", tokens.access());
        accesTokenCookie.setHttpOnly(true);
        accesTokenCookie.setMaxAge(86400);

        Cookie refreshTokenCookie = new Cookie("RefreshToken", tokens.refresh());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(86400);

        response.addCookie(accesTokenCookie);
        response.addCookie(refreshTokenCookie);

        // pod aplikacje react
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        var ex = new AuthenticationErrorDto("Wrong username or password");

        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(ex));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
