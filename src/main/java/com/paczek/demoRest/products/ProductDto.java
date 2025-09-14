package com.paczek.demoRest.products;

import java.time.LocalDateTime;

public record ProductDto(Long id, String name, Double price, String description, LocalDateTime createdDate) {
}
