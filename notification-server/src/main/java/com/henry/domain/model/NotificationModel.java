package com.henry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationModel {
    private Long id;
    private String recipient;
    private String message;
    private String type; // e.g., EMAIL, SMS
    private LocalDateTime createdAt;
}