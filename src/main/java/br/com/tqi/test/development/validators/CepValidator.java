package br.com.tqi.test.development.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import br.com.tqi.test.development.validators.annotations.CEP;

/**
 * CepValidator
 */
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class CepValidator implements ConstraintValidator<CEP, String> {

    @Override
    public void initialize(CEP cep) {
    }

    @Override
    public boolean isValid(String cepField, ConstraintValidatorContext cxt) {
        return cepField != null && cepField.matches("\\d{5}-\\d{3}") || cepField.matches("\\d{8}");
    }

}