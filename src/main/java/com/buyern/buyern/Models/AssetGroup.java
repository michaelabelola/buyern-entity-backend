package com.buyern.buyern.Models;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.UUID;

@Entity(name = "asset_groups")
@Data
public class AssetGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private String id;
    @Column(nullable = false)
    private String name;
    private String about;
    private String manager;
}
