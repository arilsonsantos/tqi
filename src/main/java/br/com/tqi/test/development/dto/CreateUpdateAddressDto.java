package br.com.tqi.test.development.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tqi.test.development.validators.annotations.CEP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CreateUpdateAddressDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CreateUpdateAddressDto {

    @NotEmpty
    private String endereco;

    private String numero;

    private String complemento;

    @NotEmpty
    private String bairro;

    @NotEmpty
    private String cidade;

    @NotNull
    private String estado;

    private String pais;

    @NotNull
    @CEP
    private String cep;

}