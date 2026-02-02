package com.choco.chocoshop.controller;

import com.choco.chocoshop.model.Purchase;
import com.choco.chocoshop.model.User;
import com.choco.chocoshop.service.PurchaseService;
import com.choco.chocoshop.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final UserService userService;

    public PurchaseController(PurchaseService purchaseService,
                              UserService userService) {
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @GetMapping("/my-purchases")
    public ResponseEntity<List<Purchase>> getMyPurchases(Principal principal) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(
                purchaseService.getPurchaseByUser(user)
        );
    }

    @GetMapping("/my-purchases/filter")
    public ResponseEntity<List<Purchase>> getMyPurchasesByDate(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(
                purchaseService.getPurchaseByUserAndDateRange(user, startDate, endDate)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {

        return purchaseService.getPurchaseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/buy")
    public ResponseEntity<Purchase> buyProduct(
            @RequestParam Long productId,
            @RequestParam int quantity,
            Principal principal) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Purchase purchase = purchaseService.createPurchase(
                user, productId, quantity
        );

        return ResponseEntity.ok(purchase);
    }


    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelPurchase(
            @PathVariable Long id,
            @RequestParam String reason) {

        purchaseService.cancelPurchase(id, reason);
        return ResponseEntity.ok("Purchase successfully cancelled");
    }
}
