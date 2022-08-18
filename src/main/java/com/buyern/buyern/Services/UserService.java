package com.buyern.buyern.Services;

import com.buyern.buyern.Configs.SecurityConfiguration;
import com.buyern.buyern.Controllers.UserController;
import com.buyern.buyern.Enums.Role;
import com.buyern.buyern.Models.User.PasswordResetToken;
import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Models.User.UserAuth;
import com.buyern.buyern.Repositories.PasswordResetTokenRepository;
import com.buyern.buyern.Repositories.UserAuthRepository;
import com.buyern.buyern.Repositories.UserRepository;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    final UserRepository userRepository;
    final UserAuthRepository userAuthRepository;
    final PasswordResetTokenRepository passwordResetTokenRepository;
    final FileService fileService;

    public ResponseEntity<ResponseDTO> getUser(Long id) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        })).build());
    }

    public ResponseEntity<ResponseDTO> existsByEmail(String email) {
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("email", email);
        jsonNode.put("registered", userRepository.existsByEmail(email));
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(jsonNode).build());
    }

    @Transactional
    public ResponseEntity<ResponseDTO> registerUser(UserDto.UserSignUpDTO userDto) {
        userDto.setEmail(userDto.getEmail().toLowerCase());

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

    public ResponseEntity<ResponseDTO> getUsers(List<Long> ids) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(userRepository.findAllById(ids)).build());
    }

    @Transactional
    public ResponseEntity<ResponseDTO> uploadImage(Long id, MultipartFile multipartFile) {
        User user = getUserById(id);
        String name = "profileImage." + multipartFile.getContentType().split("/")[1];
//        File file = renameFile(multipartFile, String.valueOf(id));

        String uploadedFileLink = fileService.uploadToUsersContainer(multipartFile, user.getId() + "/" + name);
        if (uploadedFileLink.isBlank())
            throw new ServerErrorException("Unable To Upload file. Try uploading another", new Throwable("Unable To Upload file. Try uploading another"));
        user.setImage(uploadedFileLink);
//        userRepository.save(user);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(user).build());
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        });
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new EntityNotFoundException("Email Not found");
        });
    }

    public ResponseEntity<ResponseDTO> forgotPassword(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new EntityNotFoundException("Email not registered");
        }
        try {
            Optional<PasswordResetToken> optionalPRT = passwordResetTokenRepository.findByEmail(email);
            optionalPRT.ifPresent(passwordResetTokenRepository::delete);
        } catch (Exception ignored) {
        }
        User user = getUserByEmail(email);
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUserId(user.getId());
        passwordResetToken.setEmail(user.getEmail());
        PasswordResetToken pRT = passwordResetTokenRepository.save(passwordResetToken);
        //TODO: token duration
        //TODO: send mail to user email
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(String.format("check your mail: %s. Note: token only lasts 24hrs", pRT.getEmail())).build());
    }

    @Transactional
    public ResponseEntity<ResponseDTO> resetPassword(String token, String newPassword) {
        PasswordResetToken pRT = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("token is invalid or token expired"));
        userAuthRepository.updatePasswordByEmail(SecurityConfiguration.passwordEncoder().encode(newPassword), pRT.getEmail());
        passwordResetTokenRepository.deleteById(pRT.getId());
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data("password changed successfully. you can now login").build());
    }
}
