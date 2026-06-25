package com.eventhub.dto;

import lombok.Data;

@Data

public class RegisterRequestDTO {

    private String name;
    private String surname;
    private String email;
    private String password;
}