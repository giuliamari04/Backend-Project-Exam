package com.eventhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {

    private String name;

    private String surname;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;

    private String profileImage;
}