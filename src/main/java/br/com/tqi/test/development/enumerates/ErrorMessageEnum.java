package br.com.tqi.test.development.enumerates;

import lombok.Getter;

/**
 * ErrorMessage
 */
public enum ErrorMessageEnum {

    CEP_FORMATO_INVALIDO("O CEP deve conter 8 números."),
    ENDERECO_DE_OUTRO_CLIENTE("Endereço pertence a outro cliente."),
    ENDERECO_NAO_ENCONTRADO("Endereço não encontrado"),
    CPF_JA_CADASTRADO("Já existe cliente cadastrado com o CPF informado."),
    CEP_NAO_ENCONTRADO("CEP náo foi encontrado."), 
    CLIENTE_NAO_ENCONTRADO("Cliente não encontrado.");

    @Getter
    private final String message;

    private ErrorMessageEnum(String message) {
        this.message = message;
    }

}