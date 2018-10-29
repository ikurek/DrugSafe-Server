package com.ikurek.drugsafeserver.repository;

import com.ikurek.drugsafeserver.model.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackagingRepository extends JpaRepository<Packaging, Long> {

    Packaging findBySize(Long size);
}
