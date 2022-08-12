package com.buyern.buyern.Models;

import com.buyern.buyern.Enums.State;

import javax.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    /**
     * transactions uuids
     */
    private String transactionsId;
    private int transactionsCount;
    private Long totalAmount;
    private State.Payment status;

}