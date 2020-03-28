package br.com.tqi.test.development.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tqi.test.development.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
