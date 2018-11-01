package com.ikurek.drugsafeserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data found for specified parameter")
public class DataNotFoundException extends RuntimeException {
}
