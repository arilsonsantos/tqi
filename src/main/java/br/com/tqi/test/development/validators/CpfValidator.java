package br.com.tqi.test.development.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import br.com.tqi.test.development.validators.annotations.CPF;

/**
 * CepValidator
 */
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class CpfValidator implements ConstraintValidator<CPF, String> {

    @Override
    public void initialize(CPF cpf) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext cxt) {
        return cpf != null && cpf.matches("\\d{11}");
    }

}