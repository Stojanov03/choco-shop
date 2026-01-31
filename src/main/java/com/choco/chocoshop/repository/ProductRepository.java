package com.choco.chocoshop.repository;

import com.choco.chocoshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByQuantityGreaterThan(int min);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}