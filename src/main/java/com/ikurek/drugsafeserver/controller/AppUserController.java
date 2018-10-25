package com.ikurek.drugsafeserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.exceptions.UserAlreadyExistsException;
import com.ikurek.drugsafeserver.model.AppUser;
import com.ikurek.drugsafeserver.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handles registration, token validation
 * And other stuff related to users and accounts
 */

@RestController
@RequestMapping("/api/")
public class AppUserController {

    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ObjectMapper objectMapper;

    public AppUserController(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper objectMapper) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/v1/register")
    public String signUp(@RequestBody AppUser appUser) throws JsonProcessingException {

        // Encrypt user password
        String pass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(pass);

        // Check if user already exists
        if (appUserRepository.findByEmail(appUser.getEmail()) == null) {

            // Save user if not
            AppUser savedUser = appUserRepository.save(appUser);
            System.out.println("DrugSafe-Server: Saved " + appUser.getEmail());

            // Return user as JSON
            return objectMapper.writeValueAsString(savedUser);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @GetMapping("/v1/validate")
    public void validateToken() {
        System.out.println("DrugSafe-Server: Checking token validity ");
    }
}
