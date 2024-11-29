package com.henry.domain;

import com.henry.QueueConstant;
import com.henry.base.BaseObjectLoggAble;
import com.henry.service.NotificationService;
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
}
