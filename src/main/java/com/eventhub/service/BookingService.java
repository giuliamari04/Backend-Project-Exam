package com.eventhub.service;

import com.eventhub.entity.Booking;
import com.eventhub.entity.Event;
import com.eventhub.entity.User;
import com.eventhub.exception.custom.EventFullException;
import com.eventhub.exception.custom.EventNotFoundException;
import com.eventhub.exception.custom.UnauthorizedActionException;
import com.eventhub.exception.custom.UserAlreadyBookedException;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final NotificationService notificationService;

    public Booking bookEvent(Long eventId) {

        User user = getAuthenticatedUser();

        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

        if (bookingRepository.existsByUserAndEvent(user, event)) {
            throw new UserAlreadyBookedException();
        }

        Integer bookingsCount = bookingRepository.countByEvent(event);

        if (bookingsCount >= event.getCapacity()) {
            throw new EventFullException();
        }

        Booking booking = Booking.builder()
                .bookingDate(LocalDateTime.now())
                .user(user)
                .event(event)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        notificationService.createNotification(
                user,
                "Your booking for event '" + event.getTitle() + "' has been confirmed"
        );

        return savedBooking;
    }

    public List<Booking> myBookings() {

        User user = getAuthenticatedUser();

        return bookingRepository.findByUser(user);
    }

    public void cancelBooking(Long bookingId) {

        User user = getAuthenticatedUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchElementException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException();
        }

        notificationService.createNotification(
                user,
                "Your booking for event '" + booking.getEvent().getTitle() + "' has been cancelled"
        );

        bookingRepository.delete(booking);
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}