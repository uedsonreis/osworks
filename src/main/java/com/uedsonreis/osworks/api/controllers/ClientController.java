package com.uedsonreis.osworks.api.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uedsonreis.osworks.api.controllers.domain.model.Client;

@RestController
public class ClientController {

	@GetMapping("/clients")
	public List<Client> listar() {
		var client1 = Client.builder()
				.id(1L)
				.email("jose@qualquer.com")
				.name("José da Silva")
				.phone("71 98888-7777")
				.build();
		
		var client2 = Client.builder()
				.id(1L)
				.email("maria@qualquer.com")
				.name("Maria da Silva")
				.phone("11 94444-3333")
				.build();
		
		return Arrays.asList(client1, client2);
	}

}
