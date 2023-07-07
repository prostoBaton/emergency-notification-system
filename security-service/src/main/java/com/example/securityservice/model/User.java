package com.example.securityservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    @NotEmpty(message = "Username shouldn't be empty")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;

//    @OneToMany(mappedBy = "owner")
//    private List<Recipient> recipients;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public List<Recipient> getRecipients() {
//        return recipients;
//    }
//
//    public void setRecipients(List<Recipient> recipients) {
//        this.recipients = recipients;
//    }
}
