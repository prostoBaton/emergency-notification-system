package com.example.requestmanager.dto;

import com.example.requestmanager.model.Template;

import java.util.List;

public class KafkaDto {
    List<RecipientDto> recipientDtos;
    Template template;

    public KafkaDto(List<RecipientDto> recipientDtos, Template template) {
        this.recipientDtos = recipientDtos;
        this.template = template;
    }

    public KafkaDto() {
    }

    public List<RecipientDto> getRecipientDtos() {
        return recipientDtos;
    }

    public void setRecipientDtos(List<RecipientDto> recipientDtos) {
        this.recipientDtos = recipientDtos;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
