package com.samsung.fas.pir.utils.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = NumberValidator.class)
public @interface Number {
	String 						message()	default "{invalid.number.format}";
	Class<?>[] 					groups()	default {};
	Class<? extends Payload>[] 	payload()	default {};
	boolean 					decimal()	default false;
}
