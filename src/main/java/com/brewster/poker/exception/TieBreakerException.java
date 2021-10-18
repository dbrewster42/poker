package com.brewster.poker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="There was a error with calculating the tie breaker")
public class TieBreakerException extends RuntimeException {
     public TieBreakerException(String msg){ super(msg); }
}
