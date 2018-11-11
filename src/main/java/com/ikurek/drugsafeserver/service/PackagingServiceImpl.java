package com.ikurek.drugsafeserver.service;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.model.Packaging;
import com.ikurek.drugsafeserver.repository.DrugRepository;
import com.ikurek.drugsafeserver.repository.PackagingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
public class PackagingServiceImpl implements PackagingService {

    private final DrugRepository drugRepository;
    private final PackagingRepository packagingRepository;

    public PackagingServiceImpl(DrugRepository drugRepository, PackagingRepository packagingRepository) {
        this.drugRepository = drugRepository;
        this.packagingRepository = packagingRepository;
    }

    @Override
    public Packaging findPackagingByEan(Long ean) {

        Packaging packaging = packagingRepository.findByEan(ean);


        return packaging;
    }

    @Override
    public Drug findDrugByPackaging(Packaging packaging) {

        Drug drug = drugRepository.findByPackagingContaining(packaging);

        return drug;
    }
}
