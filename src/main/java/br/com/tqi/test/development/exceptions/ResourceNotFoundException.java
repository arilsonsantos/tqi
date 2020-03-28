package br.com.tqi.test.development.exceptions;

/**
 * CepNotFoundException
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}