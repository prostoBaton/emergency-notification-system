package com.example.requestmanager.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic emailTopic(){
        return TopicBuilder.name("send-email").partitions(10).build();
    }
    @Bean
    public NewTopic PhoneTopic(){
        return TopicBuilder.name("send-phone").partitions(10).build();
    }
    @Bean
    public NewTopic AllTopic(){
        return TopicBuilder.name("send-all").partitions(10).build();
    }
}
