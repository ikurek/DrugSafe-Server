package com.ikurek.drugsafeserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.exception.DataNotFoundException;
import com.ikurek.drugsafeserver.exception.NoDataProvidedException;
import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.service.DrugServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/")
@Slf4j
public class DrugsController {

    private ObjectMapper objectMapper;
    private DrugServiceImpl drugService;

    public DrugsController(ObjectMapper objectMapper, DrugServiceImpl drugService) {
        this.objectMapper = objectMapper;
        this.drugService = drugService;
    }


    @GetMapping("/v1/drugs")
    public String getDrugsByName(@RequestParam("name") String name) {

        // Validate if name is present
        if (name == null || name.trim().isEmpty()) throw new NoDataProvidedException();

        // Find drug with specified name
        Set<Drug> foundDrugs = drugService.getDrugsWhereNameOrSubstanceContains(name);
        log.info("Found " + foundDrugs.size() + " drugs for name " + name);

        // Check if anything was found
        if (foundDrugs.isEmpty()) {
            throw new DataNotFoundException();
        } else {
            // Map drugs to json and return
            try {
                return objectMapper.writeValueAsString(foundDrugs);
            } catch (JsonProcessingException e) {
                return e.getMessage();
            }
        }
    }

    @GetMapping("/v1/drugs/{id}")
    public String getSingleDrugById(@PathVariable Long id) {

        // Query for drug by ID
        Drug drug = drugService.getDrugWithId(id);

        // Check if drug was found
        if (drug == null) {
            throw new DataNotFoundException();
        } else {
            try {
                return objectMapper.writeValueAsString(drug);
            } catch (JsonProcessingException e) {
                return e.getMessage();
            }
        }
    }

    @GetMapping("/v1/replacements/{id}")
    public String getDrugReplacementsByID(@PathVariable Long id) {
        // Query for drug by ID
        Drug drug = drugService.getDrugWithId(id);

        // Check if drug was found
        if (drug == null) {
            throw new DataNotFoundException();
        } else {

            Set<Drug> replacements = drugService.getDrugsWhereCommonNameIs(drug.getCommonName());

            try {
                return objectMapper.writeValueAsString(replacements);
            } catch (JsonProcessingException e) {
                return e.getMessage();
            }
        }
    }
}
