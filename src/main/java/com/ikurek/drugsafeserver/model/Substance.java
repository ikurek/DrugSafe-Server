package com.ikurek.drugsafeserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SUBSTANCES")
@Data
public class Substance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSTANCE_ID")
    private Long id;

    @Column(name = "NAME")
    private String name; // nazwa

    @Column
    @ElementCollection(targetClass = Drug.class)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "drugs")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<Drug> drugs = new HashSet<>();


}
