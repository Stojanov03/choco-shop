package com.choco.chocoshop.controller;

import com.choco.chocoshop.model.Factory;
import com.choco.chocoshop.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factories")
public class FactoryController {

    @Autowired
    private FactoryService factoryService;

    @GetMapping
    public List<Factory> getAllFactories() {
        return factoryService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Factory> getFactoryById(@PathVariable Long id) {
        return factoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Factory createFactory(@RequestBody Factory factory) {
        return factoryService.save(factory);
    }
    @PutMapping("/{id}")
    public Factory updateFactory(@PathVariable Long id,@RequestBody Factory factory) {
        factory.setId(id);
        return factoryService.save(factory);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactoryById(@PathVariable Long id) {
        if (factoryService.findById(id).isPresent()) {
            factoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
