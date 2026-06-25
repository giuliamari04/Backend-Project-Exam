package com.eventhub.controller;

import com.eventhub.entity.Booking;
import com.eventhub.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{eventId}")
    public Booking bookEvent(@PathVariable Long eventId) {
        return bookingService.bookEvent(eventId);
    }

    @GetMapping("/my")
    public List<Booking> myBookings() {
        return bookingService.myBookings();
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}