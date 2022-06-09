package com.buyern.buyern.Services;

import com.buyern.buyern.Helpers.ListMapper;
import com.buyern.buyern.Models.Inventory.Inventory;
import com.buyern.buyern.Models.Inventory.InventoryDto;
import com.buyern.buyern.Models.Promo;
import com.buyern.buyern.Repositories.Inventory.InventoryRepository;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService {
    final InventoryRepository inventoryRepository;
    @Autowired
    PromoService promoService;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public ResponseEntity<ResponseDTO> createInventory(InventoryDto inventoryDto) {
//        check if category exists
//        check if custom categories exists and that they belong to entity

        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventoryDto.toInventory())).build());
    }

    public ResponseEntity<ResponseDTO> createInventories(List<InventoryDto> inventoriesDto) {
        List<Inventory> inventories = new ListMapper<InventoryDto, Inventory>().map(inventoriesDto, InventoryDto::toInventory);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.saveAll(inventories)).build());
    }

    public ResponseEntity<ResponseDTO> deleteInventory(Long inventoryId) {
        try {
            inventoryRepository.deleteById(inventoryId);
            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("Deleted Successfully").build());
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting inventory");
        }
    }

    //    TODO: don't just delete inventories
//    add to inventory archive instead for a while
    public ResponseEntity<ResponseDTO> deleteInventories(List<Long> inventoryIds) {
        try {
            inventoryRepository.deleteAllById(inventoryIds);
            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("Deleted Successfully").build());
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting inventory");
        }
    }

    public ResponseEntity<ResponseDTO> updateInventory(InventoryDto inventoryDto) {
        Inventory inventory = getInventory(inventoryDto.getId(), false);
        //TODO: update only needed columns. some columns should not be updatable
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventoryDto.toInventory())).build());
    }

    /**
     * update inventory quantity and parent inventory quantity
     */
    public ResponseEntity<ResponseDTO> updateQuantity(Long inventoryId, int newQuantity) {
        //TODO: disable inventory before updating quantity
        Inventory inventory = getInventory(inventoryId, false);
        inventory.setQty(newQuantity);
        if (inventory.getParent() != null)
            inventory.getParent().setQty(inventory.getParent().getQty() - inventory.getQty() + newQuantity);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventory)).build());
    }

    public ResponseEntity<ResponseDTO> updatePrice(Long inventoryId, Double newPrice) {
        //TODO: disable inventory before updating price
        Inventory inventory = getInventory(inventoryId, false);
        inventory.setPrice(newPrice);
        if (inventory.getParent() != null)
            inventory.getParent().setPrice(inventory.getParent().getPrice() - inventory.getPrice() + newPrice);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventory)).build());
    }


    public ResponseEntity<ResponseDTO> addPromo(Long inventoryId, Long promoId) {
        Promo promo = promoService.getPromo(promoId);
        //TODO: disable inventory before updating price
        Inventory inventory = getInventory(inventoryId, false);
        if (inventory.getPromos() == null || inventory.getPromos().isEmpty())
            inventory.setPromos(new ArrayList<>());
        inventory.getPromos().add(promo);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventory)).build());
    }

    public ResponseEntity<ResponseDTO> addPromos(Long inventoryId, List<Long> promoIds) {
        List<Promo> promos = promoService.getPromos(promoIds);
        Inventory inventory = getInventory(inventoryId, false);
        inventory.getPromos().addAll(promos);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventory)).build());
    }

    public ResponseEntity<ResponseDTO> removePromo(Long inventoryId, Long promoId) {
        Promo promo = promoService.getPromo(promoId);
        Inventory inventory = getInventory(inventoryId, false);
        inventory.getPromos().remove(promo);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventory)).build());
    }

    public ResponseEntity<ResponseDTO> removeAllPromos(Long inventoryId, List<Long> promoIds) {
        List<Promo> promos = promoService.getPromos(promoIds);
        Inventory inventory = getInventory(inventoryId, false);
        inventory.getPromos().removeAll(promos);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.save(inventory)).build());
    }

    //   TODO:  if category is custom, check if custom category is created then add to custom category, else add to category
    public ResponseEntity<ResponseDTO> addToCategory() {
        return null;
    }

    public Inventory getInventory(Long inventoryId, @Nullable boolean isParent) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        if (inventory.isEmpty())
            if (isParent)
                throw new RecordNotFoundException("Parent inventory not found");
            else
                throw new RecordNotFoundException("Inventory with id does not exist");
        return inventory.get();
    }
    public List<Inventory> getInventoryVariations(Long inventoryId) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryId);
        List<Inventory> variations = inventoryRepository.findAllByParent(inventory);
        return variations;
    }

}
