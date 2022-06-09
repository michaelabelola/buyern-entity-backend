package com.buyern.buyern.dtos.Entity;

import com.buyern.buyern.Models.Entity.EntityPreset;
import com.buyern.buyern.dtos.ToolDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EntityPresetDto implements Serializable {
    private Long id;
    private EntityCategoryDto category;
    private ToolDto tool;

    public EntityPreset toEntityPreset() {
        EntityPreset entityPreset = new EntityPreset();
        entityPreset.setId(getId());
        entityPreset.setTool(getTool().toTool());
        entityPreset.setCategory(getCategory().toEntityCategory());
        return entityPreset;
    }

    public static EntityPresetDto create(EntityPreset entityPreset) {
        return EntityPresetDto.builder()
                .id(entityPreset.getId())
                .category(EntityCategoryDto.create(entityPreset.getCategory()))
                .tool(ToolDto.create(entityPreset.getTool()))
                .build();
    }
}
