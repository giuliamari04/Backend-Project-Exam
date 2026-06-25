package com.eventhub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileImageDTO {

    @NotBlank(message = "Profile image is required")
    private String profileImage;
}