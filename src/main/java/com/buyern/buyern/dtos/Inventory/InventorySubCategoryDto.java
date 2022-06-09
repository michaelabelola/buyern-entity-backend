package com.buyern.buyern.dtos.Inventory;

import com.buyern.buyern.Models.Inventory.InventorySubCategory;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventorySubCategoryDto implements Serializable {
    private Long id;
    private String name;
    private Long categoryId;
    private Long groupingId;
    private Long entityId;
    private boolean isPrivate;

    public static InventorySubCategoryDto create(InventorySubCategory subCategory) {
        InventorySubCategoryDto subCategoryDto = new InventorySubCategoryDto();
        subCategoryDto.setId(subCategoryDto.getId());
        subCategoryDto.setCategoryId(subCategory.getCategoryId());
        subCategoryDto.setEntityId(subCategory.getEntityId());
        subCategoryDto.setName(subCategory.getName());
        subCategoryDto.setPrivate(subCategory.isPrivate());
        subCategoryDto.setGroupingId(subCategory.getGroupingId());
        return subCategoryDto;
    }

    public InventorySubCategory toInventorySubCategory() {
        InventorySubCategory inventorySubCategory = new InventorySubCategory();
        inventorySubCategory.setId(getId());
        inventorySubCategory.setName(getName());
        inventorySubCategory.setCategoryId(getCategoryId());
        inventorySubCategory.setEntityId(getEntityId());
        inventorySubCategory.setGroupingId(getGroupingId());
        inventorySubCategory.setPrivate(isPrivate());
        return inventorySubCategory;
    }
}
