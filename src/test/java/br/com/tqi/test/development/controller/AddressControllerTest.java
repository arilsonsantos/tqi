package br.com.tqi.test.development.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.tqi.test.development.dto.AddressDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.exceptions.CepInvalidFormatException;
import br.com.tqi.test.development.exceptions.ResourceNotFoundException;
import br.com.tqi.test.development.service.IAddressService;
import lombok.RequiredArgsConstructor;

/**
 * AddressControllerTest
 */
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AddressController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddressControllerTest {
    static final String ADDRESS_API = "/address";

    final MockMvc mvc;
    final ModelMapper mapper;

    @MockBean
    IAddressService service;

    @Test
    @DisplayName("Find a CEP")
    public void findAValidCEP() throws Exception {
        final String cep = "05372020";
        Address address = Address.builder().id(1L).endereco("Rua X").complemento("Comp X").bairro("Centro")
                .cidade("São Paulo").estado("SP").cep(cep).pais("Brasil").build();

        BDDMockito.given(service.getByCep(cep)).willReturn(address);

        AddressDto addressDto = mapper.map(address, AddressDto.class);

        String json = new ObjectMapper().writeValueAsString(addressDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ADDRESS_API + "/" + cep)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("cep").value(addressDto.getCep()));

    }

    @Test
    @DisplayName("Throws ResourceNotFoundException - CEP that not exist")
    public void findACEPNotExist() throws Exception {
        final String cep = "11111111";

        BDDMockito.given(service.getByCep(cep))
                .willThrow(new ResourceNotFoundException("O CEP náo foi encontrado: " + cep));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ADDRESS_API.concat("/" + cep));

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("O CEP náo foi encontrado: " + cep));
    }

    @Test
    @DisplayName("Throws CepInvalidFormatException - CEP with invalid format")
    public void findAInvalidFormatedCEP() throws Exception {
        final String cep = "1111111";

        BDDMockito.given(service.getByCep(cep)).willThrow(
                new CepInvalidFormatException("O CEP deve conter 8 números"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(ADDRESS_API.concat("/" + cep));

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
                .content().string("O CEP deve conter 8 números"));
    }

}