package com.choco.chocoshop.service.impl;

import com.choco.chocoshop.model.Product;
import com.choco.chocoshop.repository.ProductRepository;
import com.choco.chocoshop.service.ProductService;
import lombok.Builder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAvailableProducts() {
        return productRepository.findByQuantityGreaterThan(0);
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
