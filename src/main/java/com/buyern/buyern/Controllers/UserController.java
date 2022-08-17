package com.buyern.buyern.Controllers;

import com.buyern.buyern.Services.UserService;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
        return userService.existsByEmail(email);
    }
    @PostMapping(path = "user")
    private ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserDto user) {
        return userService.createUser(user);
    }
    @PostMapping(path = "user/image")
    private ResponseEntity<ResponseDTO> uploadImage(@RequestBody MultipartFile image, @RequestParam Long id) {
        return userService.uploadImage(id, image);
    }



    @GetMapping(path = "users")
    private ResponseEntity<ResponseDTO> getUsers(@RequestBody List<Long> ids) {
        return userService.getUsers(ids);
    }
}
