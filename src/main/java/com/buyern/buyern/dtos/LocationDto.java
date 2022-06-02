package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.Location;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LocationDto implements Serializable {
    private Long id;
    private String tag;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String latitude;
    private String longitude;

    public Location toLocation() {
        if (getAddress() == null) return null;

        Location location = new Location();
        location.setId(getId());
        location.setTag(getTag());
        location.setAddress(getAddress());
        location.setCity(getCity());
        location.setState(getState());
        location.setCountry(getCountry());
        location.setZipcode(getZipcode());
        location.setLatitude(getLatitude());
        location.setLongitude(getLongitude());
        return location;
    }

    public static LocationDto create(Location location) {
        if (location == null) return null;
        return LocationDto.builder()
                .id(location.getId())
                .tag(location.getTag())
                .address(location.getAddress())
                .city(location.getCity())
                .state(location.getState())
                .country(location.getCountry())
                .zipcode(location.getZipcode())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
