package com.example.requestmanager.service;

import com.example.requestmanager.dto.KafkaDto;
import com.example.requestmanager.dto.RecipientDto;
import com.example.requestmanager.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    private final KafkaTemplate<String, KafkaDto> kafkaTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public NotificationService(KafkaTemplate<String, KafkaDto> kafkaTemplate, DiscoveryClient discoveryClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.discoveryClient = discoveryClient;
    }

    public void sendMessage(List<RecipientDto> recipientDtos, Template template, String topic){
        for (List<RecipientDto> recipientsToSend : splitRecipients(recipientDtos)){
            KafkaDto kafkaDto = new KafkaDto(recipientsToSend, template);
            kafkaTemplate.send(topic, kafkaDto);
        }

    }

    public List<List<RecipientDto>> splitRecipients(List<RecipientDto> recipientDtos){

        int size = recipientDtos.size();
        int parts = discoveryClient.getInstances("SENDER").size();
        int partitionSize = (int) Math.ceil((double) size / parts);
        List<List<RecipientDto>> subLists = new ArrayList<>();

        for (int i = 0; i < size; i += partitionSize) {
            subLists.add(recipientDtos.subList(i, Math.min(i + partitionSize, size)));
        }

        return subLists;
    }
}
