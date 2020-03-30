package br.com.tqi.test.development.dto;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tqi.test.development.validators.annotations.CPF;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ClientDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(Include.NON_NULL)
public class ClientDto  {

    private Long id;

    @CPF(message = "cpf inválido")
    private String cpf;

    private String nome;

    private String sexo;

    @Valid
    private AddressDto address;

}