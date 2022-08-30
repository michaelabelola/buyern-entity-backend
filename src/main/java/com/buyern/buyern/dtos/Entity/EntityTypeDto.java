package com.buyern.buyern.dtos.Entity;

import com.buyern.buyern.Models.Entity.EntityType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EntityTypeDto implements Serializable {
    private Long id;
    private String name;
    private String value;

    public EntityType toEntityCategory() {
        EntityType entityType = new EntityType();
        entityType.setId(getId());
        entityType.setName(getName());
        entityType.setUid(getValue());
        return entityType;
    }

    public static EntityTypeDto create(EntityType entityType) {
        if (entityType == null)
            return null;
        return EntityTypeDto.builder()
                .id(entityType.getId())
                .name(entityType.getName())
                .value(entityType.getUid())
                .build();
    }
}
