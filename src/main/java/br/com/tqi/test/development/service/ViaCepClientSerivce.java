package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CEP_FORMATO_INVALIDO;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CEP_NAO_ENCONTRADO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.tqi.test.development.dto.ViaCepClientAddressDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.exceptions.CepInvalidFormatException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * ViaCepClientSerivce
 */
@Service
@RequiredArgsConstructor
public class ViaCepClientSerivce implements IViaCepClient {
    private static final String PAIS = "Brasil";
    private final RestTemplate restTemplate;

    @Value("${uri.cep}")
    private String URI;

    @Value("${uri.cep.type.return}")
    private String URI_RETURN_TYPE;

    @Override
    public Address getAddressByCep(String cep) {
        isValidFormat(cep);
        ViaCepClientAddressDto reAddress = restTemplate.getForEntity(URI + cep + URI_RETURN_TYPE, ViaCepClientAddressDto.class)
                .getBody();
        isCepFound(cep, reAddress);

        Address addressEntity = new Address();
        addressEntity.setCep(reAddress.getCep());
        addressEntity.setEndereco(reAddress.getLogradouro());
        addressEntity.setBairro(reAddress.getBairro());
        addressEntity.setCidade(reAddress.getLocalidade());
        addressEntity.setEstado(reAddress.getUf());
        addressEntity.setPais(PAIS);

        return addressEntity;
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
