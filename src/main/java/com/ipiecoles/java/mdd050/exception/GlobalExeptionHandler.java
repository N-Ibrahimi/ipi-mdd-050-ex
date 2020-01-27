package com.ipiecoles.java.mdd050.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
}



