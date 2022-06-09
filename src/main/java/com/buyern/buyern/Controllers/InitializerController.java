package com.buyern.buyern.Controllers;

import com.buyern.buyern.Models.*;
import com.buyern.buyern.Models.Entity.EntityCategory;
import com.buyern.buyern.Models.Entity.EntityPreset;
import com.buyern.buyern.Models.Inventory.InventoryCategory;
import com.buyern.buyern.Models.Inventory.InventorySubCategory;
import com.buyern.buyern.Models.Inventory.InventorySubCategoryGroup;
import com.buyern.buyern.Repositories.*;
import com.buyern.buyern.Repositories.Entity.EntityCategoryRepository;
import com.buyern.buyern.Repositories.Entity.EntityPresetRepository;
import com.buyern.buyern.Repositories.Inventory.InventoryCategoryRepository;
import com.buyern.buyern.Repositories.Inventory.InventorySubCategoryGroupRepository;
import com.buyern.buyern.Repositories.Inventory.InventorySubCategoryRepository;
import com.buyern.buyern.dtos.Inventory.InventorySubCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class InitializerController {
    final
    AssetTypeRepository assetTypeRepository;
    final
    EntityCategoryRepository entityCategoryRepository;
    final
    ToolRepository toolRepository;
    final
    EntityPresetRepository entityPresetRepository;
    @Autowired
    InventoryCategoryRepository inventoryCategoryRepository;
    @Autowired
    InventorySubCategoryRepository inventorySubCategoryRepository;
    @Autowired
    InventorySubCategoryGroupRepository inventorySubCategoryGroupRepository;

    public InitializerController(AssetTypeRepository assetTypeRepository, EntityCategoryRepository entityCategoryRepository, ToolRepository toolRepository, EntityPresetRepository entityPresetRepository) {
        this.assetTypeRepository = assetTypeRepository;
        this.entityCategoryRepository = entityCategoryRepository;
        this.toolRepository = toolRepository;
        this.entityPresetRepository = entityPresetRepository;
    }

    @GetMapping("init1")
    private String init() {
        createDefaultAssetTypes();
        createDefaultEntityCategory();
        createDefaultTool();
        createInventoryCategories();
        return "Success";
    }

    @GetMapping("init2")
    private String init2() {
        createDefaultEntityPresets();
        createInventorySubCategoryGroups();
        return "Success";
    }

    @GetMapping("init3")
    private String init3() {
        createInventorySubCategories();
        return "Success";
    }

    private void createInventorySubCategoryGroups() {
        List<InventorySubCategoryGroup> inventorySubCategoryGroups = new ArrayList<>();
        inventorySubCategoryGroups.add(new InventorySubCategoryGroup(1L, "Footwear", null));
        inventorySubCategoryGroupRepository.saveAll(inventorySubCategoryGroups);
//        inventorySubCategoryGroups.add(new InventorySubCategoryGroup(1L, "Footwear", InventoryCategoryDto.builder().id(1L).build().toInventoryCategory(), List.of(
//                InventorySubCategoryDto.builder().id(1L).build().toInventorySubCategory(),
//                InventorySubCategoryDto.builder().id(2L).build().toInventorySubCategory(),
//                InventorySubCategoryDto.builder().id(3L).build().toInventorySubCategory(),
//                InventorySubCategoryDto.builder().id(4L).build().toInventorySubCategory()
//        )));
    }

    private void createInventoryCategories() {
        List<InventoryCategory> inventoryCategories = new ArrayList<>();
        inventoryCategories.add(new InventoryCategory(1L, "Apparels", null, false));
        inventoryCategories.add(new InventoryCategory(2L, "Electronics", null, false));
        inventoryCategories.add(new InventoryCategory(3L, "furniture", null, false));
        inventoryCategories.add(new InventoryCategory(4L, "Cars", null, false));
        inventoryCategories.add(new InventoryCategory(5L, "Houses", null, false));
        inventoryCategories.add(new InventoryCategory(6L, "Heavy Machines", null, false));
        inventoryCategories.add(new InventoryCategory(7L, "Factory Equipments", null, false));
        inventoryCategories.add(new InventoryCategory(8L, "Building Materials", null, false));
        inventoryCategoryRepository.saveAll(inventoryCategories);
    }

    private void createInventorySubCategories() {
        List<InventorySubCategory> inventorySubCategories = new ArrayList<>();
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(1L).name("Coats").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(2L).name("Shirts").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(3L).name("Belts").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(4L).name("boots").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(5L).name("Sandals").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(6L).name("Trainers").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(7L).name("Slides").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(InventorySubCategoryDto.builder().id(8L).name("Rings").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        List<InventorySubCategory> inventorySubCategoryList = inventorySubCategoryRepository.saveAll(inventorySubCategories);
    }

    private void createDefaultEntityPresets() {
        List<EntityPreset> entityPresets = new ArrayList<>();
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(15L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(16L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(17L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(18L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(20L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(21L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(15L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(17L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(18L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(19L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(20L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(21L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(15L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(16L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(17L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(18L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(19L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(20L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(21L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(22L).get()));
        entityPresetRepository.saveAll(entityPresets);
    }

    private void createDefaultTool() {
        List<Tool> tools = new ArrayList<>();
        tools.add(new Tool(1L, "Finance Manager", "manage your finances. all other tools require the finance manager tool. all cash transfers are handled. etc"));
        tools.add(new Tool(2L, "Inventory Manager", "This is a must for e commerce. Manage Inventories."));
        tools.add(new Tool(3L, "Customer Care", "improve relationship with you customers give them required help."));
        tools.add(new Tool(4L, "Customer Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(5L, "Asset Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(6L, "Employee / Stakeholders Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(7L, "Permissions/Role Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(8L, "Delivery Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(9L, "Location Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(10L, "Order Manager", "improve relationship with you customers give them required help."));
        toolRepository.saveAll(tools);
    }

    private void createDefaultEntityCategory() {
        List<EntityCategory> entityCategories = new ArrayList<>();
        entityCategories.add(new EntityCategory(1L, "ECOMMERCE", "E-Commerce"));
        entityCategories.add(new EntityCategory(2L, "FINANCE", "Finance"));
        entityCategories.add(new EntityCategory(3L, "FOOD_SALE", "Food Sale"));
        entityCategories.add(new EntityCategory(4L, "DELIVERY", "Delivery"));
        entityCategoryRepository.saveAll(entityCategories);
    }

    private void createDefaultAssetTypes() {
        List<AssetType> assetTypes = new ArrayList<>();
        assetTypes.add(new AssetType(1L, "OFFICE", "BUILDING", null));
        assetTypes.add(new AssetType(2L, "WAREHOUSE", "BUILDING", null));
        assetTypes.add(new AssetType(3L, "SHOWROOM", "BUILDING", null));
        assetTypes.add(new AssetType(4L, "EATERY", "BUILDING", null));
        assetTypes.add(new AssetType(5L, "POWER GENERATOR", "MACHINERY", null));
        assetTypes.add(new AssetType(6L, "CARS", "VEHICLE", null));
        assetTypes.add(new AssetType(7L, "MOTORCYCLES", "VEHICLE", null));
        assetTypes.add(new AssetType(8L, "GAMMA", "CUSTOM", null));
        assetTypes.add(new AssetType(9L, "BUSES", "VEHICLE", null));
        assetTypes.add(new AssetType(10L, "TRUCKS", "VEHICLE", null));
        assetTypeRepository.saveAll(assetTypes);
    }

}
