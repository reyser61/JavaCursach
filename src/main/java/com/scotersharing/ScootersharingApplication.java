package com.scotersharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableMethodSecurity(prePostEnabled = true)
public class ScootersharingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScootersharingApplication.class, args);
    }
}
