package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PromoRepository extends JpaRepository<Promo, Long> {
}