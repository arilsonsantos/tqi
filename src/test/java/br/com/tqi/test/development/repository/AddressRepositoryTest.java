package br.com.tqi.test.development.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.tqi.test.development.dto.AddressDto;
import br.com.tqi.test.development.dto.ClientDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.enumerates.SexoEnum;
import lombok.RequiredArgsConstructor;

/**
 * AddressRepository
 */
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DataJpaTest
public class AddressRepositoryTest {

    private final AddressRepository addressRepository;
    private final TestEntityManager entityManager;
    private final ModelMapper modelMapper;

    @Test
    @DisplayName("Save the client's address")
    public void saveValidAddress() {
        ClientDto clientDto = ClientDto.builder().nome("John Lennon").cpf("00100100101").sexo(SexoEnum.MASCULINO.name())
                .build();

        Client client = modelMapper.map(clientDto, Client.class);

        AddressDto addressDto = AddressDto.builder().cep("00000111").endereco("Rua Abbey Road").cidade("Certãozinho")
                .estado("SP").pais("Brasil").build();

        Address address = modelMapper.map(addressDto, Address.class);

        client = entityManager.persist(client);
        address.setClient(client);

        address = entityManager.persist(address);

        Assertions.assertThat(address.getId()).isNotNull();
    }

    @Test
    @DisplayName("Get a valid address by id")
    public void getAValidaddressById() {
        saveValidAddress();
        Long id = 1L;

        Optional<Address> address = addressRepository.findById(id);

        Assertions.assertThat(address.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Return false if it doesn't find an address by id")
    public void isFalseWhenAddressDoesntExist() {
        saveValidAddress();
        Long id = 10L;

        Optional<Address> address = addressRepository.findById(id);

        Assertions.assertThat(address.isPresent()).isFalse();
    }

}