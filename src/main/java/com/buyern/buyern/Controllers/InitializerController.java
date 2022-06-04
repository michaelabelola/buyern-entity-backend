package com.buyern.buyern.Controllers;

import com.buyern.buyern.Models.AssetType;
import com.buyern.buyern.Models.EntityCategory;
import com.buyern.buyern.Models.EntityPreset;
import com.buyern.buyern.Models.Tool;
import com.buyern.buyern.Repositories.AssetTypeRepository;
import com.buyern.buyern.Repositories.EntityCategoryRepository;
import com.buyern.buyern.Repositories.EntityPresetRepository;
import com.buyern.buyern.Repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        return "Success";
    }

    @GetMapping("init2")
    private String init2() {
        createDefaultEntityPresets();
        return "Success";
    }

    private void createDefaultEntityPresets() {
        List<EntityPreset> entityPresets = new ArrayList<>();
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(1L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(2L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(3L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(4L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(6L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(11L), toolRepository.findById(7L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(1L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(3L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(4L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(5L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(6L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(12L), toolRepository.findById(7L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(1L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(2L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(3L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(4L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(5L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(6L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(7L).get()));
        entityPresets.add(new EntityPreset(new EntityCategory(13L), toolRepository.findById(8L).get()));
        entityPresetRepository.saveAll(entityPresets);
    }

    private void createDefaultTool() {
        List<Tool> tools = new ArrayList<>();
        tools.add(new Tool(1L, UUID.randomUUID().toString(), "Finance Manager", "manage your finances. all other tools require the finance manager tool. all cash transfers are handled. etc"));
        tools.add(new Tool(2L, UUID.randomUUID().toString(), "Inventory Manager", "This is a must for e commerce. Manage Inventories."));
        tools.add(new Tool(3L, UUID.randomUUID().toString(), "Customer Care", "improve relationship with you customers give them required help."));
        tools.add(new Tool(4L, UUID.randomUUID().toString(), "Customer Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(5L, UUID.randomUUID().toString(), "Asset Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(6L, UUID.randomUUID().toString(), "Employee / Stakeholders Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(7L, UUID.randomUUID().toString(), "Permissions/Role Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(8L, UUID.randomUUID().toString(), "Delivery Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(9L, UUID.randomUUID().toString(), "Location Manager", "improve relationship with you customers give them required help."));
        tools.add(new Tool(10L, UUID.randomUUID().toString(), "Order Manager", "improve relationship with you customers give them required help."));
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
