package com.eventhub.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOnlineEventDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @Future(message = "Event date must be in the future")
    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @Min(value = 1, message = "Capacity must be at least 1")
    @NotNull(message = "Capacity is required")
    private Integer capacity;

    @NotBlank(message = "Meeting link is required")
    private String meetingLink;

    @NotNull(message = "Category id is required")
    private Long categoryId;

    @Min(value = 1, message = "Max participants must be at least 1")
    @NotNull(message = "Max participants is required")
    private Integer maxParticipants;
}