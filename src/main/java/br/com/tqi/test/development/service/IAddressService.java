package br.com.tqi.test.development.service;

import org.springframework.stereotype.Service;

import br.com.tqi.test.development.entity.Address;

/**
 * IAddress
 */
@Service
public interface IAddressService {

    Address getByCep(String cep);

    void updateByClientAndAddress(Long idClient, Long idAddress, Address addressEntity);

    Address getById(Long id);
    
}