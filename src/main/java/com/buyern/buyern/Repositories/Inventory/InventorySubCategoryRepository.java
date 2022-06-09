package com.buyern.buyern.Repositories.Inventory;

import com.buyern.buyern.Models.Inventory.InventorySubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventorySubCategoryRepository extends JpaRepository<InventorySubCategory, Long> {
}