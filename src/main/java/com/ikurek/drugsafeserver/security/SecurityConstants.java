package com.ikurek.drugsafeserver.security;

public class SecurityConstants {
    public static final String SECRET = "ImLazyPleaseDontStealThisKeyItIsDevelopmentOnlyAnyway";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Token ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/register";
    public static final String SIGN_IN_URL = "/api/login";
}