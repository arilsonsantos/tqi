package br.com.tqi.test.development.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tqi.test.development.dto.ClientDto;
import br.com.tqi.test.development.dto.CreateUpdateAddressDto;
import br.com.tqi.test.development.dto.CreateUpdateClientDto;
import br.com.tqi.test.development.entity.Address;
import br.com.tqi.test.development.entity.Client;
import br.com.tqi.test.development.service.AddressService;
import br.com.tqi.test.development.service.ClientService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test-tqi")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientController {

    private final ClientService clientService;
    private final AddressService addressService;
    private final ModelMapper mapper;

    @ApiOperation(value = "Find all clients")
    @GetMapping(value = "/client")
    public ResponseEntity<List<ClientDto>> getAllClient() {
        List<ClientDto> list = clientService.findAll();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @ApiOperation(value = "Find one client")
    @GetMapping(value = "/client/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") Long id) {
        Client client = clientService.getById(id);
        ClientDto clientDto = mapper.map(client, ClientDto.class);
        return ResponseEntity.ok(clientDto);
    }

    @ApiOperation(value = "Add a new client")
    @PostMapping(value = "/client")
    public ResponseEntity<ClientDto> saveNewClient(@Valid @RequestBody CreateUpdateClientDto newClientDto) {
        clientService.existsByCpf(newClientDto.getCpf());

        Client clientEntity = mapper.map(newClientDto, Client.class);
        clientEntity.getAddress().setClient(clientEntity);        
        clientEntity = clientService.save(clientEntity);

        ClientDto clientDto = mapper.map(clientEntity, ClientDto.class);
        
        return ResponseEntity.ok(clientDto);
    }

    @ApiOperation(value = "Change a client's address")
    @PutMapping(value = "/client/{idClient}/address/{idAddress}")
    public ResponseEntity<Void> changeClientAddress(
        @PathVariable("idClient") Long idClient, 
        @PathVariable("idAddress") Long idAddress, 
        @RequestBody CreateUpdateAddressDto addressDto) {

        Address address = mapper.map(addressDto, Address.class);

        addressService.updateByClientAndAddress(idClient, idAddress, address);
        return ResponseEntity.noContent().build();
    }

}
