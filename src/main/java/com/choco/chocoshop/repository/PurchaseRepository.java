package com.choco.chocoshop.repository;

import com.choco.chocoshop.model.Purchase;
import com.choco.chocoshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findByUser(User user);
    List<Purchase> findByUserAndPurchaseDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
}
