package com.buyern.buyern.Models;

import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Models.Location.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timePlaced;
    @Enumerated(EnumType.STRING)
    private State.Order state;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCompleted;
    private String delivererId;//
    private String about;
    @OneToMany(cascade = CascadeType.DETACH)
    @JoinTable(joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "content_id"))
    private List<OrderContent> contents;
    private Long handlingEntity;// entity id
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private Long handler;//user id
}
