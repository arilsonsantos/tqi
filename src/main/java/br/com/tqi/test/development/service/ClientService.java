package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CLIENTE_NAO_ENCONTRADO;
import static br.com.tqi.test.development.enumerates.ErrorMessageEnum.CPF_JA_CADASTRADO;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.exceptions.ResourceAlreadyExistsException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import br.com.tqi.test.development.repository.AddressRepository;
import br.com.tqi.test.development.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

    @Override
    public Page<Client> findAll(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clientRepository.findAll(paging);
    }
   
}
