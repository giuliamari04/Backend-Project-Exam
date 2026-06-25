package com.eventhub.controller;

import com.eventhub.dto.GeocodingResponseDTO;
import com.eventhub.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geocoding")
@RequiredArgsConstructor
public class GeocodingController {

    private final GeocodingService geocodingService;

    @GetMapping
    public GeocodingResponseDTO geocodeAddress(
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String country
    ) {
        return geocodingService.geocodeAddress(address, city, country);
    }
}