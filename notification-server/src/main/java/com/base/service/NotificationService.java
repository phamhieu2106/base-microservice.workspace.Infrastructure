package com.base.service;

import com.base.BaseObjectLoggAble;
import com.base.domain.entity.NotificationEntity;
import com.base.exception.ServiceException;
import com.base.repository.NotificationRepository;
import com.base.request.SignUpRequest;
import com.base.request.constant.NotificationErrorCode;
import com.base.request.constant.NotificationStatus;
import com.base.request.domain.CreateNotificationRequest;
import com.base.request.domain.NotificationResponse;
import com.base.request.domain.QueryNotificationRequest;
import com.base.util.MappingUtils;
import com.base.util.PageableUtils;
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

    public void sendUserEmailConfirmActive(SignUpRequest request) {
        logger.info("Bấm vào đường link ${} để xác thực user {}", request.getTokenConfirm(), request.getUsername());
    }
}
