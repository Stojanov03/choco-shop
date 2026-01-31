package com.choco.chocoshop.service;

import com.choco.chocoshop.model.Factory;
import java.util.List;

import java.util.Optional;

public interface FactoryService {
    Factory save(Factory factory);
    Optional<Factory> findById(Long id);
    List<Factory> findAll();
    void deleteById(Long id);
}
