package com.example.requestmanager.service;

import com.example.requestmanager.model.Recipient;
import com.example.requestmanager.model.User;
import com.example.requestmanager.repository.RecipientRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public void saveFromCsv(User user, MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            CsvToBean<Recipient> csvToBean = new CsvToBeanBuilder(reader)
                    .withSeparator(';')
                    .withType(Recipient.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Recipient> recipients = csvToBean.parse();
            for (Recipient recipient : recipients) {
                recipient.setOwner(user);
            }

            recipientRepository.saveAll(recipients);

        } catch (IOException e) {//TODO some error prob idk
             }
    }
    @Transactional
    public void saveFromXlsx(User user, MultipartFile file){

        List<Recipient> recipients = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);

                System.out.println(String.format("%.0f", row.getCell(0).getNumericCellValue()));

                Recipient recipient = new Recipient(String.format("%.0f",row.getCell(0).getNumericCellValue()), row.getCell(1).getStringCellValue());
                recipient.setOwner(user);
                recipients.add(recipient);
            }
        } catch (IOException e) { //todo exception
        }
        if (!recipients.isEmpty())
            recipientRepository.saveAll(recipients);
        //todo else throw new ...
    }
}
