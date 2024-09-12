package com.digisailor.order_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto-generation for UUIDs
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID productId; // UUID for productId as the primary key

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity; // Quantity available for the product
}
