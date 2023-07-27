package com.example.requestmanager.service;

import com.example.requestmanager.model.Recipient;
import com.example.requestmanager.model.User;
import com.example.requestmanager.repository.RecipientRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

        } catch (IOException e) {}
    }
    @Transactional
    public void saveFromXlsx(User user, MultipartFile file){

        List<Recipient> recipients = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

                XSSFRow row = sheet.getRow(i);
                Recipient recipient = new Recipient();

                if (row.getCell(0) != null)
                    recipient.setPhone(String.format("%.0f", row.getCell(0).getNumericCellValue()));
                if (row.getCell(1) != null)
                    recipient.setEmail(row.getCell(1).getStringCellValue());

                recipient.setOwner(user);
                recipients.add(recipient);
            }
        } catch (IOException e) {}
        if (!recipients.isEmpty())
            recipientRepository.saveAll(recipients);
        //todo else throw new ...
    }


    public byte[] getXlsx(User user) {

        List<Recipient> recipients = user.getRecipients();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Recipients");

            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Id");
            headerRow.createCell(1).setCellValue("Phone");
            headerRow.createCell(2).setCellValue("Email");

            for (int i = 0; i < recipients.size(); i++){

                Recipient recipient = recipients.get(i);

                XSSFRow row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(recipient.getId());
                row.createCell(1).setCellValue(recipient.getPhone());
                row.createCell(2).setCellValue(recipient.getEmail());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
