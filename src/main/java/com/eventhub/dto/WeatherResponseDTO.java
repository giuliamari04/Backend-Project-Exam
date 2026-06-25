package com.eventhub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResponseDTO {

    private Long eventId;

    private String eventTitle;

    private String city;

    private Double latitude;

    private Double longitude;

    private Double temperature;

    private Double windSpeed;

    private Integer weatherCode;
}