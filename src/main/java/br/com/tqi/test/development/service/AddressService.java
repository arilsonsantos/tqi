package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.ENDERECO_DE_OUTRO_CLIENTE;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.ENDERECO_NAO_ENCONTRADO;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.exceptions.ResourceAlreadyExistsException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import br.com.tqi.test.development.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

/**
 * AddressService
 */
@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private final IViaCepClient viaCepClient;
    private final IClientService clientService;
    private final AddressRepository addressRepository;

    public Address getByCep(String cep) {
        return viaCepClient.getAddressByCep(cep);
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

}