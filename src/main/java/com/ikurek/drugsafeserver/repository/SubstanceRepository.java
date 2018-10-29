package com.ikurek.drugsafeserver.repository;

import com.ikurek.drugsafeserver.model.Substance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubstanceRepository extends JpaRepository<Substance, Long> {
}
