package com.example.requestmanager.controller;

import com.example.requestmanager.exception.EntityNotFoundException;
import com.example.requestmanager.model.Template;
import com.example.requestmanager.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/get")
    public List<Template> getTemplates(){
        return templateService.findAll();
    }

    @GetMapping("/get/{id}")
    public Template getTemplate(@PathVariable int id){
            return templateService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("template not found"));
    }

    @PostMapping("/create")
    public String create(@RequestBody @Valid Template template){
        templateService.save(template);
        return "template has been created";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        Template template = templateService.findById(id).orElseThrow(() -> new EntityNotFoundException("template not found"));
        templateService.delete(template);
        return "template has been deleted";
    }

    @PatchMapping("/update/{id}")
    public String update(@PathVariable int id,
                         @RequestBody @Valid Template template){
        Template oldTemplate = templateService.findById(id).orElseThrow(() -> new EntityNotFoundException("template not found"));
        templateService.update(oldTemplate, template);
        return "template has been updated";
    }

}
