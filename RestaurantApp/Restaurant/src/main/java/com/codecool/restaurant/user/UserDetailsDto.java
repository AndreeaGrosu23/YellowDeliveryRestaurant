package com.codecool.restaurant.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private String deliveryAddress;
    private String phoneNumber;
}
