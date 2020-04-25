package com.uedsonreis.osworks.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uedsonreis.osworks.api.dto.client.ClientInput;
import com.uedsonreis.osworks.api.dto.client.ClientOutput;
import com.uedsonreis.osworks.domain.model.Client;
import com.uedsonreis.osworks.domain.service.ClientService;
import com.uedsonreis.osworks.util.ConvertClient;

@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private ConvertClient map;

	@Autowired
	private ClientService clientService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClientOutput create(@Valid @RequestBody ClientInput clientInput) {
		Client client = this.map.input(clientInput);
		return this.map.output(this.clientService.create(client));
	}
	
	@PutMapping("/{clientId}")
	public ResponseEntity<ClientOutput> update(@PathVariable Long clientId, @Valid @RequestBody ClientInput clientInput) {
		Client client = this.map.input(clientInput);
		
		client.setId(clientId);
		Client savedClient = this.clientService.update(client);
		
		if (savedClient == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(this.map.output(savedClient));
		}
	}
	
	@DeleteMapping("/{clientId}")
	public ResponseEntity<ClientOutput> delete(@PathVariable Long clientId) {
		if (this.clientService.delete(clientId)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping
	public List<ClientOutput> list() {
		return this.map.output(this.clientService.list());
	}

	@GetMapping("/{clientId}")
	public ResponseEntity<ClientOutput> get(@PathVariable Long id) {
		Client client = this.clientService.get(id);
		
		if (client == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(this.map.output(client));
		}
	}

}