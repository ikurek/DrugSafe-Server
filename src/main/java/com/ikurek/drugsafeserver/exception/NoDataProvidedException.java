package com.ikurek.drugsafeserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No data provided in required field")
public class NoDataProvidedException extends RuntimeException {
}
