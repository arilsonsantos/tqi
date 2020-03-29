package br.com.tqi.test.development.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReturnedAddressDto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViaCepClientAddressDto {
    public String cep;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String localidade;
    public String uf;
    public String pais;
}