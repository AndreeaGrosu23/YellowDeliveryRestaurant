package com.codecool.restaurant.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

    @NotBlank(message="Username cannot be empty")
    private String username;

    @NotBlank(message="Password cannot be empty")
    @Size(min=4, max=16, message="Password has to have min 4 characters and max 16 characters" )
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
