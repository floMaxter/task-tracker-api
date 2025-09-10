package com.projects.tasktracker.auth.security;

import com.projects.tasktracker.auth.client.user.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userCredentialsByEmail = userServiceClient.getUserCredentialsByEmail(email);
        return CustomUserDetails.builder()
                .id(userCredentialsByEmail.id())
                .username(userCredentialsByEmail.email())
                .password(userCredentialsByEmail.password())
                .build();
    }
}
