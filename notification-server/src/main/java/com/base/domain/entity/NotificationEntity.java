package com.base.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "recipient_createdDate_idx", columnList = "recipient, createdDate"),
        @Index(name = "recipient_status_createdDate_idx", columnList = "recipient, status, createdDate")
})
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String recipient;
    private String message;
    private Integer status;
    private String type;
    private Date createdDate;
    private Date updatedDate;
}