package com.buyern.buyern.Controllers;

import com.buyern.buyern.Services.EntityService;
import com.buyern.buyern.dtos.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("entity/{entityId}")
public class EntityController {
@Autowired
    EntityService entityService;
    private ResponseEntity<ResponseDTO> getEntitiesByIds() {
        return null;
    }

    @GetMapping()
    private ResponseEntity<ResponseDTO> getEntity(@PathVariable Long entityId) {
        return entityService.getEntityById(entityId);
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
