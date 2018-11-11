package com.ikurek.drugsafeserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.exception.UserAlreadyExistsException;
import com.ikurek.drugsafeserver.model.AppUser;
import com.ikurek.drugsafeserver.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handles registration, token validation
 * And other stuff related to users and accounts
 */

@RestController
@RequestMapping("/api/")
@Slf4j
public class AppUserController {

    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ObjectMapper objectMapper;

    public AppUserController(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper objectMapper) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/register")
    public String signUp(@RequestBody AppUser appUser) throws JsonProcessingException {

        // Encrypt user password
        String pass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(pass);

        // Check if user already exists
        if (appUserRepository.findByEmail(appUser.getEmail()) == null) {

            // Save user if not
            AppUser savedUser = appUserRepository.save(appUser);
            log.info("User " + appUser.getEmail() + " registered");

            // Return user as JSON
            return objectMapper.writeValueAsString(savedUser);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @GetMapping("/validate")
    public void validateToken(@RequestBody AppUser appUser) {

        log.info("User " + appUser.getEmail() + " requested token validation");

    }
}
