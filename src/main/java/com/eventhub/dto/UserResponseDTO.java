package com.eventhub.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import com.eventhub.entity.Role;
@Data
@Builder

public class UserResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String profileImage;

    private LocalDateTime registrationDate;

    private Role role;
}