package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.ENDERECO_DE_OUTRO_CLIENTE;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.ENDERECO_NAO_ENCONTRADO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.exceptions.ResourceAlreadyExistsException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import br.com.tqi.test.development.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AddressService
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddressService implements IAddressService {
    private final IClientService clientService;
    private final ICepService cepService;
    private final AddressRepository addressRepository;
   
    public Address getByCep(String cep) {
        return cepService.getAddressByCep(cep);
    }

    public void updateByClientAndAddress(Long idClient, Long idAddress, Address addressEntity) {
        Client client = clientService.getById(idClient);
        Address actualAddress = getById(idAddress);

        if (!actualAddress.getClient().equals(client)) {
            log.error("O endereço {} pertence ao cliente {}", idAddress, actualAddress.getClient());

            throw new ResourceAlreadyExistsException(ENDERECO_DE_OUTRO_CLIENTE.getMessage());
        }

        addressEntity.setId(actualAddress.getId());
        addressEntity.setClient(client);
        client.setAddress(addressEntity);

        addressRepository.save(addressEntity);

        log.info("Endereço autializado para o cliente {}", client.getId());
    }

    public Address getById(Long id) {
        Optional<Address> client = addressRepository.findById(id);
        return client.orElseThrow(() -> new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO.getMessage()));
    }

}