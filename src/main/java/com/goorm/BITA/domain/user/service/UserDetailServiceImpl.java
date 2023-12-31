package com.goorm.BITA.domain.user.service;

import com.goorm.BITA.domain.user.UserDetailsImpl;
import com.goorm.BITA.domain.user.domain.User;
import com.goorm.BITA.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    return new RuntimeException("Invalid email.");
                });

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(
                user.getEmail(),
                user.getPassword(),
                user.getId(),
                user.getName(),
                grantedAuthorities
        );

        return userDetailsImpl;
    }
}
