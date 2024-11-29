package com.henry.service;

import com.henry.base.BaseObjectLoggAble;
import com.henry.base.exception.ServiceException;
import com.henry.domain.entity.NotificationEntity;
import com.henry.repository.NotificationRepository;
import com.henry.request.constant.NotificationErrorCode;
import com.henry.request.constant.NotificationStatus;
import com.henry.request.domain.CreateNotificationRequest;
import com.henry.request.domain.NotificationResponse;
import com.henry.request.domain.QueryNotificationRequest;
import com.henry.util.MappingUtils;
import com.henry.util.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class NotificationService extends BaseObjectLoggAble {

    private final NotificationRepository notificationRepository;

    public String createNotification(CreateNotificationRequest request) {
        try {
            NotificationEntity notificationEntity = MappingUtils.mapObject(request, NotificationEntity.class);
            notificationEntity.setStatus(NotificationStatus.UNREAD);
            notificationEntity.setCreatedDate(new Date());
            return notificationRepository.save(notificationEntity).getId();
        } catch (Exception e) {
            logger.error(">>>> createNotification ERROR <<<<", e);
            return e.getMessage();
        }
    }

    public long countNumberOfNotificationUnreadByRecipient(String recipient) {
        return notificationRepository.countByRecipientAndStatusIs(recipient, NotificationStatus.UNREAD);
    }

    public String updateReadNotification(String id) {
        try {
            NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(() ->
                    new ServiceException(NotificationErrorCode.NOTIFICATION_NOT_FOUND));
            notificationEntity.setStatus(NotificationStatus.READ);
            notificationEntity.setUpdatedDate(new Date());
            return notificationRepository.save(notificationEntity).getId();
        } catch (Exception e) {
            logger.error(">>>> updateReadNotification ERROR <<<<", e);
            return e.getMessage();
        }
    }

    public Page<NotificationResponse> findAllByRecipient(String recipient, QueryNotificationRequest request) {
        Pageable pageable = PageableUtils.convertToPageable(request.getPageNumber(), request.getPageSize());
        Page<NotificationEntity> notificationEntityPage;
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            notificationEntityPage = notificationRepository.findAllByRecipientAndStatusIs(recipient, request.getStatus(), pageable);
        } else {
            notificationEntityPage = notificationRepository.findAllByRecipient(recipient, pageable);
        }
        return PageableUtils.convertPageToPageResponse(notificationEntityPage, NotificationResponse.class);
    }

    public void sendUserEmailConfirmActive(String username) throws MessagingException {
        logger.info("Bấm vào đường link ${123} để xác thực user {}", username);
    }
}
