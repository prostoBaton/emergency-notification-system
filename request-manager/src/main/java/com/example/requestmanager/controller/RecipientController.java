package com.example.requestmanager.controller;

import com.example.requestmanager.dto.RecipientDto;
import com.example.requestmanager.exception.EntityNotFoundException;
import com.example.requestmanager.model.Recipient;
import com.example.requestmanager.model.User;
import com.example.requestmanager.service.JwtDecoder;
import com.example.requestmanager.service.RecipientService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipient")
public class RecipientController {

    private final JwtDecoder jwtDecoder;
    private final RecipientService recipientService;
    private final ModelMapper modelMapper;

    @Autowired
    public RecipientController(JwtDecoder jwtDecoder, RecipientService recipientService, ModelMapper modelMapper) {
        this.jwtDecoder = jwtDecoder;
        this.recipientService = recipientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get")
    public List<RecipientDto> getRecipients(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        List<Recipient> recipients =  jwtDecoder.extractUser(authHeader.substring(7)).getRecipients();

        List<RecipientDto> recipientsDto = new ArrayList<>();

        for (Recipient recipient : recipients) {
            RecipientDto recipientDto = new RecipientDto(recipient.getPhone(), recipient.getEmail());
            recipientsDto.add(recipientDto);
        }
        return recipientsDto;
    }

    @GetMapping("/get/{id}")
    public RecipientDto getRecipient(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                     @PathVariable("id") int id){
        User user = jwtDecoder.extractUser(authHeader.substring(7));
        Recipient recipient = recipientService.findById(id).orElse(null);
        for (Recipient userRecipient : user.getRecipients()) {
            if (userRecipient.equals(recipient))
                return new RecipientDto(recipient.getPhone(), recipient.getEmail());
        }
        throw new EntityNotFoundException("recipient not found or you don't own them");
    }

    @PostMapping("/create")
    public String create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                         @RequestBody @Valid RecipientDto recipientDto){
        Recipient recipient = modelMapper.map(recipientDto, Recipient.class);
        recipient.setOwner(jwtDecoder.extractUser(authHeader.substring(7)));

        recipientService.save(recipient);
        return "recipient has been added";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                         @PathVariable int id){
        User user = jwtDecoder.extractUser(authHeader.substring(7));
        for (Recipient userRecipient : user.getRecipients()) {
            if (userRecipient.getId() == id) {
                recipientService.delete(recipientService.findById(id).get());
                return "recipient has been deleted";
            }
        }
        throw new EntityNotFoundException("recipient not found");
    }

    @PatchMapping("/update/{id}")
    public String update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                         @PathVariable int id,
                         @RequestBody @Valid RecipientDto recipientDto){
        User user = jwtDecoder.extractUser(authHeader.substring(7));

        for (Recipient userRecipient : user.getRecipients()) {
            if (userRecipient.getId() == id) {
                Recipient recipient = modelMapper.map(recipientDto, Recipient.class);
                recipientService.update(user, recipient, id);
                return "recipient has been updated";
            }
        }
        throw new EntityNotFoundException("recipient not found");
    }
}
