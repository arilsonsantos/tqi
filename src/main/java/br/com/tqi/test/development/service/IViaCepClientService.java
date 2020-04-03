package br.com.tqi.test.development.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.tqi.test.development.dto.ViaCepClientAddressDto;

@FeignClient(url = "${uri.cep}", name = "cep" )
public interface IViaCepClientService {

    @GetMapping("{cep}/${uri.cep.type.return}")
    ViaCepClientAddressDto getCep(@PathVariable(value = "cep") String cep);
}