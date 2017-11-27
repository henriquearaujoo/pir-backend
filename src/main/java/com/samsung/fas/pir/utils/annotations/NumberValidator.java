package com.samsung.fas.pir.utils.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<Number, Object> {
	private		Number		number;

	@Override
	public void initialize(Number number) {
		this.number = number;
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		String	regex	= number.decimal() ? "-?[0-9][0-9\\.\\,]*" : "-?[0-9]+";
		String	data	= String.valueOf(object);
		return data.matches(regex);
	}
}