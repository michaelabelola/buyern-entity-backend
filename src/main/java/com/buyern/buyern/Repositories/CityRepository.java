package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Override
    Optional<City> findById(Long aLong);

    List<City> findAllByCountryId(Long countryId);

    List<City> findAllByCountryCode(String countryCode);

    List<City> findAllByStateId(Long StateId);

    List<City> findAllByCountryCodeAndStateCode(String countryCode, String stateCode);
}