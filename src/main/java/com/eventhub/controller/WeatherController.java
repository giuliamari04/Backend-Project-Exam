package com.eventhub.controller;

import com.eventhub.dto.WeatherResponseDTO;
import com.eventhub.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/event/{eventId}")
    public WeatherResponseDTO getWeatherForEvent(@PathVariable Long eventId) {
        return weatherService.getWeatherForEvent(eventId);
    }
}