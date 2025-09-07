package com.paczek.demoRest.orders;

import java.time.LocalDateTime;

public record OrderDto(Long id, Long userId, String productName, Integer quantity, Double price, LocalDateTime orderDate) {

}
