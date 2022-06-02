package com.buyern.buyern.Models;

import com.buyern.buyern.Enums.State;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String orderId;
    private Date timePlaced;
    @Enumerated(EnumType.STRING)
    private State.Order state;
    private Date timeCompleted;
    private String delivererId;
    private String about;
    @OneToMany(cascade = CascadeType.DETACH)
    @JoinTable(joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "content_id"))
    private List<OrderContent> contents;
    private String handlingEntity;
    private String location;
    private String handler;
}
