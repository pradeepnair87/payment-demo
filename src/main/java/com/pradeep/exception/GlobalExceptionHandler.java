package com.pradeep.exception;

import com.pradeep.responses.ResponseDetails;
import com.pradeep.responses.Status;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@RequestMapping(produces = "application/json")
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class) public ResponseEntity<ResponseDetails> handleUserAlreadyExistsException(final UserAlreadyExistsException exception) {
        ResponseDetails responseDetails = new ResponseDetails(exception.getMessage(),Status.FAILED,ExceptionCodes.UserAlreadyExistsExceptionCode);
        log.error("UserAlreadyExistsException : {}", exception.getMessage());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class) public ResponseEntity<ResponseDetails> handleUserNotFoundException(final UserNotFoundException exception) {
        ResponseDetails responseDetails = new ResponseDetails(exception.getMessage(),Status.FAILED,ExceptionCodes.UserNotFoundExceptionCode);
        log.error("UserNotFoundException : {}", exception.getMessage());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @ExceptionHandler(InsufficentBalanceException.class) public ResponseEntity<ResponseDetails> handleInsufficientFundException(final InsufficentBalanceException exception) {
        ResponseDetails responseDetails = new ResponseDetails(exception.getMessage(),Status.FAILED,ExceptionCodes.InsufficientFundExceptionCode);
        log.error("InsufficentBalanceException : {}", exception.getMessage());
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }

    @ExceptionHandler(ValidationException.class) public ResponseEntity<ResponseDetails> handleValidationException(final ValidationException exception) {
        ResponseDetails responseDetails = new ResponseDetails(exception.getMessage(),Status.FAILED,ExceptionCodes.UserNotFoundExceptionCode);
        log.error("GeneralException : {}", exception);
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }
    @ExceptionHandler(Exception.class) public ResponseEntity<ResponseDetails> handleException(final Exception exception) {
        ResponseDetails responseDetails = new ResponseDetails(exception.getMessage(),Status.ERROR,ExceptionCodes.GeneralException);
        log.error("GeneralException : {}", exception);
        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }
}

