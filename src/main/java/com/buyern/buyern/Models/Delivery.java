package com.buyern.buyern.Models;

import com.buyern.buyern.Enums.BuyernEntityType;
import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Models.Location.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long entityId;
    @OneToOne
    @JoinColumn(name = "start_location_id")
    private Location startLocation;
    @OneToOne
    @JoinColumn(name = "end_location_id")
    private Location endLocation;
    @OneToOne
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;
    private State.Delivery state;
    private Long handlerId;//driver Id
    private Long managerId;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * @implNote if <b>state</b> is not {@code DELIVERED or COMPLETED} this should be considered estimate delivery time
     */
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date endTime;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date timeCreated;
    private Long senderId;
    private BuyernEntityType senderType;
    private Long receiverId;
    private BuyernEntityType receiverType;
    private Double price;
    @OneToMany
    @JoinTable(name = "active_payments", joinColumns = @JoinColumn(name = "object_id"), inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private List<Payment> payments;
    @OneToOne
    @JoinColumn(name = "parent_delivery_id")
    private Delivery parentDelivery;
}
