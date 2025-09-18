package com.paczek.demo.app.util;

import com.paczek.demo.app.orders.OrderDto;
import com.paczek.demo.app.products.ProductDto;
import com.paczek.demo.app.products.ProductEntity;
import com.paczek.demo.app.users.UserDto;
import com.paczek.demo.app.orders.OrderEntity;
import com.paczek.demo.app.users.UserEntity;

import java.util.List;
import java.util.function.Supplier;

public class Mappers {

    public static UserEntity map(UserDto from) {
        return new UserEntity(from.firstName(), from.lastName(), from.email(), from.gender(), from.ipAddress());
    }

    public static UserDto map(UserEntity from) {
        return new UserDto(from.getId(), from.getFirstName(), from.getLastName(), from.getEmail(), from.getGender(), from.getIpAddress());
    }

    public static ProductDto map(ProductEntity from) {
        return new ProductDto(from.getId(), from.getName(), from.getPricePln(), from.getDescription(), from.getCreatedDate());
    }

    public static ProductEntity map(ProductDto from) {
        return new ProductEntity(from.id(), from.name(), from.pricePln(), from.description(), from.createdDate());
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
