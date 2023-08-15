package com.example.requestmanager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class RequestManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestManagerApplication.class, args);
    }

    //TODO separate services somehow

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().build();
    }
}
