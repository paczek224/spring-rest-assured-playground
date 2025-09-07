package com.paczek.demoRest.users;

import com.paczek.demoRest.util.IdProvider;

public record UserDto(Long id, String firstName, String lastName, String email, String gender,
                      String ipAddress) implements IdProvider {

    @Override
    public Long getId() {
        return id;
    }
}
