package br.com.tqi.test.development.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.tqi.test.development.dto.ViaCepClientAddressDto;

/**
 * ViaCepClientServiceTest
 */
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
public class ViaCepClientServiceTest {

    @Test
    @DisplayName("Get a CEP -> [viacep.com.br/ws]")
    public void findAValidCEPonViaCep() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ViaCepClientAddressDto addressDto = testRestTemplate
                .getForEntity("http://viacep.com.br/ws/01001000/json", ViaCepClientAddressDto.class).getBody();

        Assertions.assertThat(addressDto).isNotNull();
    }

}