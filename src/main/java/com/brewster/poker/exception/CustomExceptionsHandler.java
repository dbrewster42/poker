package com.brewster.poker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionsHandler extends ResponseEntityExceptionHandler {

     @ExceptionHandler(value = GameNotFoundException.class)
     @ResponseStatus(HttpStatus.NOT_FOUND)
     public ErrorResponse resolveGameNotFoundException(GameNotFoundException e, WebRequest req) {
          return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                  HttpStatus.NOT_FOUND.getReasonPhrase(),
                  e.getMessage(),
                  req.getDescription(true));
     }

//     @ExceptionHandler(value = UserNotFoundException.class)
//     @ResponseStatus(HttpStatus.NOT_FOUND)
//     public ErrorResponse resolveUserNotFoundException(UserNotFoundException e, WebRequest req) {
//          return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
//                  HttpStatus.NOT_FOUND.getReasonPhrase(),
//                  e.getMessage(),
//                  req.getDescription(true));
//     }

     @ExceptionHandler(value = InvalidBetException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ErrorResponse resolveInvalidBetException(InvalidBetException e, WebRequest req) {
          return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                  HttpStatus.BAD_REQUEST.getReasonPhrase(),
                  e.getMessage(),
                  req.getDescription(true));
     }
}
