package br.com.tqi.test.development.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tqi.test.development.dto.AddressDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.service.IAddressService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * AddressController
 */
@RestController
@RequestMapping("address")
@AllArgsConstructor
public class AddressController {

    private  IAddressService addressService;
    private  ModelMapper mapper;


    @ApiOperation(value = "Get address by cep")
    @GetMapping(value = "/{cep}")
    public ResponseEntity<AddressDto> getAddressByCEP(@PathVariable("cep")  String cep) {
        Address address = addressService.getByCep( cep);
        AddressDto addressDto = mapper.map(address, AddressDto.class);
        return ResponseEntity.ok(addressDto);
    }

}