package com.eventhub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponseDTO {

    private Long id;

    private String message;

    private LocalDateTime createdAt;

    private boolean read;
}