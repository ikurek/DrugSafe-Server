package com.ikurek.drugsafeserver.repository;

import com.ikurek.drugsafeserver.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    List<AppUser> findAll();
}
