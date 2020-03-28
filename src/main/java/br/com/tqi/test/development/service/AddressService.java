package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CEP_FORMATO_INVALIDO;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CEP_NAO_ENCONTRADO;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.ENDERECO_DE_OUTRO_CLIENTE;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.ENDERECO_NAO_ENCONTRADO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.exceptions.CepInvalidFormatException;
import br.com.tqi.test.development.exceptions.ResourceAlreadyExistsException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import br.com.tqi.test.development.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * AddressService
 */
@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private static final String PAIS = "Brasil";
    private final RestTemplate restTemplate;
    private final IClientService clientService;
    private final AddressRepository addressRepository;

    @Value("${uri.cep}")
    private String URI;

    @Value("${uri.cep.type.return}")
    private String URI_RETURN_TYPE;

    public Address getByCep(String cep) {
        isValidFormat(cep);

        ReturnedAddressDto reAddress = restTemplate.getForEntity(URI + cep + URI_RETURN_TYPE, ReturnedAddressDto.class)
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

    public void updateByClientAndAddress(Long idClient, Long idAddress, Address addressEntity) {
        Client clientEntity = clientService.getById(idClient);
        Address actualAddress = getById(idAddress);

        if (!actualAddress.getClient().equals(clientEntity)) {
            throw new ResourceAlreadyExistsException(ENDERECO_DE_OUTRO_CLIENTE.getMessage());
        }

        addressEntity.setId(actualAddress.getId());
        addressEntity.setClient(clientEntity);
        clientEntity.setAddress(addressEntity);

        addressRepository.save(addressEntity);
    }

    public Address getById(Long id) {
        Optional<Address> client = addressRepository.findById(id);
        return client.orElseThrow(() -> new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO.getMessage()));
    }

    private void isValidFormat(String cep) {
        if (!cep.matches("\\d{5}-\\d{3}") && !cep.matches("\\d{8}")) {
            throw new CepInvalidFormatException(CEP_FORMATO_INVALIDO.getMessage());
        }
    }

    private void isCepFound(String cep, ReturnedAddressDto reAddress) {
        if (reAddress.cep == null) {
            throw new ResourceNotFoundException(CEP_NAO_ENCONTRADO.getMessage());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ReturnedAddressDto {
        public String cep;
        public String logradouro;
        public String complemento;
        public String bairro;
        public String localidade;
        public String uf;
        public String unidade;
        public String pais;
    }

}