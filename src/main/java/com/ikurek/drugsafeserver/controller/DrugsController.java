package com.ikurek.drugsafeserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.exception.EmptyRequestBodyException;
import com.ikurek.drugsafeserver.exception.MalformedJsonException;
import com.ikurek.drugsafeserver.exception.NoDataProvidedException;
import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.model.Packaging;
import com.ikurek.drugsafeserver.model.Substance;
import com.ikurek.drugsafeserver.repository.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    @GetMapping("/v1/drugs")
    public String getDrugsByName(@RequestBody String json) {

        String name;

        // Validate if request body is present
        if (json == null || json.trim().isEmpty()) throw new EmptyRequestBodyException();

        // Attempt json parse to read name
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            name = jsonNode.get("name").asText();
        } catch (IOException e) {
            throw new MalformedJsonException();
        }

        // Validate if name is present
        if (name == null || name.trim().isEmpty()) throw new NoDataProvidedException();

        // Find drug with specified name
        Set<Drug> foundDrugs = drugRepository.findAllByNameContains(name);
        log.info("Found " + foundDrugs.size() + " drugs for name " + name);

        // Map drugs to json and return
        try {
            return objectMapper.writeValueAsString(foundDrugs);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

    }
}
