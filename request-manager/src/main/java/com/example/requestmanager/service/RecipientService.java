package com.example.requestmanager.service;

import com.example.requestmanager.dto.RecipientDto;
import com.example.requestmanager.model.Recipient;
import com.example.requestmanager.model.User;
import com.example.requestmanager.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RecipientService {

    private final RecipientRepository recipientRepository;

    @Autowired
    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @Transactional
    public void save(Recipient recipient) {
        recipientRepository.save(recipient);
    }

    public List<Recipient> findAll(){
        return recipientRepository.findAll();
    }

    public Optional<Recipient> findById(int id){
        return recipientRepository.findById(id);
    }

    @Transactional
    public void delete(Recipient recipient){
        recipientRepository.delete(recipient);
    }

    @Transactional
    public void update(User user, Recipient recipient, int id) {
        recipient.setId(id);
        recipient.setOwner(user);
        recipientRepository.save(recipient);
    }
}
