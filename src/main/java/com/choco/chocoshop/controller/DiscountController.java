package com.choco.chocoshop.controller;

import com.choco.chocoshop.model.Discount;
import com.choco.chocoshop.model.Product;
import com.choco.chocoshop.service.DiscountService;
import com.choco.chocoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @GetMapping("/active")
    public List<Discount> getActiveDiscounts() {
        return discountService.getActiveDiscounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id) {
        return discountService.getDiscountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Discount> getDiscountByProduct(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .flatMap(discountService::getDiscountByProduct)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Discount createDiscount(@RequestBody Discount discount) {
        return discountService.saveDiscount(discount);
    }

    @PutMapping("/{id}")
    public Discount updateDiscount(@PathVariable Long id, @RequestBody Discount discount) {
        discount.setId(id);
        return discountService.saveDiscount(discount);
    }

    @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscountById(id);
    }

    @PostMapping("/deactivate-expired")
    public void deactivateExpiredDiscounts() {
        discountService.deactivateExpiredDiscounts();
    }
}