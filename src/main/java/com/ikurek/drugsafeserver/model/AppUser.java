package com.ikurek.drugsafeserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "EMAIL")
    @NonNull
    private String email;

    @Column(name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull
    private String password;

    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AppUser() {

    }
}