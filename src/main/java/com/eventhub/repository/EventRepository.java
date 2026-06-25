package com.eventhub.repository;

import com.eventhub.entity.Event;
import com.eventhub.entity.PhysicalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategoryId(Long categoryId);

    List<Event> findByDateAfterOrderByDateAsc(LocalDateTime now);

    List<Event> findByTitleContainingIgnoreCase(String title);

    List<Event> findAllByOrderByDateAsc();

    @Query("SELECT e FROM PhysicalEvent e WHERE LOWER(e.city) = LOWER(:city)")
    List<PhysicalEvent> findPhysicalEventsByCity(String city);

    @Query("""
           SELECT e FROM Event e
           WHERE e.capacity <= (
                SELECT COUNT(b)
                FROM Booking b
                WHERE b.event = e
           )
           """)
    List<Event> findSoldOutEvents();

    @Query("""
           SELECT e FROM Event e
           WHERE e.date > :now
           AND (:categoryId IS NULL OR e.category.id = :categoryId)
           ORDER BY e.date ASC
           """)
    List<Event> findUpcomingByCategory(LocalDateTime now, Long categoryId);
}