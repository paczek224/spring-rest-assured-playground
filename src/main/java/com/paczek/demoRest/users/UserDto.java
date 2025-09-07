package com.paczek.demoRest.users;

import com.paczek.demoRest.util.IdProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements IdProvider {

    Long id;
    String firstName;
    String lastName;
    String email;
    String gender;
    String ipAddress;

    public UserDto(String firstName, String lastName, String email, String gender, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.ipAddress = ipAddress;
    }
}
