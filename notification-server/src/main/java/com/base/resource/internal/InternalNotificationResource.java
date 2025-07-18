package com.base.resource.internal;

import com.base.domain.CreateNotificationRequest;
import com.base.domain.response.WrapResponse;
import com.base.resource.BaseResource;
import com.base.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/${prefix.api}/${spring.application.name}/notifications")
public class InternalNotificationResource extends BaseResource {

    private final NotificationService notificationService;

    @PostMapping("/create")
    public WrapResponse<String> createNotification(@Valid @RequestBody CreateNotificationRequest request) {
        return WrapResponse.ok(notificationService.createNotification(request));
    }
}
