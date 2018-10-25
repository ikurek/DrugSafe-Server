package com.ikurek.drugsafeserver.service;


import com.ikurek.drugsafeserver.model.AppUser;
import com.ikurek.drugsafeserver.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * Custom Implementation of UserDetailsService
 * Overrides username auth to use email based autenthication
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser applicationUser = appUserRepository.findByEmail(username);

        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());

        }
    }
}

