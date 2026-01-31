package com.choco.chocoshop.repository;

import com.choco.chocoshop.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactoryRepository extends JpaRepository<Factory,Long> {
    Optional<Factory> findByPib(String pib);
}
