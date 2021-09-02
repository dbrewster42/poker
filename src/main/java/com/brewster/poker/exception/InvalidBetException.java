package com.brewster.poker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="The bet was invalid")
public class InvalidBetException extends RuntimeException {
     public InvalidBetException(String msg){ super(msg); }
}
