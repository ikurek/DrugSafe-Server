package com.ikurek.drugsafeserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PACKAGINGS")
@Data
public class Packaging implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACKAGING_ID")
    Long id;

    @Column(name = "GOVERNMENT_ID")
    Long packageId; // id

    @Column(name = "SIZE")
    Long size; // wielkosc

    @Column(name = "SIZE_UNIT")
    String sizeUnit; // jednostkaWielkosci

    @Column(name = "EAN")
    Long ean; // kodEAN

    @Column(name = "CATEGORY")
    String category; // kategoriaDostepnosci

    @Column(name = "REMOVED")
    Boolean removed; // skasowane

    @Column(name = "EU_NUMBER")
    String euNumber; // numerEu

    @Column(name = "PARALLER_DISTRIBUTOR")
    String parallelDistributor; // dystrybutorRownolegly

    @Column
    @ElementCollection(targetClass = Drug.class)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "drugs")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<Drug> drugs = new HashSet<>();

}
