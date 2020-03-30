package br.com.tqi.test.development.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.tqi.test.development.entity.Client;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    boolean existsByCpf(String cpf);

    @Query(value = "SELECT c FROM Client c LEFT JOIN c.address")
    Page<Client> findAll(Pageable pageable);
}
