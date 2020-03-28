package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CLIENTE_NAO_ENCONTRADO;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CPF_JA_CADASTRADO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tqi.test.development.dto.ClientDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.exceptions.ResourceAlreadyExistsException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import br.com.tqi.test.development.repository.AddressRepository;
import br.com.tqi.test.development.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    public Client save(Client clientEntity) {
        Address addressEntity = clientEntity.getAddress();

        Client newClient = clientRepository.save(clientEntity);

        if (addressEntity.getId() == null) {
            addressEntity.setClient(newClient);
            addressRepository.save(addressEntity);
        }

        newClient.setAddress(addressEntity);

        return newClient;
    }

    public List<ClientDto> findAll() {
        ModelMapper mapper = new ModelMapper();

        List<Client> listClient = clientRepository.findAll();
        List<ClientDto> clients = listClient.stream().map(s -> mapper.map(s, ClientDto.class))
                .collect(Collectors.toList());

        return clients;
    }

    public void existsByCpf(String cpf) {
        boolean exists = clientRepository.existsByCpf(cpf);
        if (exists) {
            throw new ResourceAlreadyExistsException(CPF_JA_CADASTRADO.getMessage());
        }
    }

    public Client getById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO.getMessage()));
    }

}
