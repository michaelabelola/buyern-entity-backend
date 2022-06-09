package com.buyern.buyern.dtos.Inventory;

import com.buyern.buyern.Models.Inventory.InventorySubCategoryGroup;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InventorySubCategoryGroupDto implements Serializable {
    private Long id;
    private String name;
    private InventoryCategoryDto category;
    private List<InventorySubCategoryDto> subCategories;

    public static InventorySubCategoryGroupDto inventorySubCategoryGroupDto(InventorySubCategoryGroup inventorySubCategoryGroup) {
        InventorySubCategoryGroupDto inventorySubCategoryGroupDto = new InventorySubCategoryGroupDto();
        inventorySubCategoryGroupDto.setId(inventorySubCategoryGroup.getId());
        inventorySubCategoryGroupDto.setName(inventorySubCategoryGroup.getName());
        inventorySubCategoryGroupDto.setCategory(InventoryCategoryDto.create(inventorySubCategoryGroup.getCategory()));
        return inventorySubCategoryGroupDto;
    }

    public InventorySubCategoryGroup inventorySubCategoryGroup() {
        InventorySubCategoryGroup inventorySubCategoryGroup = new InventorySubCategoryGroup();
        inventorySubCategoryGroup.setId(getId());
        inventorySubCategoryGroup.setName(getName());
        inventorySubCategoryGroup.setCategory(getCategory().toInventoryCategory());
        return inventorySubCategoryGroup;
    }
}
