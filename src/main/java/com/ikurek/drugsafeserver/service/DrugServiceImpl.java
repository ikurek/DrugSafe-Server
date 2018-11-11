package com.ikurek.drugsafeserver.service;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.repository.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;

    public DrugServiceImpl(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Override
    public void merge(Set<Drug> drugs) {

        int PARTITIONS_COUNT = 10;
        int index = 0;
        List<Set<Drug>> sets = new ArrayList<Set<Drug>>(PARTITIONS_COUNT);

        for (int i = 0; i < PARTITIONS_COUNT; i++) {
            sets.add(new HashSet<Drug>());
        }

        index = 0;
        for (Drug drug : drugs) {
            sets.get(index++ % PARTITIONS_COUNT).add(drug);
        }

        index = 0;
        for (Set<Drug> set : sets) {
            drugRepository.saveAll(set);
            index++;
            log.info("Update progress: " + (index * 10) + "%");
        }


    }

    @Override
    public Long getDrugCount() {
        return drugRepository.count();
    }

    @Override
    public Set<Drug> getDrugsWhereNameOrSubstanceContains(String contained) {

        Set<Drug> finalSet = drugRepository.findAllByNameContainsIgnoreCaseOrCommonNameContainsIgnoreCase(contained, contained);

        return finalSet;
    }

    @Override
    public Drug getDrugWithId(Long id) {
        Optional<Drug> optionalDrug = drugRepository.findById(id);

        return optionalDrug.orElse(null);
    }

    @Override
    public Set<Drug> getDrugsWhereCommonNameIs(String commonName) {
        return drugRepository.findAllByCommonNameIs(commonName);
    }
}
