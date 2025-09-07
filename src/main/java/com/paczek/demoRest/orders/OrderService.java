package com.paczek.demoRest.orders;

import com.paczek.demoRest.util.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<UserOrdersSummaryDto> findUsersWithTotalSpent(){
        return orderRepository.findUsersWithTotalSpent();
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(Mappers::map)
                .toList();
    }
}
