package com.ikurek.drugsafeserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not parse JSON")
public class MalformedJsonException extends RuntimeException {
}
