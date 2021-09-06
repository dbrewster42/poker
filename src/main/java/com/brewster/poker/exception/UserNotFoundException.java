package com.brewster.poker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="User could not be found")
public class UserNotFoundException extends RuntimeException {
}

