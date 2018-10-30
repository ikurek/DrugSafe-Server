package com.ikurek.drugsafeserver.repository;

import com.ikurek.drugsafeserver.model.Substance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubstanceRepository extends JpaRepository<Substance, Long> {

    Set<Substance> findAllByNameContains(String contained);
}
