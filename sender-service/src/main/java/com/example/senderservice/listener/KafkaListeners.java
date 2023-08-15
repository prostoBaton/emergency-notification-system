package com.example.senderservice.listener;

import com.example.senderservice.dto.KafkaDto;
import com.example.senderservice.dto.RecipientDto;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @Value("${email_key}")
    private String emailKey;
    @Value("${domain}")
    private String domain;

    @KafkaListener(topics = "send-email", containerFactory = "userKafkaListenerContainerFactory", groupId = "app.1")
    public void emailListener(KafkaDto kafkaDto) throws UnirestException {

        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(emailKey)
                .createApi(MailgunMessagesApi.class);
                for (RecipientDto recipientDto: kafkaDto.getRecipientDtos()) {
                    if (recipientDto.getEmail() != null && !recipientDto.getEmail().isEmpty()) {
                        Message message = Message.builder()
                                .from("emergencynotificationproject@gmail.com")
                                .to(recipientDto.getEmail())
                                .subject(kafkaDto.getTemplate().getTitle())
                                .text(kafkaDto.getTemplate().getContent())
                                .build();

                        mailgunMessagesApi.sendMessage(domain, message);
                    }
                }
    }

    @KafkaListener(topics = "send-phone", containerFactory = "userKafkaListenerContainerFactory", groupId = "app.1")
    public void phoneListener(KafkaDto kafkaDto){//TODO
    }

    @KafkaListener(topics = "send-all", containerFactory = "userKafkaListenerContainerFactory", groupId = "app.1")
    public void sendAllListener(KafkaDto kafkaDto){
        try {
            emailListener(kafkaDto);
            phoneListener(kafkaDto);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

    }

}
