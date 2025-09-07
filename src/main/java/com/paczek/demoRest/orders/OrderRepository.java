package com.paczek.demoRest.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT new com.paczek.demoRest.orders.UserOrdersSummaryDto(u.firstName, u.lastName, SUM(o.price)) " +
            "FROM OrderEntity o JOIN o.user u " +
            "GROUP BY u.id, u.firstName, u.lastName")
    List<UserOrdersSummaryDto> findUsersWithTotalSpent();
}
