package com.choco.chocoshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Blob;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uniqueCode;
    private String productName;
    private String description;
    private BigDecimal price;
    private int quantity;

    @ManyToOne
    private Factory factory;

}
