package com.eventhub.service;

import com.eventhub.dto.GeocodingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeocodingService {

    private final RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    public GeocodingResponseDTO geocodeAddress(
            String address,
            String city,
            String country
    ) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://nominatim.openstreetmap.org/search")
                .queryParam("street", address)
                .queryParam("city", city)
                .queryParam("country", country)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .queryParam("addressdetails", 1)
                .build()
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "EventHubBackend/1.0");
        headers.set("Accept", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                List.class
        );

        List<Map<String, Object>> response =
                (List<Map<String, Object>>) responseEntity.getBody();

        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("Address not found");
        }

        Map<String, Object> firstResult = response.get(0);

        return GeocodingResponseDTO.builder()
                .query(address + ", " + city + ", " + country)
                .latitude(Double.parseDouble(firstResult.get("lat").toString()))
                .longitude(Double.parseDouble(firstResult.get("lon").toString()))
                .displayName(firstResult.get("display_name").toString())
                .build();
    }
}