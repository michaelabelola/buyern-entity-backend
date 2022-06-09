package com.buyern.buyern.Controllers;

import com.buyern.buyern.Models.Inventory.Inventory;
import com.buyern.buyern.Models.Inventory.InventoryDto;
import com.buyern.buyern.Services.InventoryService;
import com.buyern.buyern.dtos.ResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory")
public class InventoryController {
    final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("")
    private ResponseEntity<ResponseDTO> getInventory(@RequestParam Long inventoryId) {
        Inventory inventory = inventoryService.getInventory(inventoryId, false);
        if (inventory.getParent() == null)
            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(inventory).build());

        List<Inventory> variations = inventoryService.getInventoryVariations(inventoryId);
        ObjectNode mainInventory = new ObjectMapper().valueToTree(inventory.getParent());
        mainInventory.putPOJO("variations", variations);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(mainInventory).build());

    }

    @PostMapping("")
    private ResponseEntity<ResponseDTO> create(@RequestBody InventoryDto inventory) {
        return inventoryService.createInventory(inventory);
    }
}
