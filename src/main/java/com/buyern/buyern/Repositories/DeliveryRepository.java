package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}