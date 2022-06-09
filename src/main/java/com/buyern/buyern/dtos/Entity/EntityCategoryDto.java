package com.buyern.buyern.dtos.Entity;

import com.buyern.buyern.Models.Entity.EntityCategory;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EntityCategoryDto implements Serializable {
    private Long id;
    private String name;
    private String value;

    public EntityCategory toEntityCategory() {
        EntityCategory entityCategory = new EntityCategory();
        entityCategory.setId(getId());
        entityCategory.setName(getName());
        entityCategory.setValue(getValue());
        return entityCategory;
    }

    public static EntityCategoryDto create(EntityCategory entityCategory) {
        if (entityCategory == null)
            return null;
        return EntityCategoryDto.builder()
                .id(entityCategory.getId())
                .name(entityCategory.getName())
                .value(entityCategory.getValue())
                .build();
    }
}
