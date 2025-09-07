package com.paczek.demoRest.orders;

import com.paczek.demoRest.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Column(name = "product_name")
    String productName;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "price")
    double price;

    @Column(name = "order_date")
    LocalDateTime orderDate;
}
