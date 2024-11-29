package com.henry.repository;

import com.henry.domain.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {
    Page<NotificationEntity> findAllByRecipient(String recipient, Pageable pageable);

    Page<NotificationEntity> findAllByRecipientAndStatusIs(String recipient, Integer status, Pageable pageable);

    long countByRecipientAndStatusIs(String recipient, Integer status);
}
