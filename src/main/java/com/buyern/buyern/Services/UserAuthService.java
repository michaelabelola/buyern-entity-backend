package com.buyern.buyern.Services;

import com.buyern.buyern.Configs.SecurityConfiguration;
import com.buyern.buyern.Enums.Role;
import com.buyern.buyern.Enums.TokenType;
import com.buyern.buyern.Models.User.CustomToken;
import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Models.User.UserAuth;
import com.buyern.buyern.Repositories.CustomTokenRepository;
import com.buyern.buyern.Repositories.UserAuthRepository;
import com.buyern.buyern.Repositories.UserRepository;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import com.buyern.buyern.exception.EntityAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class UserAuthService {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);
    final UserAuthRepository userAuthRepository;
    final UserRepository userRepository;
    final CustomTokenRepository customTokenRepository;

    public ResponseEntity<ResponseDTO> existsByEmail(String email) {
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("email", email);
        jsonNode.put("registered", userAuthRepository.existsByEmail(email));
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(jsonNode).build());
    }

    @Transactional
    public ResponseEntity<ResponseDTO> registerUser(UserDto.UserSignUpDTO userDto) {
        userDto.setEmail(userDto.getEmail().toLowerCase());
        if (userAuthRepository.existsByEmail(userDto.getEmail()))
            throw new EntityAlreadyExistsException("Email already registered");

        User user = userDto.toModel();
        UserAuth userAuth = new UserAuth();

        User savedUser = userRepository.save(user);
        userAuth.setId(savedUser.getId());
        userAuth.setEmail(savedUser.getEmail());
        userAuth.setPassword(SecurityConfiguration.passwordEncoder().encode(userDto.getPassword()));
        userAuth.setRole(Role.ADMIN);
        userAuth.setVerified(false);

        UserAuth savedUserAuth = userAuthRepository.save(userAuth);
        ObjectNode objectNode = new ObjectMapper().createObjectNode().putPOJO("userAuth", savedUserAuth).putPOJO("user", savedUser);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(objectNode).build());

    }

    public ResponseEntity<ResponseDTO> forgotPassword(String email) {
        email = email.toLowerCase();
        if (!userRepository.existsByEmail(email))
            throw new EntityNotFoundException("Email not registered");
        if (customTokenRepository.existsByEmailAndType(email, TokenType.PASSWORD_RESET))
            customTokenRepository.deleteByEmail(email);

        User user = getUserByEmail(email);
        CustomToken token = new CustomToken();
        token.setToken(UUID.randomUUID().toString());
        token.setType(TokenType.PASSWORD_RESET);
        token.setUserId(user.getId());
        token.setEmail(user.getEmail());
        CustomToken token1 = customTokenRepository.save(token);
        //TODO: token duration
        //TODO: send mail to user email
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(String.format("check your mail: %s. Note: token only lasts 24hrs", token1.getEmail())).build());
    }

    @Transactional
    public ResponseEntity<ResponseDTO> resetPassword(String email, String token, String newPassword) {
        CustomToken token1 = customTokenRepository.findByEmailAndToken(email, token).orElseThrow(() -> new EntityNotFoundException("token is invalid or token expired"));
        userAuthRepository.updatePasswordById(SecurityConfiguration.passwordEncoder().encode(newPassword), token1.getUserId());
        customTokenRepository.deleteById(token1.getId());
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data("password changed successfully. you can now login").build());
    }

    public ResponseEntity<ResponseDTO> loginSuccess(Authentication auth) {
//       Principal principal = (Principal) auth.getPrincipal();
//       logger.info(auth.getName());
//       logger.info(String.valueOf(auth.isAuthenticated()));
        logger.info((String) auth.getPrincipal());
        return ResponseEntity.ok(ResponseDTO.builder().message("00").data("SUCCESS").data(userRepository.findById(Long.valueOf(String.valueOf(auth.getPrincipal())))).build());
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new EntityNotFoundException("Email Not found");
        });
    }
}
