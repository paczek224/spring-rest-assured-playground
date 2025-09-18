package com.paczek.demo.app.products;

import java.time.LocalDateTime;

public record ProductDto(Long id, String name, Double pricePln, String description, LocalDateTime createdDate) {
}
