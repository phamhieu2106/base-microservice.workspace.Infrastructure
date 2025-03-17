package com.henry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(NotificationServerApplication.class);
        application.run(args);
    }
}