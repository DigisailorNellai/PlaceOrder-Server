package com.digisailor.order_server.service;

import com.digisailor.grpc.CreateOrderRequest;
import com.digisailor.grpc.OrderItem;
import com.digisailor.grpc.OrderResponse;
import com.digisailor.grpc.OrderServiceGrpc;
import com.digisailor.grpc.OrderStatus;
import com.digisailor.order_server.entity.Order;
import com.digisailor.order_server.entity.OrderItemEntity;
import com.digisailor.order_server.repository.OrderRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        // Create and save the new order
        Order order = new Order();
        order.setOrderId(UUID.randomUUID()); // Manually generate UUID
        order.setUserId(UUID.fromString(request.getUserId())); // Convert String to UUID
        order.setStatus(OrderStatus.PLACED); // Set initial status

        // Map gRPC OrderItem to entity OrderItemEntity
        Order finalOrder = order;
        List<OrderItemEntity> orderItems = request.getItemsList().stream().map(item -> {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setProductId(UUID.fromString(String.valueOf(UUID.fromString(item.getProductId())))); // Convert String to UUID
            orderItemEntity.setProductName(item.getProductName());
            orderItemEntity.setQuantity(item.getQuantity());
            orderItemEntity.setOrder(finalOrder); // Associate with the order
            return orderItemEntity;
        }).collect(Collectors.toList());

        // Set order items
        order.setItems(orderItems);

        // Save order to the database (this will cascade and save the items too)
        order = orderRepository.save(order);

        // Build the response
        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId(order.getOrderId().toString()) // Convert UUID to String
                .setStatus(OrderStatus.valueOf(order.getStatus().name())) // Map entity OrderStatus to gRPC OrderStatus
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
