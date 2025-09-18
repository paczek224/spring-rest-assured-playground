package com.paczek.demo.app.users;

import com.paczek.demo.app.util.IdProvider;

public record UserDto(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String gender,
                      String ipAddress) implements IdProvider {

    @Override
    public Long getId() {
        return id;
    }
}
