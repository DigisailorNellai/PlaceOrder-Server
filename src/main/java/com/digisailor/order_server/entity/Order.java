package com.digisailor.order_server.entity;

import com.digisailor.grpc.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
