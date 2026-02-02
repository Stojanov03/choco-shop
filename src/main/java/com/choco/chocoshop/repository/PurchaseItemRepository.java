package com.choco.chocoshop.repository;

import com.choco.chocoshop.model.PurchaseItem;
import com.choco.chocoshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {
    List<PurchaseItem> findByProduct(Product product);

    @Query("SELECT p.product, SUM(p.quantity) as totalSold FROM PurchaseItem p GROUP BY p.product ORDER BY totalSold DESC")
    List<Object[]> findTopSellingProducts();
}
