package br.com.tqi.test.development.service;

import static br.com.tqi.test.development.enumerates.SexoEnum.MASCULINO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.tqi.test.development.dto.ClientDto;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.repository.AddressRepository;
import br.com.tqi.test.development.repository.ClientRepository;

/**
 * ClientServiceTest
 */
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

    IClientService clientService;

    @MockBean
    AddressRepository addressRepository;

    @MockBean
    ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        this.clientService = new ClientService(clientRepository, addressRepository);
    }

    @Test
    public void findAll() {
        ClientDto clientDto1 = ClientDto.builder().id(1L).nome("John Lennon").sexo(MASCULINO.name()).cpf("00011122201")
                .build();
        ClientDto clientDto2 = ClientDto.builder().id(2L).nome("Paul McCartney").sexo(MASCULINO.name())
                .cpf("00011122202").build();

        ModelMapper modelMapper = new ModelMapper();

        List<ClientDto> clientsDto = Arrays.asList(clientDto1, clientDto2);
        List<Client> clients = clientsDto.stream().map(s -> modelMapper.map(s, Client.class))
                .collect(Collectors.toList());

        Pageable paging = PageRequest.of(0, 10, Sort.by(Direction.DESC, "nome"));
        Page<Client> page = new PageImpl<>(clients, paging, 2);
        Mockito.when(clientRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(page);

        Page<Client> result = clientService.findAll(0, 10, "nome");

        ModelMapper mapper = new ModelMapper();
        Page<ClientDto> resultDto = result.map(s -> mapper.map(s, ClientDto.class));
        System.out.println(resultDto.getNumber());
        System.out.println(resultDto.getNumberOfElements());
        System.out.println(resultDto.getSize());
        System.out.println(resultDto.getTotalElements());
        System.out.println(resultDto.getTotalPages());

        Assertions.assertThat(resultDto.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(resultDto.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(resultDto.getContent()).isEqualTo(clientsDto);
    }

    // @Test
    // @DisplayName("Throws ConstraintViolationException xxxxxxxxxxxxxxxxxxx")
    // public void throwCPFinvacreateANewClientWithInvalidCPF() {
    //     ClientDto dto = ClientDto.builder().nome("Bob Marley").cpf("000").sexo(SexoEnum.MASCULINO.name()).build();
    //     ModelMapper modelMapper = new ModelMapper();
    //     Client client = modelMapper.map(dto, Client.class);
    //     Throwable ex = Assertions.catchThrowable(() -> clientRepository.save(client));
    //     Assertions.assertThat(ex).isInstanceOf(ConstraintViolationException.class);
    // }

}