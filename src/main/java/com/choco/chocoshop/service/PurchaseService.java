package com.choco.chocoshop.service;

import com.choco.chocoshop.model.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    List<Purchase> getAllPurchases();
    Optional<Purchase> getPurchaseById(Long id);
    Purchase savePurchase(Purchase purchase);
    void deletePurchaseById(Long id);
}
