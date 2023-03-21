package br.com.course.webfluxcourse.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Constraint(validatedBy = { TrimStringValidator.class })
@Retention(RUNTIME)
public @interface TrimString {

    String message() default "field cannot have blank spaces at the beginning or at the end";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
