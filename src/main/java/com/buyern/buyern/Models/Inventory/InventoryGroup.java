package com.buyern.buyern.Models.Inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventory_groups")
public class InventoryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private Long entityId;
    @OneToMany
    @JoinTable(name = "inventory_groups_items", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    private List<Inventory> inventories;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;
}