package com.base.config;

import com.base.QueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueNotificationConfig {

    @Bean
    public Queue userConfirmActiveQueue() {
        return QueueBuilder.durable(QueueConstant.QUEUE_USER_CONFIRM_ACTIVE).build();
    }
}
