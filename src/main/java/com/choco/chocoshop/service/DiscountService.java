package com.choco.chocoshop.service;

import com.choco.chocoshop.model.Discount;
import com.choco.chocoshop.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiscountService {
    List<Discount> getAllDiscounts();
    Optional<Discount> getDiscountById(Long id);
    Optional<Discount> getDiscountByProduct(Product product);
    Discount saveDiscount(Discount discount);
    void deleteDiscountById(Long id);
    List<Discount> getActiveDiscounts();
    void deactivateExpiredDiscounts();
}