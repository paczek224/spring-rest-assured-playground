package com.paczek.demoRest.products;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    double price;

    @Column(name = "description")
    String description;

    @Column(name = "created_at")
    LocalDateTime createdDate;

    public ProductEntity() {
    }

    public ProductEntity(long id, String name, double price, String description, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdDate = createdDate;
    }

    public ProductEntity(String name, double price, String description,  LocalDateTime createdDate) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdDate = createdDate;
    }
}
