package com.egt.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
/**
 * This class has centralized exception handler mechanism for the project.
 *
 */
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	ResponseEntity<Object> buildMessage(ExceptionMessage errorMessage) {
		return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
	}
	
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception, WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(HttpStatus.BAD_REQUEST);
        errorMessage.setMessage(exception.getMessage());

        return buildMessage(errorMessage);

    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(HttpStatus.NOT_FOUND);
        errorMessage.setMessage(exception.getMessage());
        return buildMessage(errorMessage);

    }
    
    @ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGeneralException(Exception exception, WebRequest request){
    	ExceptionMessage errorMessage = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR);
        errorMessage.setMessage("General Error");
        return buildMessage(errorMessage);
	}
}