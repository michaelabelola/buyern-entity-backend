package com.buyern.buyern.Models.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_sub_category_groups")
public class InventorySubCategoryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private InventoryCategory category;
}