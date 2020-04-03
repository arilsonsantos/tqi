package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CEP_FORMATO_INVALIDO;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CEP_NAO_ENCONTRADO;

import org.springframework.stereotype.Service;

import br.com.tqi.test.development.dto.ViaCepClientAddressDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.exceptions.CepInvalidFormatException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

/**
 * ViaCepClientSerivce
 */
@Service
@AllArgsConstructor
public class CepService implements ICepService {
    private  IViaCepClientService viaCepClient;

    @Override
    public Address getAddressByCep(String cep) {
        isValidFormat(cep);
        ViaCepClientAddressDto reAddress = viaCepClient.getCep(cep);
        isCepFound(cep, reAddress);

        Address address = new Address();
        address.setCep(reAddress.getCep());
        address.setEndereco(reAddress.getLogradouro());
        address.setBairro(reAddress.getBairro());
        address.setCidade(reAddress.getLocalidade());
        address.setEstado(reAddress.getUf());
        address.setPais("Brasil");

        return address;
    }

    private void isValidFormat(String cep) {
        if (!cep.matches("\\d{5}-\\d{3}") && !cep.matches("\\d{8}")) {
            throw new CepInvalidFormatException(CEP_FORMATO_INVALIDO.getMessage());
        }
    }

    private void isCepFound(String cep, ViaCepClientAddressDto reAddress) {
        if (reAddress.cep == null) {
            throw new ResourceNotFoundException(CEP_NAO_ENCONTRADO.getMessage());
        }
    }

}
