package com.buyern.buyern.Models;

import com.buyern.buyern.Enums.State;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.UUID;

@Entity(name = "assets")
@Data
public class Asset {
    @Id
    @Column(nullable = false, unique = true)
    private String id;
    private Long entityId;
    @OneToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "type_id")
    private AssetType type;
    private String assignee;
    private String manager;
    @Enumerated(EnumType.STRING)
    private State.Asset state;
    private boolean locationSameWIthAssignee;
    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "location_id")
    private Location location;
    private String images;
}
