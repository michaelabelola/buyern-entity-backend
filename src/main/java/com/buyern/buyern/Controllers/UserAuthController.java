package com.buyern.buyern.Controllers;

import com.buyern.buyern.Services.UserAuthService;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "user/auth")
@Data
public class UserAuthController {
    final UserAuthService userAuthService;
    @GetMapping(path = "email/isRegistered")
    private ResponseEntity<ResponseDTO> isRegistered(@RequestParam String email) {
        return userAuthService.existsByEmail(email.toLowerCase());
    }
    @PostMapping(path = "signup")
    private ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDto.UserSignUpDTO user) {
        return userAuthService.registerUser(user);
    }
    @GetMapping(path = "/signIn/success")
    private ResponseEntity<ResponseDTO> loginSuccess(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
//        return ResponseEntity.ok(ResponseDTO.builder().message("00").data("SUCCESS").data("User signed in successfully").build());
        return userAuthService.loginSuccess(auth);
    }
    @GetMapping(path = "/signIn/fail")
    private ResponseEntity<ResponseDTO> loginFail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder().message("XX").data("Failed").data("User Error Signing In").build());
    }
    @GetMapping(path = "signOut/success")
    private ResponseEntity<ResponseDTO> loginUser() {
        return ResponseEntity.ok(ResponseDTO.builder().message("00").data("SUCCESS").data("User signed Out successfully").build());
    }
    @PostMapping(path = "forgotPassword")
    private ResponseEntity<ResponseDTO> forgotPassword(@Valid @NotNull @Email @RequestParam String email) {
        return userAuthService.forgotPassword(email);
    }
    @PostMapping(path = "resetPassword")
    private ResponseEntity<ResponseDTO> resetPassword(@Valid @NotNull @RequestParam String email, @Valid @NotNull @RequestParam String token, @RequestParam @NotNull String newPassword) {
        return userAuthService.resetPassword(email,token, newPassword);
    }
}
