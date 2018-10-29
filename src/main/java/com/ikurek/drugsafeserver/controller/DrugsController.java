package com.ikurek.drugsafeserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.model.Packaging;
import com.ikurek.drugsafeserver.model.Substance;
import com.ikurek.drugsafeserver.repository.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/")
@Slf4j
public class DrugsController {

    private ObjectMapper objectMapper;

    private DrugRepository drugRepository;

    public DrugsController(ObjectMapper objectMapper, DrugRepository drugRepository) {
        this.objectMapper = objectMapper;
        this.drugRepository = drugRepository;
    }

    @GetMapping("/v1/drugs/test")
    public void createTestDrug() {

        Set<Packaging> packagingArrayList = new HashSet<>();
        Set<Substance> substancesArrayList = new HashSet<>();

        Packaging testPackage = new Packaging();
        testPackage.setSize(10L);
        packagingArrayList.add(testPackage);

        Substance testSubstance = new Substance();
        testSubstance.setName("test");
        substancesArrayList.add(testSubstance);

        Drug drug = new Drug();
        drug.setName("test");
        drug.setPackaging(packagingArrayList);
        drug.setSubstances(substancesArrayList);

        Drug savedDrug = drugRepository.save(drug);

        log.info("Saved " + savedDrug.getId());

    }
}
