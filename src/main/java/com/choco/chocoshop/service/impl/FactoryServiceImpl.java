package com.choco.chocoshop.service.impl;

import com.choco.chocoshop.model.Factory;
import com.choco.chocoshop.repository.FactoryRepository;
import com.choco.chocoshop.service.FactoryService;

import java.util.List;
import java.util.Optional;

public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository  factoryRepository;

    public FactoryServiceImpl(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    @Override
    public Factory save(Factory factory) {
        return factoryRepository.save(factory);
    }

    @Override
    public Optional<Factory> findById(Long id) {
        return factoryRepository.findById(id);
    }

    @Override
    public List<Factory> findAll() {
        return factoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        factoryRepository.deleteById(id);
    }
}
