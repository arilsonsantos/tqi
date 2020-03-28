package br.com.tqi.test.development.validators.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.tqi.test.development.validators.CepValidator;

/**
 * Cep
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CepValidator.class)
public @interface CEP {
    String message() default "{validator.constraints.CEP.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}