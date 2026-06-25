package com.eventhub.service;

import com.eventhub.dto.ReviewRequestDTO;
import com.eventhub.dto.ReviewResponseDTO;
import com.eventhub.entity.Event;
import com.eventhub.entity.Review;
import com.eventhub.entity.User;
import com.eventhub.exception.custom.BookingRequiredException;
import com.eventhub.exception.custom.EventNotFoundException;
import com.eventhub.exception.custom.UnauthorizedActionException;
import com.eventhub.exception.custom.UserAlreadyReviewedException;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    public ReviewResponseDTO createReview(ReviewRequestDTO dto) {

        User user = getAuthenticatedUser();

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(EventNotFoundException::new);

        if (!bookingRepository.existsByUserAndEvent(user, event)) {
            throw new BookingRequiredException();
        }

        if (reviewRepository.existsByUserAndEvent(user, event)) {
            throw new UserAlreadyReviewedException();
        }

        Review review = Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .user(user)
                .event(event)
                .build();

        Review savedReview = reviewRepository.save(review);

        return mapToResponseDTO(savedReview);
    }

    public List<ReviewResponseDTO> getReviewsByEvent(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

        return reviewRepository.findByEventOrderByCreatedAtDesc(event)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<ReviewResponseDTO> getMyReviews() {

        User user = getAuthenticatedUser();

        return reviewRepository.findByUser(user)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public Double getAverageRatingByEvent(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

        Double average = reviewRepository.findAverageRatingByEvent(event);

        return average == null ? 0.0 : average;
    }

    public void deleteReview(Long reviewId) {

        User user = getAuthenticatedUser();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("Review not found"));

        boolean isOwner = review.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedActionException();
        }

        reviewRepository.delete(review);
    }

    private ReviewResponseDTO mapToResponseDTO(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .userId(review.getUser().getId())
                .userEmail(review.getUser().getEmail())
                .eventId(review.getEvent().getId())
                .eventTitle(review.getEvent().getTitle())
                .build();
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}