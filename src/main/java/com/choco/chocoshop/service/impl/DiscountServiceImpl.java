package com.choco.chocoshop.service.impl;

import com.choco.chocoshop.model.Discount;
import com.choco.chocoshop.model.Product;
import com.choco.chocoshop.repository.DiscountRepository;
import com.choco.chocoshop.service.DiscountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public Optional<Discount> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }

    @Override
    public Optional<Discount> getDiscountByProduct(Product product) {
        return discountRepository.findByProduct(product);
    }

    @Override
    public Discount saveDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public void deleteDiscountById(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public List<Discount> getActiveDiscounts() {
        return discountRepository.findByValidUntilAfter(LocalDateTime.now());
    }

    @Override
    public void deactivateExpiredDiscounts() {
        List<Discount> expiredDiscounts = discountRepository.findAll().stream()
                .filter(d -> d.getValidUntil().isBefore(LocalDateTime.now()))
                .toList();
        discountRepository.deleteAll(expiredDiscounts);
    }
}