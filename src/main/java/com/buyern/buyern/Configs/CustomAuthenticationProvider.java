package com.buyern.buyern.Configs;

import com.buyern.buyern.Models.User.UserAuth;
import com.buyern.buyern.Repositories.UserAuthRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@Data
public class CustomAuthenticationProvider implements AuthenticationProvider {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    final UserAuthRepository userAuthRepository;

    // TODO:
    // In this function we need to connect with identity provider
    // and validate the user
    // we are hardcoding for a single user for demo purposes
    UserDetails isValidUser(String email, String password) {
        UserAuth userAuth = userAuthRepository.findByEmailAndPassword(email.toLowerCase(), password).orElseThrow(() -> new BadCredentialsException("Email or password Incorrect"));
        UserDetails user = User
                .withUsername(userAuth.getEmail())
                .password(userAuth.getPassword())
                .roles("ADMIN")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
//                .roles(userAuth.getRole().name())
                .build();
        try {
            logger.info("On Login : User Auth Details: {}", new ObjectMapper().writeValueAsString(user));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = isValidUser(username, password);

        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Incorrect user credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType
                .equals(UsernamePasswordAuthenticationToken.class);
    }
}
