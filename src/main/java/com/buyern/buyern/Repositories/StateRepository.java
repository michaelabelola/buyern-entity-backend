package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.Location.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {

    List<State> findAllByCountryId(Long id);
    List<State> findAllByCountryCode(String countryCode);
}