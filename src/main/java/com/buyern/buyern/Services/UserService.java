package com.buyern.buyern.Services;

import com.buyern.buyern.Controllers.UserController;
import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Repositories.UserRepository;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Data
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    final UserRepository userRepository;
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

    public ResponseEntity<ResponseDTO> createUser(UserDto userDto) {
        User user = userDto.toModel();
        user.setId(null);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(userRepository.save(user)).build());
    }

    public ResponseEntity<ResponseDTO> getUsers(List<Long> ids) {
        return null;
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
}
