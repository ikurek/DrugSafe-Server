package com.ikurek.drugsafeserver.repository;

import com.ikurek.drugsafeserver.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {


    Set<Drug> findAllByNameContainsOrCommonNameContains(String containedInName, String containedInCommonName);

    Set<Drug> findAllByCommonNameContains(String contained);

    Set<Drug> findAllBySubstancesContaining(String contained);

}
