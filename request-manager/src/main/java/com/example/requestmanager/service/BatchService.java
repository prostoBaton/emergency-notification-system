package com.example.requestmanager.service;

import com.example.requestmanager.model.Recipient;
import com.example.requestmanager.model.User;
import com.example.requestmanager.repository.RecipientRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BatchService {

    private final RecipientRepository recipientRepository;

    @Autowired
    public BatchService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    @Transactional
    public void saveFromCsv(User user, MultipartFile file){
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> rows = csvReader.readAll();
            List<Recipient> recipients = new ArrayList<>();
            for (String[] row :rows){
                List<String> credentials = List.of(row[0].split(";"));
                Recipient recipient = new Recipient(credentials.get(0), credentials.get(1));    //а если поменяют местами то че а? а как это исправить это пиздец так то
                recipient.setOwner(user);                                                       //TODO check 1 row for credentials sequence
                recipients.add(recipient);
            }

            recipientRepository.saveAll(recipients);

        } catch (IOException | CsvException e) {}
    }
}
