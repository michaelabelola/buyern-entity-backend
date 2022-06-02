package com.buyern.buyern.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String tag;
    private String address;
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String country;
    private String zipcode;
    private String latitude;
    private String longitude;

}
