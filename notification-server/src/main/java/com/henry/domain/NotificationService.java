package com.henry.domain;

import com.henry.base.BaseObjectLoggAble;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class NotificationService extends BaseObjectLoggAble {

    public void sendUserEmailConfirmActive(String username) throws MessagingException {
        logger.info("Bấm vào đường link ${123} để xác thực user {}", username);
    }
}
