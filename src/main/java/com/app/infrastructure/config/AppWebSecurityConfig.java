package com.app.infrastructure.config;

import com.app.application.dto.error.AuthenticationErrorDto;
import com.app.application.service.token.TokensService;
import com.app.application.service.user.AppUserDetailsServiceImpl;
import com.app.infrastructure.api.filter.AppAuthenticationFilter;
import com.app.infrastructure.api.filter.AppAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AppWebSecurityConfig {
    private final AppUserDetailsServiceImpl appUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokensService tokensService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        var authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(appUserDetailsService)
                .passwordEncoder(passwordEncoder);
        var authenticationManager = authenticationManagerBuilder.build();

        httpSecurity
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint());
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler());
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.POST, "/users/**", "/login")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/all/**")
                                .permitAll()
                                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .addFilter(new AppAuthenticationFilter(tokensService, authenticationManager))
                .addFilter(new AppAuthorizationFilter(tokensService, authenticationManager))
                .authenticationManager(authenticationManager);
        return httpSecurity.build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) -> handleAuthenticationError(response, authException));
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, authException) -> handleAuthenticationError(response, authException));
    }

    private void handleAuthenticationError(HttpServletResponse response, RuntimeException authException) throws IOException {
        var ex = new AuthenticationErrorDto(authException.getMessage());

        response.setStatus(403);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(ex));
        response.getWriter().flush();
        response.getWriter().close();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.CACHE_CONTROL
        ));
        corsConfiguration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.OPTIONS.name()
        ));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
