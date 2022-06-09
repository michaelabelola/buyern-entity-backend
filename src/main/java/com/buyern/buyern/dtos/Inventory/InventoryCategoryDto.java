package com.buyern.buyern.dtos.Inventory;

import com.buyern.buyern.Models.Inventory.InventoryCategory;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class InventoryCategoryDto implements Serializable {
    private Long id;
    private String name;
    private Long entityId;
    private boolean isPrivate;

    public InventoryCategory toInventoryCategory() {
        InventoryCategory category = new InventoryCategory();
        category.setId(getId());
        category.setName(getName());
        category.setEntityId(getEntityId());
        category.setPrivate(isPrivate());
        return category;
    }

    public static InventoryCategoryDto create(InventoryCategory category) {
        return new InventoryCategoryDtoBuilder()
                .id(category.getId())
                .name(category.getName())
                .entityId(category.getEntityId())
                .isPrivate(category.isPrivate())
                .build();
    }
}
