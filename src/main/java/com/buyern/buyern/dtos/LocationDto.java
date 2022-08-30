package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.Location.City;
import com.buyern.buyern.Models.Location.Country;
import com.buyern.buyern.Models.Location.Location;
import com.buyern.buyern.Models.Location.State;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LocationDto implements Serializable {
    private Long id;
    private String tag;
    private String address;
    private City city;
    private State state;
    private Country country;
    private String zipcode;
    private String latitude;
    private String longitude;
}
