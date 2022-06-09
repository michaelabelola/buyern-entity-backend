package com.buyern.buyern.Services;

import com.buyern.buyern.Models.Promo;
import com.buyern.buyern.Repositories.PromoRepository;
import com.buyern.buyern.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoService {
    @Autowired
    PromoRepository promoRepository;

    public Promo getPromo(Long promoId) {
        Optional<Promo> promo = promoRepository.findById(promoId);
        if (promo.isEmpty())
            throw new RecordNotFoundException("Promo with id %d does not exist".formatted(promoId));
        return promo.get();
    }

    public List<Promo> getPromos(List<Long> promoIds) {
        List<Promo> promos = promoRepository.findAllById(promoIds);
        if (promos.isEmpty())
            throw new RecordNotFoundException("No Promos found");
        return promos;
    }
}
