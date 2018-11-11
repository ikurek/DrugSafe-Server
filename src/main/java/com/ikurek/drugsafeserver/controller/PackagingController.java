package com.ikurek.drugsafeserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.exception.DataNotFoundException;
import com.ikurek.drugsafeserver.exception.NoDataProvidedException;
import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.model.Packaging;
import com.ikurek.drugsafeserver.service.PackagingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@Slf4j
public class PackagingController {

    private ObjectMapper objectMapper;
    private PackagingServiceImpl packagingService;

    public PackagingController(ObjectMapper objectMapper, PackagingServiceImpl packagingService) {
        this.objectMapper = objectMapper;
        this.packagingService = packagingService;
    }

    @GetMapping("/packagings")
    public String getPackagingByEan(@RequestParam("ean") Long ean) {

        // Validadte if parameter was passed
        if (ean == null) throw new NoDataProvidedException();

        // Find package with given EAN
        log.info("Searching package for EAN: " + ean);
        Packaging packaging = packagingService.findPackagingByEan(ean);
        if (packaging == null) throw new DataNotFoundException();
        log.info("Package found: " + packaging);

        // Find drug bound with package
        log.info("Searching drug for package: " + packaging);
        Drug drug = packagingService.findDrugByPackaging(packaging);
        if (drug == null) throw new DataNotFoundException();
        log.info("Drug found: " + drug);

        // Return as JSON
        try {
            return objectMapper.writeValueAsString(drug);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
