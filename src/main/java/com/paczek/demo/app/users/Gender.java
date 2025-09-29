package com.paczek.demo.app.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("Male"), FEMALE("Female"), POLYGENDER("Polygender");
    final String label;
}

