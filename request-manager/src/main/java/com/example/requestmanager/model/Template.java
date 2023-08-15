package com.example.requestmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Template")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotNull(message = "title shouldn't be empty")
    @NotEmpty(message = "title shouldn't be empty")
    @Length(max = 50, message = "title length shouldn't be exceed 50")
    private String title;

    @Column(name = "content")
    @NotNull(message = "content shouldn't be empty")
    @NotEmpty(message = "content shouldn't be empty")
    @Length(max = 200, message = "content length shouldn't be exceed 200")
    private String content;

    public Template(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Template() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
