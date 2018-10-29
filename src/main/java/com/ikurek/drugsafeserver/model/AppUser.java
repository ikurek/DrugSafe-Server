package com.ikurek.drugsafeserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
    private String email;

    @Column(name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}