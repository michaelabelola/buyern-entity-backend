package com.buyern.buyern.Services;

import com.buyern.buyern.Models.Entity.Entity;
import com.buyern.buyern.Repositories.Entity.EntityRepository;
import com.buyern.buyern.dtos.Entity.EntityDto;
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

    public ResponseEntity<ResponseDTO> getEntityById(Long entityId) {
        System.out.println(entityId.toString());
        Optional<Entity> entity = entityRepository.findById(entityId);
        if (entity.isEmpty())
            throw new RecordNotFoundException("Entity does not exist");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(EntityDto.create(entity.get())).build());
    }
}
