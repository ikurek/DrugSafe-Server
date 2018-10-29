package com.ikurek.drugsafeserver.repository;

import com.ikurek.drugsafeserver.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<Drug, Long> {

    Drug findByGovernmentId(Long governmentId);

}
