package com.example.requestmanager.repository;

import com.example.requestmanager.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
}
