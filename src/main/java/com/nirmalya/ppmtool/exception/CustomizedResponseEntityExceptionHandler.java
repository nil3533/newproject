package com.nirmalya.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdAlreadyExistsException(ProjectIdException ex, WebRequest request){
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request){
		ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler
    public final ResponseEntity<Object> userNameExistsException(UserNameAlreadyExistsException ex, WebRequest request){
		UserNameExistsExceptionResponse userNameExistsExceptionResponse = new UserNameExistsExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(userNameExistsExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    
}
