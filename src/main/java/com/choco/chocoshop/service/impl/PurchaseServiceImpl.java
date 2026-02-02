package com.choco.chocoshop.service.impl;

import com.choco.chocoshop.model.Product;
import com.choco.chocoshop.model.Purchase;
import com.choco.chocoshop.model.PurchaseItem;
import com.choco.chocoshop.model.User;
import com.choco.chocoshop.repository.ProductRepository;
import com.choco.chocoshop.repository.PurchaseRepository;
import com.choco.chocoshop.service.PurchaseService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseRepository.findById(id);
    }

    @Override
    public List<Purchase> getPurchaseByUser(User user) {
        return purchaseRepository.findByUser(user);
    }

    @Override
    public List<Purchase> getPurchaseByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return purchaseRepository.findByUserAndPurchaseDateBetween(user, startDate, endDate);
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public void deletePurchaseById(Long id) {
        purchaseRepository.deleteById(id);
    }
    public Purchase createPurchase(User user,Long productId, int quantity){
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getQuantity() < quantity){
            throw new RuntimeException("Not enough products in stock");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        PurchaseItem item = new PurchaseItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setItems(List.of(item));
        purchase.setTotalAmount(product.getPrice().doubleValue() * quantity);

        item.setPurchase(purchase);

        return purchaseRepository.save(purchase);
    }

    @Override
    public void cancelPurchase(Long purchaseId, String reason) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new RuntimeException("Purchase not found"));

        long hours = Duration.between(purchase.getPurchaseDate(), LocalDateTime.now()).toHours();

        if(hours > 1){
            throw new RuntimeException("Purchase can no longer be cancelled");
        }

        purchase.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);
        });

        purchaseRepository.delete(purchase);
    }
}
