package com.ikurek.drugsafeserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.exception.EmptyRequestBodyException;
import com.ikurek.drugsafeserver.exception.MalformedJsonException;
import com.ikurek.drugsafeserver.exception.NoDataProvidedException;
import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.service.DrugServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
        Set<Drug> foundDrugs = drugService.getDrugsWhereNameOrSubstanceContains(name);
        log.info("Found " + foundDrugs.size() + " drugs for name " + name);

        // Map drugs to json and return
        try {
            return objectMapper.writeValueAsString(foundDrugs);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }

    }
}
