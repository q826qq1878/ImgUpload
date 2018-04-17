package com.jjc.imgup.common.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public class Validators {
	private final static Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public static <T> Set<ConstraintViolation<T>> validate(T obj, Class<?>... groups) {
		return validator.validate(obj, groups);
	}

}
