package br.com.tqi.test.development.exceptions;

/**
 * CepFormatoInvalido
 */
public class CepInvalidFormatException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CepInvalidFormatException(String message) {
        super(message);
    }
}