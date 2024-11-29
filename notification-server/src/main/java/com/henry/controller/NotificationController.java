package com.henry.controller;

import com.henry.base.controller.BaseController;
import com.henry.base.domain.response.WrapResponse;
import com.henry.request.domain.NotificationResponse;
import com.henry.request.domain.QueryNotificationRequest;
import com.henry.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${prefix.api}/${spring.application.name}/notifications")
public class NotificationController extends BaseController {

    private final NotificationService notificationService;

    @PostMapping("/read/{id}")
    public CompletableFuture<WrapResponse<String>> updateReadNotification(@PathVariable String id) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(notificationService.updateReadNotification(id)));
    }

    @GetMapping("/count-number-of-notification-unread-by-recipient/{recipient}")
    public CompletableFuture<WrapResponse<Long>> countNumberOfNotificationUnreadByRecipient(@PathVariable String recipient) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(notificationService.countNumberOfNotificationUnreadByRecipient(recipient)));
    }

    @PostMapping("/find-all-by-recipient/{recipient}")
    public CompletableFuture<WrapResponse<Page<NotificationResponse>>> findAllByRecipient(@PathVariable String recipient,
                                                                                          @RequestBody QueryNotificationRequest request) {
        return CompletableFuture.supplyAsync(()
                -> WrapResponse.ok(notificationService.findAllByRecipient(recipient, request)));
    }
}
