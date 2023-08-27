package com.example.requestmanager.service;

import com.example.requestmanager.model.Template;
import com.example.requestmanager.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository, RestTemplate restTemplate) {
        this.templateRepository = templateRepository;
    }

    public Optional<Template> findById(int id){
        return templateRepository.findById(id);
    }

    public List<Template> findAll(){
        return templateRepository.findAll();
    }

    @Transactional
    public void save(Template template){
        templateRepository.save(template);
    }

    @Transactional
    public void delete(Template template) {
        templateRepository.delete(template);
    }

    @Transactional
    public void update(Template oldTemplate, Template template) {
        template.setId(oldTemplate.getId());
        templateRepository.save(template);
    }
}
