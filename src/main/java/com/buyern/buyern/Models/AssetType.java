package com.buyern.buyern.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.UUID;

@Entity(name = "asset_types")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AssetType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;
    //OFFICE, WAREHOUSE, CARS, MOTORCYCLES, VEHICLES
    @Column(nullable = false, unique = true)
    private String name;
    //VEHICLE, BUILDING
    private String typeGroup;
    private UUID entityId;
}
