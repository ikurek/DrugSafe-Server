package com.ikurek.drugsafeserver.service;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.model.Substance;
import com.ikurek.drugsafeserver.repository.DrugRepository;
import com.ikurek.drugsafeserver.repository.SubstanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;
    private final SubstanceRepository substanceRepository;

    public DrugServiceImpl(DrugRepository drugRepository, SubstanceRepository substanceRepository) {
        this.drugRepository = drugRepository;
        this.substanceRepository = substanceRepository;
    }

    @Override
    public void merge(Set<Drug> drugs) {
        Drug existingDrug;

        for (Drug drug : drugs) {
            existingDrug = drugRepository.findByGovernmentId(drug.getGovernmentId());

            // If drug was found
            if (existingDrug != null) {
                // Get ID of drug in local DB
                drug.setId(existingDrug.getId());
                // Save drug with ID assigned to perform update
                drugRepository.save(drug);

            } else {
                // Just save as new entity
                drugRepository.save(drug);
            }
        }

    }

    @Override
    public Long getDrugCount() {
        return drugRepository.count();
    }

    @Override
    public Set<Drug> getDrugsWhereNameOrSubstanceContains(String contained) {

        Set<Drug> finalSet = drugRepository.findAllByNameContainsOrCommonNameContains(contained);

        Set<Substance> substancesWithName = substanceRepository.findAllByNameContains(contained);

        for (Substance substance : substancesWithName) {

            for (Drug drug : substance.getDrugs()) {
                finalSet.add(drug);
            }

        }

        return finalSet;
    }
}
