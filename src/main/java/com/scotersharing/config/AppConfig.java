package com.scotersharing.config;

import com.scotersharing.mapper.ScooterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ScooterMapper scooterMapper() {
        return new ScooterMapper();
    }
}
