package com.henry;

import com.henry.config.GradlePropertiesLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.ConfigurableEnvironment;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaServerApplication.class);
        application.addInitializers(applicationContext -> {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            new GradlePropertiesLoader(environment).loadGradleProperties();
        });
        application.run(args);
    }
}