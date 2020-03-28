package br.com.tqi.test.development.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.tqi.test.development.dto.ClientDto;
import br.com.tqi.test.development.entity.Client;

/**
 * IClientService
 */
@Service
public interface IClientService {

    public Client save(Client clientEntity);

    public List<ClientDto> findAll();

    public void existsByCpf(String cpf);

    public Client getById(Long id);

}