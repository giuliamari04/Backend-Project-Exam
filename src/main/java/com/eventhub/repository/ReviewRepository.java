package com.eventhub.repository;

import com.eventhub.entity.Event;
import com.eventhub.entity.Review;
import com.eventhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByEventOrderByCreatedAtDesc(Event event);

    List<Review> findByUser(User user);

    boolean existsByUserAndEvent(User user, Event event);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.event = :event")
    Double findAverageRatingByEvent(Event event);
}