package com.choco.chocoshop.service;

import com.choco.chocoshop.model.Purchase;
import com.choco.chocoshop.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    List<Purchase> getAllPurchases();
    Optional<Purchase> getPurchaseById(Long id);
    List<Purchase> getPurchaseByUser(User user);
    List<Purchase> getPurchaseByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate);
    Purchase createPurchase(User user,Long productId, int quantity);
    Purchase savePurchase(Purchase purchase);
    void deletePurchaseById(Long id);
    void cancelPurchase(Long purchaseId,String reason);
}
