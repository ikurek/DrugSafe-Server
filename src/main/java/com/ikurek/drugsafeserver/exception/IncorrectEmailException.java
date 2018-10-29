package com.ikurek.drugsafeserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email is malformed or incorrect")
public class IncorrectEmailException extends RuntimeException {

}