package com.buyern.buyern.Controllers;

import com.buyern.buyern.Services.UserService;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Data
public class UserController {
    final UserService userService;

    @GetMapping(path = "user")
    private ResponseEntity<ResponseDTO> getUser(@RequestParam Long id) {
        return userService.getUser(id);
    }

    @GetMapping(path = "user/email/isRegistered")
    private ResponseEntity<ResponseDTO> isRegistered(@RequestParam String email) {
        return userService.existsByEmail(email.toLowerCase());
    }
    @PostMapping(path = "signup")
    private ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDto.UserSignUpDTO user) {
        return userService.registerUser(user);
    }
    @PostMapping(path = "login?error")
    private ResponseEntity<ResponseDTO> errorZ() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder().message("XX").data("Failed").data("User Error Signing In").build());
       }
    @GetMapping(path = "/signIn/success")
    private ResponseEntity<ResponseDTO> loginSuccess() {
        return ResponseEntity.ok(ResponseDTO.builder().message("00").data("SUCCESS").data("User signed in successfully").build());
    }
    @GetMapping(path = "/signIn/fail")
    private ResponseEntity<ResponseDTO> loginFail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder().message("XX").data("Failed").data("User Error Signing In").build());
    }
    @PostMapping(path = "signOut/success")
    private ResponseEntity<ResponseDTO> loginUser() {
        return ResponseEntity.ok(ResponseDTO.builder().message("00").data("SUCCESS").data("User signed Out successfully").build());
    }
    @PostMapping(path = "forgotPassword")
    private ResponseEntity<ResponseDTO> forgotPassword(@Valid @NotNull @Email @RequestParam String email) {
        return userService.forgotPassword(email);
    }
    @PostMapping(path = "resetPassword")
    private ResponseEntity<ResponseDTO> resetPassword(@Valid @NotNull @RequestParam String token, @NotNull String newPassword) {
        return userService.resetPassword(token, newPassword);
    }
    @PostMapping(path = "user")
    private ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserDto.UserSignUpDTO user) {
        return userService.registerUser(user);
    }
    @PostMapping(path = "user/image")
    private ResponseEntity<ResponseDTO> uploadImage(@NotNull @RequestBody MultipartFile image, @RequestParam Long id) {
        return userService.uploadImage(id, image);
    }
    @GetMapping(path = "users")
    private ResponseEntity<ResponseDTO> getUsers(@RequestBody List<Long> ids) {
        return userService.getUsers(ids);
    }
}
