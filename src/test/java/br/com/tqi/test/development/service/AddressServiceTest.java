package br.com.tqi.test.development.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.tqi.test.development.dto.AddressDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import br.com.tqi.test.development.repository.AddressRepository;

/**
 * AddressServiceTest
 */
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
public class AddressServiceTest {

    IAddressService addressService;
    IClientService clientService;
    IViaCepClient viaCepService;

    @MockBean
    AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        this.addressService = new AddressService(viaCepService, clientService, addressRepository);
    }

    @Test
    @DisplayName("Get a valid address by Id")
    public void getAddressById() {
        Long id = 1L;
        ModelMapper modelMapper = new ModelMapper();
        AddressDto addressDto = AddressDto.builder().id(1L).cep("00045678").endereco("Rua Abbey Road")
                .cidade("Certãozinho").estado("SP").pais("Brasil").build();

        Address address = modelMapper.map(addressDto, Address.class);

        Mockito.when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));

        Address addressFound = addressService.getById(id);

        Assertions.assertThat(addressFound.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Throws ResourceNotFoundException when id doens't exist")
    public void throwResourceNotFound() {
        Long id = 1L;

        Mockito.when(addressRepository.findById(any(Long.class)))
                .thenThrow(new ResourceNotFoundException("Endereço não encontrado"));

        Throwable ex = Assertions.catchThrowable(() -> addressService.getById(id));

        Assertions.assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(addressRepository, Mockito.times(1)).findById(id);
    }

}