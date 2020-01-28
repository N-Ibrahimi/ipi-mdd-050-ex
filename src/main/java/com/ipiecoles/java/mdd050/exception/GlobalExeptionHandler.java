package com.ipiecoles.java.mdd050.exception;


import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExeptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleEntityNotFoundException(EntityNotFoundException e) {
		return e.getMessage();
	}

	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handlermethodArgumentMismatchException(MethodArgumentTypeMismatchException e) {	
		return "La valeur '"+ e.getValue() + " est incorrect pour le paramètre "+ e.getName() +  " '.";
	}
	
	//PropertyReferenceException
	
	@ExceptionHandler(PropertyReferenceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handlerpropertyRefrenceExeption(PropertyReferenceException e) {
		return "La valeur '"+ e.getPropertyName() + "' est incorrect pour le paramètre.";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handlerIllegalArgumentExeption(IllegalArgumentException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handlerConstraintViolationException(ConstraintViolationException e) {
		return e.getMessage();
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ObjectError> errors=ex.getBindingResult().getAllErrors();
		String message="";
		
		for(ObjectError error:errors) {
			String nomChamp=((FieldError) error).getField(); // casting the error for having the name of column 
			message+= nomChamp + " " + error.getDefaultMessage();
		}
		
		return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
	}


}



