package com.example.requestmanager.controller;

import com.example.requestmanager.dto.RecipientDto;
import com.example.requestmanager.exception.EntityNotFoundException;
import com.example.requestmanager.model.Recipient;
import com.example.requestmanager.model.Template;
import com.example.requestmanager.model.User;
import com.example.requestmanager.service.JwtDecoder;
import com.example.requestmanager.service.NotificationService;
import com.example.requestmanager.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/send")
public class NotificationController {

    private final JwtDecoder jwtDecoder;
    private final NotificationService notificationService;
    private final TemplateService templateService;

    @Autowired
    public NotificationController(JwtDecoder jwtDecoder, NotificationService notificationService, TemplateService templateService) {
        this.jwtDecoder = jwtDecoder;
        this.notificationService = notificationService;
        this.templateService = templateService;
    }

    //TODO merge 3 methods below in 1 (@PathVariable + exception?)

    @GetMapping("/all/{template_id}")
    public String sendAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
            ,@PathVariable int template_id){

        User user = jwtDecoder.extractUser(authHeader.substring(7));
        Template template = templateService.findById(template_id).orElseThrow(() -> new EntityNotFoundException("template not found"));
        List<RecipientDto> recipientDtos = new ArrayList<>();

        if (user.getRecipients().isEmpty())
            throw new EntityNotFoundException("you don't have any recipients");

        for (Recipient userRecipient : user.getRecipients()){
            recipientDtos.add(new RecipientDto(userRecipient.getPhone(), userRecipient.getEmail()));
        }

       notificationService.sendMessage(recipientDtos, template, "send-all");
        return "notifications have been sent";
    }

    @GetMapping("/email/{template_id}")
    public String sendEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
            ,@PathVariable int template_id){

        User user = jwtDecoder.extractUser(authHeader.substring(7));
        Template template = templateService.findById(template_id).orElseThrow(() -> new EntityNotFoundException("template not found"));
        List<RecipientDto> recipientDtos = new ArrayList<>();

        if (user.getRecipients().isEmpty())
            throw new EntityNotFoundException("you don't have any recipients");

        for (Recipient userRecipient : user.getRecipients()){
            recipientDtos.add(new RecipientDto(userRecipient.getPhone(), userRecipient.getEmail()));
        }

        notificationService.sendMessage(recipientDtos, template, "send-email");
        return "notifications have been sent";
    }

    @GetMapping("/phone/{template_id}")
    public String sendPhone(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
            ,@PathVariable int template_id){

        User user = jwtDecoder.extractUser(authHeader.substring(7));
        Template template = templateService.findById(template_id).orElseThrow(() -> new EntityNotFoundException("template not found"));
        List<RecipientDto> recipientDtos = new ArrayList<>();

        if (user.getRecipients().isEmpty())
            throw new EntityNotFoundException("you don't have any recipients");

        for (Recipient userRecipient : user.getRecipients()){
            recipientDtos.add(new RecipientDto(userRecipient.getPhone(), userRecipient.getEmail()));
        }

        notificationService.sendMessage(recipientDtos, template, "send-phone");
        return "notifications have been sent";
    }
}
