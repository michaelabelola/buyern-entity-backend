package com.buyern.buyern.Models.Inventory;

import com.buyern.buyern.Enums.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventory_promos")
@Data
public class InventoryPromo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String about;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startDate;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endDate;
    private State.InventoryPromo state;
    @OneToMany
    @JoinTable(name = "promo_inventories", joinColumns = @JoinColumn(name = "promo_id"), inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    private List<Inventory> inventories;
    private String image;
    /**
     * run count is the amount of  timwe this promo nas been started (<b>run</b>)*/
    private long runCount = 0L;
    /**
     * <h3>Images Array</h3>
     * {id:1, link:"www.image.com/img.jpg", tag:"coverImg", title:"get  one buy blah blah blah 😉", text:"just random unneeded text users can add"}
     */
    private String images; //all promo images json array

    public InventoryPromo() {
    }
}