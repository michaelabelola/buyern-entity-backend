package com.buyern.buyern.Models.Inventory;

import com.buyern.buyern.Helpers.ListMapper;
import com.buyern.buyern.Models.Promo;
import com.buyern.buyern.dtos.Inventory.InventoryCategoryDto;
import com.buyern.buyern.dtos.PromoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class InventoryDto {
    private Long id;
    private String name;
    private Long entityId;
    private String about;
    private Date timeAdded;
    private Long addedBy;
    private int qty;
    private double price;
    private List<PromoDto> promos;
    private InventoryDto parent;
    private String image;
    private List images;
    private InventoryCategoryDto category;
    private List<InventoryCategoryDto> customCategories;

    public static InventoryDto create(Inventory inventory) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setId(inventory.getId());
        inventoryDto.setName(inventory.getName());
        inventoryDto.setEntityId(inventory.getEntityId());
        inventoryDto.setAbout(inventory.getAbout());
        inventoryDto.setTimeAdded(inventory.getTimeAdded());
        inventoryDto.setAddedBy(inventory.getAddedBy());
        inventoryDto.setQty(inventory.getQty());
        inventoryDto.setPrice(inventory.getPrice());
        inventoryDto.setPromos(new ListMapper<Promo, PromoDto>().map(inventory.getPromos(), PromoDto::create));
        inventoryDto.setParent(InventoryDto.create(inventory.getParent()));
        inventoryDto.setImage(inventory.getImage());
        ObjectMapper objectMapper = new ObjectMapper();
        List imagesDto;
        try {
            imagesDto = objectMapper.readValue(inventory.getImages(), List.class);
        } catch (JsonProcessingException e) {
            imagesDto = null;
        }
        inventoryDto.setImages(imagesDto);
        inventoryDto.setCategory(InventoryCategoryDto.create(inventory.getCategory()));
        inventoryDto.setCustomCategories(new ListMapper<InventoryCategory, InventoryCategoryDto>().map(inventory.getCustomCategories(), InventoryCategoryDto::create));
        return inventoryDto;
    }

    public Inventory toInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(getId());
        inventory.setName(getName());
        inventory.setEntityId(getEntityId());
        inventory.setAbout(getAbout());
        inventory.setTimeAdded(getTimeAdded());
        inventory.setAddedBy(getAddedBy());
        inventory.setQty(getQty());
        inventory.setPrice(getPrice());
        inventory.setPromos(new ListMapper<PromoDto, Promo>().map(getPromos(), PromoDto::toPromo));
        inventory.setParent(getParent().toInventory());
        inventory.setImage(getImage());
        try {
            inventory.setImages(new ObjectMapper().writeValueAsString(getImages()));
        } catch (JsonProcessingException e) {
            inventory.setImages(null);
        }
        inventory.setCategory(getCategory().toInventoryCategory());
        inventory.setCustomCategories(new ListMapper<InventoryCategoryDto, InventoryCategory>().map(getCustomCategories(), InventoryCategoryDto::toInventoryCategory));
        return inventory;
    }
}
