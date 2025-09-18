package com.paczek.demo.app.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/summary")
    public ResponseEntity<List<UserOrdersSummaryDto>> getOrdersSummary(){
        return ResponseEntity.ok(orderService.findUsersWithTotalSpent());
    }
}
