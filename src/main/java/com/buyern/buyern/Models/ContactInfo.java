package com.buyern.buyern.Models;

import javax.persistence.*;

@Entity(name = "ContactInfos")
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String email;
    private String phone;
}
