package com.buyern.buyern.Services;

import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Repositories.UserRepository;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.dtos.UserDto;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Data
public class UserService {
    final UserRepository userRepository;

    public ResponseEntity<ResponseDTO> getUser(Long id) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User not found");
        })).build());
    }

    public ResponseEntity<ResponseDTO> createUser(UserDto userDto) {
        User user = userDto.toModel();
        user.setId(null);
        userRepository.ex
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(userRepository.save(user)).build());
    }

    public ResponseEntity<ResponseDTO> getUsers(List<Long> ids) {
        return null;
    }
}
