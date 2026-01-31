package com.choco.chocoshop.repository;

import com.choco.chocoshop.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {

}
