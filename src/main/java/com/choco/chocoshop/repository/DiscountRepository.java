package com.choco.chocoshop.repository;

import com.choco.chocoshop.model.Discount;
import com.choco.chocoshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByProduct(Product product);
    List<Discount> findByValidUntilAfter(LocalDateTime now);
}