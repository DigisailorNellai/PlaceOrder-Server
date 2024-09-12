package com.digisailor.order_server.entity;

import com.digisailor.grpc.OrderItem;
import com.digisailor.grpc.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Add userId to the Order entity

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // One order can have multiple items, mapped by the "order" field in OrderItem
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItemEntity> items;

    // Getters and setters for userId and items

    // Getter and setter for items
    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }
}
