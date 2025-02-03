package com.henry;

import com.henry.config.GradlePropertiesLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication(scanBasePackages = "com.henry")
@EnableFeignClients
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AuthServerApplication.class);
        application.addInitializers(applicationContext -> {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            new GradlePropertiesLoader(environment).loadGradleProperties();
        });
        application.run(args);
    }
}