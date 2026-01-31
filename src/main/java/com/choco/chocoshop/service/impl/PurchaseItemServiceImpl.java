package com.choco.chocoshop.service.impl;

import com.choco.chocoshop.model.PurchaseItem;
import com.choco.chocoshop.repository.PurchaseItemRepository;
import com.choco.chocoshop.service.PurchaseItemService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseItemServiceImpl implements PurchaseItemService {

    private final PurchaseItemRepository purchaseItemRepository;
    public PurchaseItemServiceImpl(PurchaseItemRepository purchaseItemRepository) {
        this.purchaseItemRepository = purchaseItemRepository;
    }
    @Override
    public PurchaseItem save(PurchaseItem purchaseItem) {
        return purchaseItemRepository.save(purchaseItem);
    }
}
