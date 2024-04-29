package com.app.application.service.user;

import com.app.domain.user_management.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(userFromDb -> new User(
                        userFromDb.getUsername(),
                        userFromDb.getPassword(),
                        userFromDb.isActive(), true, true, true,
                        List.of(new SimpleGrantedAuthority(userFromDb.getRole().toString()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
