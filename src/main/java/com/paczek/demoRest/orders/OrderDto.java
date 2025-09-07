package com.paczek.demoRest.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private String productName;
    private Integer quantity;
    private Double price;
    private LocalDateTime orderDate;
}
