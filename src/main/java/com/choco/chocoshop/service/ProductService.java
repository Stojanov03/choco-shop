package com.choco.chocoshop.service;

import com.choco.chocoshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    void deleteProductById(Long id);
    List<Product> getAvailableProducts();
    List<Product> searchProducts(
            String uniqueCode,
            String productName,
            String factoryName,
            Integer minQuantity,
            Double minPrice,
            Double maxPrice
    );
    List<Product> getAllProductsSorted(String sortBy);
}
