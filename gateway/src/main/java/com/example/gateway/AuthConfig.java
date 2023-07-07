package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
