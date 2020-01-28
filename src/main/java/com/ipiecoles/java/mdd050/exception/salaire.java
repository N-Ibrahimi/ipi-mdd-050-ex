package com.ipiecoles.java.mdd050.exception;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ipiecoles.java.mdd050.model.SalaireValidator;

@Documented
@Constraint(validatedBy = SalaireValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface salaire {
		
	String message() default "salaire incorrect"; // c'est une method en réalité ( varaibale et methode in the same time) 
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default {};
}
