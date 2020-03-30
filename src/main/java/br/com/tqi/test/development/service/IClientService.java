package br.com.tqi.test.development.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.tqi.test.development.entity.Client;

/**
 * IClientService
 */
@Service
public interface IClientService {

    public Client save(Client clientEntity);

    public void existsByCpf(String cpf);

    public Client getById(Long id);

    Page<Client> findAll(Integer pageNumber, Integer pageSize, String sortBy);

}