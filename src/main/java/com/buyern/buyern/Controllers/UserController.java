package com.buyern.buyern.Controllers;

import com.buyern.buyern.Services.UserService;
import com.buyern.buyern.dtos.ResponseDTO;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Data
public class UserController {
    final UserService userService;
    @GetMapping(path = "user")
    private ResponseEntity<ResponseDTO> getUser(@RequestParam Long id) {
        return userService.getUser(id);
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
