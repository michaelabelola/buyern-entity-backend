package com.buyern.buyern.Repositories.Inventory;

import com.buyern.buyern.Models.Inventory.InventorySubCategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventorySubCategoryGroupRepository extends JpaRepository<InventorySubCategoryGroup, Long> {
}