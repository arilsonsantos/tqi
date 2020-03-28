package br.com.tqi.test.development.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tqi.test.development.validators.annotations.CPF;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CreateUpdateClientDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CreateUpdateClientDto {

    @CPF
    private String cpf;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String sexo;

    @Valid
    private CreateUpdateAddressDto address;

}