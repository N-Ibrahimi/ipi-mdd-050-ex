package com.ipiecoles.java.mdd050.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ipiecoles.java.mdd050.exception.salaire;

public class SalaireValidator implements ConstraintValidator<salaire, Double> {// @salaire et le type de notre donnée

	@Override
	public void initialize(salaire constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(Double salaire, ConstraintValidatorContext context) {
		return salaire >= Entreprise.SALAIRE_BASE && 
				salaire <= Entreprise.SALAIRE_BASE * 6;
	}
	
	
}
