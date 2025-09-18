package com.paczek.demo.app.products;

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

    @Column(name = "price_pln")
    double pricePln;

    @Column(name = "description")
    String description;

    @Column(name = "created_at")
    LocalDateTime createdDate;

    public ProductEntity() {
    }

    public ProductEntity(long id, String name, double pricePln, String description, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.pricePln = pricePln;
        this.description = description;
        this.createdDate = createdDate;
    }

    public ProductEntity(String name, double pricePln, String description,  LocalDateTime createdDate) {
        this.name = name;
        this.pricePln = pricePln;
        this.description = description;
        this.createdDate = createdDate;
    }
}
