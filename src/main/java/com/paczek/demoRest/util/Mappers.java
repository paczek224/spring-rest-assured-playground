package com.paczek.demoRest.util;

import com.paczek.demoRest.orders.OrderDto;
import com.paczek.demoRest.users.UserDto;
import com.paczek.demoRest.orders.OrderEntity;
import com.paczek.demoRest.users.UserEntity;

import java.util.List;
import java.util.function.Supplier;

public class Mappers {

    public static UserEntity map(UserDto from) {
        return new UserEntity(from.getFirstName(), from.getLastName(), from.getEmail(), from.getGender(), from.getIpAddress());
    }

    public static UserDto map(UserEntity from) {
        return new UserDto(from.getId(), from.getFirstName(), from.getLastName(), from.getEmail(), from.getGender(), from.getIpAddress());
    }

    public static OrderDto map(OrderEntity o) {
        return new OrderDto(o.getId(), o.getUser().getId(), o.getProductName(), o.getQuantity(), o.getPrice(), o.getOrderDate());
    }

    public static <E extends IdProvider> E find(List<E> users, Supplier<IdProvider> getKey) {
        return users.stream()
                .filter(o->o.getId().equals(getKey.get().getId()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
