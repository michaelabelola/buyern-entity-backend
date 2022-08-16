package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.Location.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("select c from countries c order by c.name")
    List<Country> findByOrderByNameAsc();

    List<Country> findCountryByIso2(String countryCode);
}