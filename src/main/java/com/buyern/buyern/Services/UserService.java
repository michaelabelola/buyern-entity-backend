package com.buyern.buyern.Services;

import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Models.User.UserSession;
import com.buyern.buyern.Repositories.UserAuthRepository;
import com.buyern.buyern.Repositories.UserRepository;
import com.buyern.buyern.dtos.ResponseDTO;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class UserService {
    final UserRepository userRepository;
    final UserAuthRepository userAuthRepository;
    final FileService fileService;

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<Optional<User>> getUser(Long id, Principal principal) {
        if (principal != null) {

            logger.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            logger.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        }
//        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userRepository.findById(id));
    }

    public ResponseEntity<ResponseDTO> getUserByUid(String uId, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(findUserByUid(uId)).build());
    }

    public ResponseEntity<ResponseDTO> getUsers(List<Long> ids) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(userRepository.findAllById(ids)).build());
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        });
    }

    private User getUserById(String id) {
        return userRepository.findById(Long.valueOf(id)).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        });
    }

    public User findUserByUid(String uid) {
        return userRepository.findByUid(uid).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        });
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new EntityNotFoundException("Email Not found");
        });
    }

}
