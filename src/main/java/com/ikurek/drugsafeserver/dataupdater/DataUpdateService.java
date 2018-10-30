package com.ikurek.drugsafeserver.dataupdater;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.service.DrugServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class DataUpdateService {

    private final DrugServiceImpl drugService;

    public DataUpdateService(DrugServiceImpl drugService) {
        this.drugService = drugService;
    }

    //@PostConstruct
    //public void bootUpdate() {
    //    updateDatabase();
    //}

    @Scheduled(cron = "0 0 * * * *")
    public void scheduledUpdate() {
        updateDatabase();
    }

    public void updateDatabase() {

            log.info("Running database update task");
            log.info("Building downloader");
            DataSourceDownloader dataSourceDownloader = new DataSourceDownloader();
            log.info("Validating file presence");
            dataSourceDownloader.validateFilePresence();
            log.info("Downloading new file");
            dataSourceDownloader.downloadDrugListAsXML();
            log.info("Building parser");
            DataSourceParser dataSourceParser = new DataSourceParser();
            log.info("Parsing");
            Set<Drug> drugs = dataSourceParser.run();
        log.info("Parsed " + drugs.size() + " drugs");

        //FIXME: No batch update operation? Really JPA? Really???
        drugService.merge(drugs);

        log.info("Storing " + drugService.getDrugCount() + " drugs in database");


    }


}
