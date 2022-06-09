package com.buyern.buyern.Models.Inventory;

import com.buyern.buyern.Models.Promo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventories")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String name;
    private Long entityId;
    private String about;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timeAdded;
    private Long addedBy;//user Id
    private int qty;
    private double price;
    /**
     * <h3>Promo Json Array</h3>
     * {promoId:123, promoName:"Summer Discount", }
     */
    @OneToMany
    @JoinTable(name = "promo_inventory_ids", joinColumns = @JoinColumn(name = "inventory_id"), inverseJoinColumns = @JoinColumn(name = "promo_id"))
    private List<Promo> promos; // promo json array
    @OneToOne
    @JoinColumn(name = "parent_id")
    private Inventory parent;
    private String image;
    private String images; // image link json array
    @OneToOne
    @JoinColumn(name = "category_id")
    private InventoryCategory category;
    /**
     * this is an array of custom categories created by the entities
     */
    @OneToMany
    @JoinTable(name = "custom_categories_id", joinColumns = @JoinColumn(name = "inventory_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<InventoryCategory> customCategories;
}