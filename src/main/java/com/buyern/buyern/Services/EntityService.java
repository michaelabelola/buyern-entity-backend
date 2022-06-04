package com.buyern.buyern.Services;

import com.buyern.buyern.Models.Entity;
import com.buyern.buyern.Repositories.EntityRepository;
import com.buyern.buyern.dtos.EntityDto;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.exception.RecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntityService {
    final EntityRepository entityRepository;

    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public ResponseEntity<ResponseDTO> getEntityByEntityId(String entityId) {
        Optional<Entity> entity = entityRepository.findByEntityId(entityId);
        if (entity.isEmpty())
            throw new RecordNotFoundException("Entity does not exist");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(EntityDto.create(entity.get())).build());
    }
}
