package com.eventhub.service;

import com.eventhub.dto.CreateOnlineEventDTO;
import com.eventhub.dto.CreatePhysicalEventDTO;
import com.eventhub.dto.UpdateEventDTO;
import com.eventhub.entity.*;
import com.eventhub.exception.custom.CategoryNotFoundException;
import com.eventhub.exception.custom.EventNotFoundException;
import com.eventhub.exception.custom.UnauthorizedActionException;
import com.eventhub.repository.CategoryRepository;
import com.eventhub.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final GeocodingService geocodingService;

    public OnlineEvent createOnlineEvent(
            CreateOnlineEventDTO dto,
            User creator
    ) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        OnlineEvent event = new OnlineEvent();

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setCapacity(dto.getCapacity());
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setMeetingLink(dto.getMeetingLink());
        event.setCreator(creator);
        event.setCategory(category);

        return eventRepository.save(event);
    }

    public PhysicalEvent createPhysicalEvent(
            CreatePhysicalEventDTO dto,
            User creator
    ) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        PhysicalEvent event = new PhysicalEvent();

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setCapacity(dto.getCapacity());
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setAddress(dto.getAddress());
        event.setCity(dto.getCity());
        event.setCountry(dto.getCountry());

        if (dto.getLatitude() == null || dto.getLongitude() == null) {
            var coordinates = geocodingService.geocodeAddress(
                    dto.getAddress(),
                    dto.getCity(),
                    dto.getCountry()
            );

            event.setLatitude(coordinates.getLatitude());
            event.setLongitude(coordinates.getLongitude());
        } else {
            event.setLatitude(dto.getLatitude());
            event.setLongitude(dto.getLongitude());
        }

        event.setCreator(creator);
        event.setCategory(category);

        return eventRepository.save(event);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(EventNotFoundException::new);
    }

    public List<Event> findByCategory(Long categoryId) {
        return eventRepository.findByCategoryId(categoryId);
    }

    public List<Event> findUpcoming() {
        return eventRepository.findByDateAfterOrderByDateAsc(LocalDateTime.now());
    }

    public List<Event> searchByTitle(String title) {
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Event> sortByDate() {
        return eventRepository.findAllByOrderByDateAsc();
    }

    public List<PhysicalEvent> findPhysicalEventsByCity(String city) {
        return eventRepository.findPhysicalEventsByCity(city);
    }

    public List<Event> findSoldOutEvents() {
        return eventRepository.findSoldOutEvents();
    }

    public List<Event> findUpcomingByCategory(Long categoryId) {
        return eventRepository.findUpcomingByCategory(LocalDateTime.now(), categoryId);
    }

    public Event updateEvent(Long id, UpdateEventDTO dto, User user) {

    Event event = eventRepository.findById(id)
            .orElseThrow(EventNotFoundException::new);

    boolean isAdmin = user.getRole() == Role.ADMIN;
    boolean isCreator = event.getCreator().getId().equals(user.getId());

    if (!isAdmin && !isCreator) {
        throw new UnauthorizedActionException();
    }

    if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
        event.setTitle(dto.getTitle());
    }

    if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
        event.setDescription(dto.getDescription());
    }

    if (dto.getDate() != null) {
        event.setDate(dto.getDate());
    }

    if (dto.getCapacity() != null) {
        event.setCapacity(dto.getCapacity());
    }

    if (dto.getMaxParticipants() != null) {
        event.setMaxParticipants(dto.getMaxParticipants());
    }

    if (dto.getCategoryId() != null) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        event.setCategory(category);
    }

    if (event instanceof OnlineEvent onlineEvent) {

        if (dto.getMeetingLink() != null && !dto.getMeetingLink().isBlank()) {
            onlineEvent.setMeetingLink(dto.getMeetingLink());
        }
    }

    if (event instanceof PhysicalEvent physicalEvent) {

        boolean addressChanged = false;

        if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
            physicalEvent.setAddress(dto.getAddress());
            addressChanged = true;
        }

        if (dto.getCity() != null && !dto.getCity().isBlank()) {
            physicalEvent.setCity(dto.getCity());
            addressChanged = true;
        }

        if (dto.getCountry() != null && !dto.getCountry().isBlank()) {
            physicalEvent.setCountry(dto.getCountry());
            addressChanged = true;
        }

        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            physicalEvent.setLatitude(dto.getLatitude());
            physicalEvent.setLongitude(dto.getLongitude());
        } else if (addressChanged) {
            var coordinates = geocodingService.geocodeAddress(
                    physicalEvent.getAddress(),
                    physicalEvent.getCity(),
                    physicalEvent.getCountry()
            );

            physicalEvent.setLatitude(coordinates.getLatitude());
            physicalEvent.setLongitude(coordinates.getLongitude());
        }
    }

    return eventRepository.save(event);
}
}