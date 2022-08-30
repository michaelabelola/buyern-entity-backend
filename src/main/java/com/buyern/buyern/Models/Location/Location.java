package com.buyern.buyern.Models.Location;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String tag;
    private String address;
//    @Column(name = "city_id")
//    private Long city;
//    @Column(name = "state_id")
//    private Long state;
//    @Column(name = "country_id")
//    private Long country;
    @ManyToOne(cascade = CascadeType.DETACH)
    private City city;
    @ManyToOne(cascade = CascadeType.DETACH)
    private State state;
    @ManyToOne(cascade = CascadeType.DETACH)
    private Country country;
    private String zipcode;
    private Double latitude;
    private Double longitude;
//    private String ownerId;

}
