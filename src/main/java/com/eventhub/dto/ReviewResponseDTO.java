package com.eventhub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponseDTO {

    private Long id;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;

    private Long userId;

    private String userEmail;

    private Long eventId;

    private String eventTitle;
}