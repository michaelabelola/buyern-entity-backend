package com.buyern.buyern.Repositories.Inventory;

import com.buyern.buyern.Models.Inventory.InventoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryCategoryRepository extends JpaRepository<InventoryCategory, Long> {
}