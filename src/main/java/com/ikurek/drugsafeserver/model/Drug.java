package com.ikurek.drugsafeserver.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DRUGS")
@Data
public class Drug implements Serializable {

    @Id
    @Column(name = "DRUG_ID")
    private Long id;

    @Column(name = "NAME")
    private String name; // nazwaProduktu

    @Column(name = "COMMON_NAME")
    private String commonName; // nazwaPowszechnieStosowana

    @Column(name = "DRUG_TYPE")
    private String drugType; // rodzajPreparatu

    @Column(name = "AMMOUNT_OF_SUBSTANCE", length = 512)
    private String ammountOfSubstance; // moc

    @Column(name = "TYPE")
    private String type; // postac

    @Column(name = "OWNER")
    private String owner; // podmiotOdpowiedzialny

    @Column(name = "PROCEDURE_TYPE")
    private String procedureType; // typProcedury

    @Column(name = "PERMISSION_NUMBER")
    private Long permissionNumber; // numerPozwolenia

    @Column(name = "PERMISSION_EXPIRY")
    private String permissionExpiry; //waznoscPozwolenia

    @Column(name = "ATC")
    private String atc; // kodATC

    @ElementCollection(targetClass = String.class)
    private Set<String> substances = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "drug_packaging",
            joinColumns = {@JoinColumn(name = "DRUG_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "PACKAGING_ID", nullable = false)})
    private Set<Packaging> packaging = new HashSet<>();

}
