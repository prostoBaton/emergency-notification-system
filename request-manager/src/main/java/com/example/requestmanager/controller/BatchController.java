package com.example.requestmanager.controller;


import com.example.requestmanager.exception.InvalidFileFormatException;
import com.example.requestmanager.model.Recipient;
import com.example.requestmanager.model.User;
import com.example.requestmanager.service.BatchService;
import com.example.requestmanager.service.JwtDecoder;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


@RestController
@RequestMapping("/batch")
public class BatchController {

    private final BatchService batchService;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public BatchController(BatchService batchService, JwtDecoder jwtDecoder) { //TODO exception if no file
        this.batchService = batchService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/save/csv")
    public String uploadCsv(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                            @RequestPart @NotNull MultipartFile file) {

        if (!Objects.equals(file.getContentType(), "text/csv"))
            throw new InvalidFileFormatException("invalid file format");

        User user = jwtDecoder.extractUser(authHeader.substring(7));
        batchService.saveFromCsv(user, file);

        return "recipients have been saved";
    }

    @PostMapping("/save/xlsx")
    public String uploadXlsx(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                            @RequestPart @NotNull MultipartFile file){

        if (!Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            throw new InvalidFileFormatException("invalid file format");

        User user = jwtDecoder.extractUser(authHeader.substring(7));
        batchService.saveFromXlsx(user, file);

        return "recipients have been saved";
    }

    @GetMapping("/get/csv")
    public void getCsv(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                           HttpServletResponse response){

        User user = jwtDecoder.extractUser(authHeader.substring(7));

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipients.csv\"");
        response.setContentType("application/csv");

        try {
            StatefulBeanToCsv<Recipient> writer = new StatefulBeanToCsvBuilder<Recipient>(response.getWriter())
                    .withSeparator(';')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            writer.write(user.getRecipients());
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get/xlsx")
    public ResponseEntity<Resource> getXlsx(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        User user = jwtDecoder.extractUser(authHeader.substring(7));

        byte[] data = batchService.getXlsx(user);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"recipients.xlsx\"")
                .body(byteArrayResource);
    }
}
