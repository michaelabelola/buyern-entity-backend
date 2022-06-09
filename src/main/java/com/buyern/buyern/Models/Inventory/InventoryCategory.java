package com.buyern.buyern.Models.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
/**
 * <b>Examples: </b>Apparels, Heavy Machines, furniture, Electronics, Houses, Building Materials, Factory Equipments */
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_categories")
public class InventoryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String name;
    private Long entityId;
    private boolean isPrivate;
}