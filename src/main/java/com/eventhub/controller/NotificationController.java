package com.eventhub.controller;

import com.eventhub.dto.NotificationResponseDTO;
import com.eventhub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my")
    public List<NotificationResponseDTO> getMyNotifications() {
        return notificationService.getMyNotifications();
    }

    @GetMapping("/my/unread")
    public List<NotificationResponseDTO> getMyUnreadNotifications() {
        return notificationService.getMyUnreadNotifications();
    }

    @PatchMapping("/{notificationId}/read")
    public NotificationResponseDTO markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
    }
}