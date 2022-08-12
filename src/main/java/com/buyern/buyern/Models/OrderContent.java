package com.buyern.buyern.Models;

import com.buyern.buyern.Enums.BuyernEntityType;
import com.buyern.buyern.Enums.OrderType;
import com.buyern.buyern.Models.Location.Location;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "order_contents")
@Data
public class OrderContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String handlingEntity;
    private String placedBy;
    private BuyernEntityType placedByType;
    private OrderType orderType;
    @OneToOne
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
