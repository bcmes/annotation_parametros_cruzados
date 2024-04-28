package com.example.demo.annotation.crossparameter;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Constraint(validatedBy = PermissionControlValidator.class)
public @interface PermissionControl {
    String message() default "the requester is not allowed to access the data entered.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    TypePermission value() default TypePermission.NONE;
    Class<?> typeClass();
    String methodName();
    String parameterNameValidation();
}