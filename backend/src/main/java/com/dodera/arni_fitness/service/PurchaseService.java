package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

}
