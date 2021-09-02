package com.brewster.poker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionsHandler extends ResponseEntityExceptionHandler {

     @ExceptionHandler(value = GameNotFoundException.class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
     public ErrorResponse resolveInvalidMove(GameNotFoundException e, WebRequest req) {
          return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                  HttpStatus.NOT_FOUND.getReasonPhrase(),
                  e.getMessage(),
                  req.getDescription(true));
     }
}
