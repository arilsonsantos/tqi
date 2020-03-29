package br.com.tqi.test.development.service;

import br.com.tqi.test.development.entity.Address;

/**
 * IViaCepClient
 */
public interface IViaCepClient {

    Address getAddressByCep(String cep);

}