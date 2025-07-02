package com.base.action;

import com.base.BaseObjectLoggAble;
import com.base.QueueConstant;
import com.base.request.SignUpRequest;
import com.base.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueHandler extends BaseObjectLoggAble {

    private final NotificationService notificationService;

    @RabbitListener(queues = QueueConstant.QUEUE_USER_CONFIRM_ACTIVE)
    private void handleSendUserEmailConfirmActiveQueue(String username) {
        try {
            notificationService.sendUserEmailConfirmActive(username);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    @RabbitListener(queues = QueueConstant.QUEUE_USER_CONFIRM_ACTIVE)
    private void handleSignUpUser(SignUpRequest request) {
        notificationService.sendUserEmailConfirmActive(request);
    }
}
