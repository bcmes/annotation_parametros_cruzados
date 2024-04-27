package com.example.demo.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Constraint(validatedBy = ConsistentParametersValidator.class)
public @interface ConsistentParameters {
    String message() default "Invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Type value() default Type.NONE;
}