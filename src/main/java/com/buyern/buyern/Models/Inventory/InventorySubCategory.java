package com.buyern.buyern.Models.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "inventory_sub_categories")
public class InventorySubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String name;
    private Long categoryId;
    private Long groupingId;
    private Long entityId;
    private boolean isPrivate;
}