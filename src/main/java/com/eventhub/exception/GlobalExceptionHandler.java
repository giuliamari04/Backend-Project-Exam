package com.eventhub.exception;

import com.eventhub.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
                return buildResponse(HttpStatus.CONFLICT, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
                return buildResponse(HttpStatus.UNAUTHORIZED, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
                return buildResponse(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(EventNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleEventNotFound(EventNotFoundException ex) {
                return buildResponse(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(CategoryNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex) {
                return buildResponse(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(EventFullException.class)
        public ResponseEntity<ErrorResponse> handleEventFull(EventFullException ex) {
                return buildResponse(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(UserAlreadyBookedException.class)
        public ResponseEntity<ErrorResponse> handleUserAlreadyBooked(UserAlreadyBookedException ex) {
                return buildResponse(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(UnauthorizedActionException.class)
        public ResponseEntity<ErrorResponse> handleUnauthorizedAction(UnauthorizedActionException ex) {
                return buildResponse(HttpStatus.FORBIDDEN, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
                return buildResponse(
                                HttpStatus.FORBIDDEN,
                                "ACCESS_DENIED",
                                "You do not have permission to access this resource");
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

                String message = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .orElse("Validation error");

                return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message);
        }

        @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex) {
                return buildResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage());
        }

        @ExceptionHandler(BaseException.class)
        public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
                return buildResponse(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

                ex.printStackTrace();

                return buildResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "INTERNAL_SERVER_ERROR",
                                ex.getMessage());
        }

        @ExceptionHandler(UserAlreadyReviewedException.class)
        public ResponseEntity<ErrorResponse> handleUserAlreadyReviewed(UserAlreadyReviewedException ex) {
                return buildResponse(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(BookingRequiredException.class)
        public ResponseEntity<ErrorResponse> handleBookingRequired(BookingRequiredException ex) {
                return buildResponse(HttpStatus.FORBIDDEN, ex.getCode(), ex.getMessage());
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
                return buildResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", ex.getMessage());
        }

        private ResponseEntity<ErrorResponse> buildResponse(
                        HttpStatus status,
                        String error,
                        String message) {
                return ResponseEntity
                                .status(status)
                                .body(new ErrorResponse(
                                                status.value(),
                                                error,
                                                message));
        }
}