package com.example.requestmanager.model;

import com.opencsv.bean.CsvIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Recipients")
public class Recipient {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "sequence_id_auto_gen", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_auto_gen")
    private int id;

    @Column(name = "phone")
    @Pattern(regexp="(^$|[0-9]{11})")
    private String phone;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @CsvIgnore
    private User owner;

    public Recipient(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public Recipient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
