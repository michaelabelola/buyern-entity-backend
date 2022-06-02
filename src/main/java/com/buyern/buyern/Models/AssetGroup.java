package com.buyern.buyern.Models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "asset_groups")
@Data
public class AssetGroup {
    @Id
    @Column(nullable = false, unique = true)
    private String id = UUID.randomUUID().toString();
    @Column(nullable = false)
    private String name;
    private String about;
    private String manager;
}
