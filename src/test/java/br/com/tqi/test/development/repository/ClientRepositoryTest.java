package br.com.tqi.test.development.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.tqi.test.development.dto.ClientDto;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.enumerates.SexoEnum;
import lombok.RequiredArgsConstructor;

/**
 * ClientRepositoryTest
 */
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DataJpaTest
public class ClientRepositoryTest {

    private final TestEntityManager entityManager;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        ClientDto c1 = ClientDto.builder().nome("John Lennon").cpf("00000000001").sexo(SexoEnum.MASCULINO.name())
                .build();
        ClientDto c2 = ClientDto.builder().nome("Paul McCartney").cpf("00000000002").sexo(SexoEnum.MASCULINO.name())
                .build();
        ClientDto c3 = ClientDto.builder().nome("George Harrison").cpf("00000000003").sexo(SexoEnum.MASCULINO.name())
                .build();
        ClientDto c4 = ClientDto.builder().nome("Ringo Star").cpf("00000000004").sexo(SexoEnum.MASCULINO.name())
                .build();

        List<ClientDto> dtos = Arrays.asList(c1, c2, c3, c4);
        List<Client> clients = dtos.stream().map(c -> modelMapper.map(c, Client.class)).collect(Collectors.toList());

        clients.forEach(c -> entityManager.persist(c));

    }


    @Test
    @DisplayName("Find all with Pageable")
    public void test() {
        Pageable paging = PageRequest.of(1, 2, Sort.by(Direction.ASC, "nome"));

        Page<Client> result = clientRepository.findAll(paging);
        Assertions.assertThat(result.getNumber()).isEqualTo(1);
        Assertions.assertThat(result.getSize()).isEqualTo(2);
        Assertions.assertThat(result.getTotalElements()).isEqualTo(4);
        Assertions.assertThat(result.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(result.getSort().getOrderFor("nome").getDirection()).isEqualTo(Direction.ASC);
    }

}