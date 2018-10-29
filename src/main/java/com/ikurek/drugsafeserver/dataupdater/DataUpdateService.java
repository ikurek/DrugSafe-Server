package com.ikurek.drugsafeserver.dataupdater;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.repository.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Set;


@Service
@Slf4j
public class DataUpdateService {

    private Environment environment;
    private DrugRepository drugRepository;

    public DataUpdateService(Environment environment, DrugRepository drugRepository) {
        this.environment = environment;
        this.drugRepository = drugRepository;
    }

    @PostConstruct
    public void bootUpdate() {
        updateDatabase();
    }

    @Scheduled(cron = "0 0 * * * *")
    public void scheduledUpdate() {
        updateDatabase();
    }

    private void updateDatabase() {

        if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
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


            drugRepository.saveAll(drugs);
        }


    }


}
