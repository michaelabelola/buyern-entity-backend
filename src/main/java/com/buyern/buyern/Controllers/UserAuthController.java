package com.buyern.buyern.Controllers;

import com.azure.core.annotation.BodyParam;
import com.buyern.buyern.Models.User.UserAuth;
import com.buyern.buyern.Services.UserAuthService;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import com.buyern.buyern.exception.CustomExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "user/auth")
@Data
public class UserAuthController {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserAuthController.class);
    final UserAuthService userAuthService;

    @GetMapping(path = "email/isRegistered")
    private ResponseEntity<ResponseDTO> isRegistered(@RequestParam String email) {
        return userAuthService.existsByEmail(email.toLowerCase());
    }

    @PostMapping(path = "signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    private ResponseEntity<ResponseDTO> registerUser(
            @NotBlank(message = "First name is mandatory") String firstName,
            @NotBlank(message = "Last name is mandatory") String lastName,
            @NotBlank(message = "email is mandatory") @Email(message = "email is mandatory") String email,
            @NotBlank(message = "phone is mandatory")
                    String phone,
            @NotBlank(message = "Date Of Birth is mandatory")
            String dob,
            @NotBlank(message = "address is mandatory") String address,
            String address2,
            @NotNull(message = "State is mandatory") Long state,
            @NotNull(message = "Country is mandatory") Long country,
            Long city,
            @NotNull(message = "Password is mandatory") String password,
            @BodyParam("profileImage") MultipartFile profileImage
    ) throws ParseException, HttpMediaTypeNotSupportedException {
        UserDto.UserSignUpDTO userDto = new UserDto.UserSignUpDTO();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPhone(phone);
        userDto.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob.strip()));
        userDto.setAddress(address);
        userDto.setAddress2(address2);
        userDto.setState(state);
        userDto.setCountry(country);
        userDto.setCity(city);
        userDto.setPassword(password);
        userDto.setProfileImage(profileImage);
        return userAuthService.registerUser(userDto);
    }

    @GetMapping(path = "/signIn/success")
    private ResponseEntity<ResponseDTO> loginSuccess(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
        List<Cookie> cookieList = Arrays.stream(request.getCookies()).toList();
        logger.error(cookieList.toString());
        return userAuthService.loginSuccess(auth);
    }

    @GetMapping(path = "/signIn/fail")
    private ResponseEntity<ResponseDTO> loginFail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder().message("XX").data("Failed").data("Email or Password Incorrect").build());
    }

    @PostMapping(path = "/signIn")
    private ResponseEntity<ResponseDTO> signIn(@Valid @RequestBody UserDto.UserLoginDto user, HttpServletResponse response) throws JsonProcessingException {
        return userAuthService.signIn(user, response);
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
        return userAuthService.resetPassword(email, token, newPassword);
    }
}
