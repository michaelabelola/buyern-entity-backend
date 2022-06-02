package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Override
    List<Country> findAll();

    List<Country> findCountryByIso2(String countryCode);
}