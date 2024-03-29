package com.buyern.buyern.Controllers;

import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Services.UserService;
import com.buyern.buyern.dtos.ResponseDTO;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@Data
public class UserController {
    final UserService userService;

    @GetMapping(path = "user")
    private ResponseEntity<Optional<User>> getUser(@RequestParam Long id, Principal principal) {
        return userService.getUser(id, principal);
    }

    @GetMapping(path = "userByUid")
    private ResponseEntity<ResponseDTO> getUser(@RequestParam String uid, Principal principal) {
        return userService.getUserByUid(uid, principal);
    }

    @GetMapping(path = "users")
    private ResponseEntity<ResponseDTO> getUsers(@RequestBody List<Long> ids) {
        return userService.getUsers(ids);
    }
}
