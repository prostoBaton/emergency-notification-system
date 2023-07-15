package com.example.requestmanager.controller;


import com.example.requestmanager.model.User;
import com.example.requestmanager.service.BatchService;
import com.example.requestmanager.service.JwtDecoder;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/batch")
public class BatchController {

    private final BatchService batchService;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public BatchController(BatchService batchService, JwtDecoder jwtDecoder) {
        this.batchService = batchService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/csv")
    public String uploadCsv(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                            @RequestPart @NotNull(message = "csv shouldn't be empty") MultipartFile file) {

        User user = jwtDecoder.extractUser(authHeader.substring(7));
        batchService.saveFromCsv(user, file);

        return "gowno";
    }
}
