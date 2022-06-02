package com.buyern.buyern.Controllers;

import com.buyern.buyern.dtos.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{entityId}")
public class EntityController {

    private ResponseEntity<ResponseDTO> getEntitiesByIds() {
        return null;
    }
    private ResponseEntity<ResponseDTO> getEntity() {
        return null;
    }
    private ResponseEntity<ResponseDTO> editEntity() {
        return null;
    }
    private ResponseEntity<ResponseDTO> registerEntity() {
        return null;
    }
    private ResponseEntity<ResponseDTO> deleteEntity() {
        return null;
    }
    private ResponseEntity<ResponseDTO> deactivateEntity() {
        return null;
    }
    private ResponseEntity<ResponseDTO> activateEntity() {
        return null;
    }
}
