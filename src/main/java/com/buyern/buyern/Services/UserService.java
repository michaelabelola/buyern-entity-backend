package com.buyern.buyern.Services;

import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Repositories.UserAuthRepository;
import com.buyern.buyern.Repositories.UserRepository;
import com.buyern.buyern.dtos.ResponseDTO;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Data
public class UserService {
    final UserRepository userRepository;
    final UserAuthRepository userAuthRepository;
    final FileService fileService;

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);
    public ResponseEntity<ResponseDTO> getUser(Long id) {
        logger.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        })).build());
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

    private User getUserById(String id) {
        return userRepository.findById(Long.valueOf(id)).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        });
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new EntityNotFoundException("Email Not found");
        });
    }

}
