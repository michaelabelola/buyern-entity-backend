package com.buyern.buyern.Controllers;

import com.buyern.buyern.Services.UserService;
import com.buyern.buyern.dtos.ResponseDTO;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Data
public class UserController {
    final UserService userService;
    @GetMapping(path = "user")
    private ResponseEntity<ResponseDTO> getUser(@RequestParam Long id) {
        return userService.getUser(id);
    }

    @GetMapping(path = "users")
    private ResponseEntity<ResponseDTO> getUsers(@RequestBody List<Long> ids) {
        return userService.getUsers(ids);
    }
}
