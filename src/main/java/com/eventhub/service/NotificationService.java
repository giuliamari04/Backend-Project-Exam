package com.eventhub.service;

import com.eventhub.dto.NotificationResponseDTO;
import com.eventhub.entity.Notification;
import com.eventhub.entity.User;
import com.eventhub.exception.custom.UnauthorizedActionException;
import com.eventhub.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(User user, String message) {

        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .createdAt(LocalDateTime.now())
                .read(false)
                .build();

        notificationRepository.save(notification);
    }

    public List<NotificationResponseDTO> getMyNotifications() {

        User user = getAuthenticatedUser();

        return notificationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<NotificationResponseDTO> getMyUnreadNotifications() {

        User user = getAuthenticatedUser();

        return notificationRepository.findByUserAndReadFalseOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public NotificationResponseDTO markAsRead(Long notificationId) {

        User user = getAuthenticatedUser();

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException("Notification not found"));

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException();
        }

        notification.setRead(true);

        Notification savedNotification = notificationRepository.save(notification);

        return mapToResponseDTO(savedNotification);
    }

    public void deleteNotification(Long notificationId) {

        User user = getAuthenticatedUser();

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException("Notification not found"));

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedActionException();
        }

        notificationRepository.delete(notification);
    }

    private NotificationResponseDTO mapToResponseDTO(Notification notification) {
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .read(notification.isRead())
                .build();
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}