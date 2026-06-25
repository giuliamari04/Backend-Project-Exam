package com.eventhub.service;

import com.eventhub.dto.WeatherResponseDTO;
import com.eventhub.entity.Event;
import com.eventhub.entity.PhysicalEvent;
import com.eventhub.exception.custom.EventNotFoundException;
import com.eventhub.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final EventRepository eventRepository;
    private final RestTemplate restTemplate;

    public WeatherResponseDTO getWeatherForEvent(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

        if (!(event instanceof PhysicalEvent physicalEvent)) {
            throw new IllegalArgumentException("Weather is available only for physical events");
        }

        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + physicalEvent.getLatitude()
                + "&longitude=" + physicalEvent.getLongitude()
                + "&current_weather=true";

        Map response = restTemplate.getForObject(url, Map.class);

        Map currentWeather = (Map) response.get("current_weather");

        return WeatherResponseDTO.builder()
                .eventId(physicalEvent.getId())
                .eventTitle(physicalEvent.getTitle())
                .city(physicalEvent.getCity())
                .latitude(physicalEvent.getLatitude())
                .longitude(physicalEvent.getLongitude())
                .temperature(((Number) currentWeather.get("temperature")).doubleValue())
                .windSpeed(((Number) currentWeather.get("windspeed")).doubleValue())
                .weatherCode(((Number) currentWeather.get("weathercode")).intValue())
                .build();
    }
}