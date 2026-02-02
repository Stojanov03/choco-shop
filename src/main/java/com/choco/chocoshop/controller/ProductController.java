package com.choco.chocoshop.controller;

import com.choco.chocoshop.model.Product;
import com.choco.chocoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getALlProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/available")
    public List<Product> getAvailableProducts(){
        return productService.getAvailableProducts();
    }

    @GetMapping("/on-sale")
    public List<Product> getProductsOnSale(){
        return productService.getProductsOnSale();
    }

    @GetMapping("/top-selling")
    public List<Product> getTopSellingProducts(@RequestParam(defaultValue = "5") int limit){
        return productService.getTopSellingProducts(limit);
    }

    @GetMapping("/homepage")
    public List<Product> getHomepageProducts(@RequestParam(defaultValue = "5") int limit){
        List<Product> onSale = productService.getProductsOnSale();
        if (!onSale.isEmpty()) {
            return onSale.stream().limit(limit).toList();
        }
        return productService.getTopSellingProducts(limit);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String uniqueCode,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String factoryName,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice){
        return productService.searchProducts(uniqueCode, productName, factoryName, minQuantity, minPrice, maxPrice);
    }

    @GetMapping("/sort")
    public List<Product> getAllProductsSorted(@RequestParam String sortBy){
        return productService.getAllProductsSorted(sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,@RequestBody Product product){
        product.setId(id);
        return productService.saveProduct(product);
    }
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
    }
}
