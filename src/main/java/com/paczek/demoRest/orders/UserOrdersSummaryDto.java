package com.paczek.demoRest.orders;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class UserOrdersSummaryDto {
    private final String firstName;
    private final String lastName;
    private final Double totalSpent;

    public UserOrdersSummaryDto(String firstName, String lastName, Double totalSpent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalSpent = totalSpent;
    }
}
