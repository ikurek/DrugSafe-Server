package com.ikurek.drugsafeserver.repository;

import com.ikurek.drugsafeserver.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DrugRepository extends JpaRepository<Drug, Long> {

    Drug findByGovernmentId(Long governmentId);

    Set<Drug> findAllByName(String name);

    Set<Drug> findAllByNameContains(String contained);

}
