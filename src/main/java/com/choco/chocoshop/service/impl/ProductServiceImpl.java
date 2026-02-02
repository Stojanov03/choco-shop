package com.choco.chocoshop.service.impl;

import com.choco.chocoshop.model.Discount;
import com.choco.chocoshop.model.Product;
import com.choco.chocoshop.repository.PurchaseItemRepository;
import com.choco.chocoshop.repository.ProductRepository;
import com.choco.chocoshop.service.DiscountService;
import com.choco.chocoshop.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final DiscountService discountService;
    private final PurchaseItemRepository purchaseItemRepository;

    public ProductServiceImpl(ProductRepository productRepository, DiscountService discountService, PurchaseItemRepository purchaseItemRepository) {
        this.productRepository = productRepository;
        this.discountService = discountService;
        this.purchaseItemRepository = purchaseItemRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (!purchaseItemRepository.findByProduct(product).isEmpty()) {
            throw new RuntimeException("Cannot delete product that has been purchased");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAvailableProducts() {
        return productRepository.findByQuantityGreaterThan(0);
    }

    @Override
    public List<Product> getProductsOnSale() {
        return discountService.getActiveDiscounts().stream()
                .map(Discount::getProduct)
                .filter(p -> p.getQuantity() > 0)
                .toList();
    }

    @Override
    public List<Product> getTopSellingProducts(int limit) {
        return purchaseItemRepository.findTopSellingProducts().stream()
                .map(row -> (Product) row[0])
                .limit(limit)
                .toList();
    }

    @Override
    public List<Product> searchProducts(String uniqueCode, String productName, String factoryName, Integer minQuantity, Double minPrice, Double maxPrice) {
        return productRepository.findAll().stream()
                .filter(product -> uniqueCode == null || product.getUniqueCode().contains(uniqueCode))
                .filter(product -> productName == null || product.getProductName().toLowerCase().contains(productName.toLowerCase()))
                .filter(product -> factoryName == null || product.getFactory().getName().toLowerCase().contains(factoryName.toLowerCase()))
                .filter(product -> minQuantity == null || product.getQuantity() >= minQuantity)
                .filter(product -> minPrice == null || product.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0)
                .filter(product -> maxPrice == null || product.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0)
                .toList();
    }

    @Override
    public List<Product> getAllProductsSorted(String sortBy) {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
    }
}
