package com.digisailor.order_server.entity;

import com.digisailor.grpc.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private UUID userId; // Changed to UUID

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // One order can have multiple items, mapped by the "order" field in OrderItemEntity
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItemEntity> items;
}
