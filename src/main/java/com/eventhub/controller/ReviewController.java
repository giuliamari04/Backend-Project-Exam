package com.eventhub.controller;

import com.eventhub.dto.ReviewRequestDTO;
import com.eventhub.dto.ReviewResponseDTO;
import com.eventhub.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponseDTO createReview(@RequestBody @Valid ReviewRequestDTO dto) {
        return reviewService.createReview(dto);
    }

    @GetMapping("/event/{eventId}")
    public List<ReviewResponseDTO> getReviewsByEvent(@PathVariable Long eventId) {
        return reviewService.getReviewsByEvent(eventId);
    }

    @GetMapping("/my")
    public List<ReviewResponseDTO> getMyReviews() {
        return reviewService.getMyReviews();
    }

    @GetMapping("/event/{eventId}/average")
    public Double getAverageRatingByEvent(@PathVariable Long eventId) {
        return reviewService.getAverageRatingByEvent(eventId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}