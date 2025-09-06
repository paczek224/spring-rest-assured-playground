package com.paczek.demoRest.util;

import com.paczek.demoRest.dto.user.UserDto;
import com.paczek.demoRest.entity.user.UserEntity;

public class Mappers {

    public static UserEntity map(UserDto from) {
        return new UserEntity(from.getFirstName(), from.getLastName(), from.getEmail(), from.getGender(), from.getIpAddress());
    }

    public static UserDto map(UserEntity from) {
        return new UserDto(from.getId(), from.getFirstName(), from.getLastName(), from.getEmail(), from.getGender(), from.getIpAddress());
    }
}
