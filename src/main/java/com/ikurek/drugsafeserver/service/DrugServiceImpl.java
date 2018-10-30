package com.ikurek.drugsafeserver.service;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.repository.DrugRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

        drugRepository.saveAll(drugs);
    }

    @Override
    public Long getDrugCount() {
        return drugRepository.count();
    }

    @Override
    public Set<Drug> getDrugsWhereNameOrSubstanceContains(String contained) {

        Set<Drug> finalSet = drugRepository.findAllByNameContainsOrCommonNameContains(contained, contained);

        return finalSet;
    }
}
