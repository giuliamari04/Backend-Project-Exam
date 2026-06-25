package com.eventhub.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateEventDTO {

    private String title;

    private String description;

    @Future(message = "Event date must be in the future")
    private LocalDateTime date;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @Min(value = 1, message = "Max participants must be at least 1")
    private Integer maxParticipants;

    private Long categoryId;

    private String meetingLink;

    private String address;

    private String city;

    private String country;

    private Double latitude;

    private Double longitude;
}