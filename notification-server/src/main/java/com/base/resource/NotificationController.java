package com.base.resource;

import com.base.domain.response.WrapResponse;
import com.base.request.domain.NotificationResponse;
import com.base.request.domain.QueryNotificationRequest;
import com.base.service.NotificationService;
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
