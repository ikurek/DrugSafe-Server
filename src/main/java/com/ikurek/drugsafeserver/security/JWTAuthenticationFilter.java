package com.ikurek.drugsafeserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikurek.drugsafeserver.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import static com.ikurek.drugsafeserver.security.SecurityConstants.*;

/**
 * Spring Autenthication filter
 * Handles creating JWT tokens
 * And logging in users with token
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // Reroutes login from default spring configuration
        this.setFilterProcessesUrl(SIGN_IN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            appUser.getEmail(),
                            appUser.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        // Data necessary for token generation
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
        String user = ((User) auth.getPrincipal()).getUsername();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        // Building new token
        String token = Jwts.builder()
                .setSubject(user)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();

        // Adding token as header
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
