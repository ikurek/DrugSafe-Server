package com.ikurek.drugsafeserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request body cannot be empty")
public class EmptyRequestBodyException extends RuntimeException {
}
