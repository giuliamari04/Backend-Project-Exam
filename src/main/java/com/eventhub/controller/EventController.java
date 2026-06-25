package com.eventhub.controller;

import com.eventhub.dto.CreateOnlineEventDTO;
import com.eventhub.dto.CreatePhysicalEventDTO;
import com.eventhub.entity.Event;
import com.eventhub.entity.OnlineEvent;
import com.eventhub.entity.PhysicalEvent;
import com.eventhub.entity.User;
import com.eventhub.service.EventService;
import com.eventhub.dto.UpdateEventDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping("/online")
    public OnlineEvent createOnlineEvent(
            @RequestBody @Valid CreateOnlineEventDTO dto,
            @AuthenticationPrincipal User user) {
        return eventService.createOnlineEvent(dto, user);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping("/physical")
    public PhysicalEvent createPhysicalEvent(
            @RequestBody @Valid CreatePhysicalEventDTO dto,
            @AuthenticationPrincipal User user) {
        return eventService.createPhysicalEvent(dto, user);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<Event> getEventsByCategory(@PathVariable Long categoryId) {
        return eventService.findByCategory(categoryId);
    }

    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.findUpcoming();
    }

    @GetMapping("/search")
    public List<Event> searchEventsByTitle(@RequestParam String title) {
        return eventService.searchByTitle(title);
    }

    @GetMapping("/sort/date")
    public List<Event> sortEventsByDate() {
        return eventService.sortByDate();
    }

    @GetMapping("/city/{city}")
    public List<PhysicalEvent> getPhysicalEventsByCity(@PathVariable String city) {
        return eventService.findPhysicalEventsByCity(city);
    }

    @GetMapping("/sold-out")
    public List<Event> getSoldOutEvents() {
        return eventService.findSoldOutEvents();
    }

    @GetMapping("/upcoming/category/{categoryId}")
    public List<Event> getUpcomingEventsByCategory(@PathVariable Long categoryId) {
        return eventService.findUpcomingByCategory(categoryId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/{id}")
    public Event updateEvent(
            @PathVariable Long id,
            @RequestBody @Valid UpdateEventDTO dto,
            @AuthenticationPrincipal User user) {
        return eventService.updateEvent(id, dto, user);
    }
}