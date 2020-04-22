package com.uedsonreis.osworks.api.controllers;

import java.util.List;
import java.util.Optional;

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

import com.uedsonreis.osworks.domain.model.Client;
import com.uedsonreis.osworks.domain.repository.ClientRepository;

@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client save(@Valid @RequestBody Client client) {
		return this.clientRepository.save(client);
	}
	
	@PutMapping("/{clientId}")
	public ResponseEntity<Client> update(@PathVariable Long clientId, @Valid @RequestBody Client client) {
		if (this.clientRepository.existsById(clientId)) {
			
			client.setId(clientId);
			Client savedClient = this.clientRepository.save(client);
			return ResponseEntity.ok(savedClient);
			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{clientId}")
	public ResponseEntity<Client> remove(@PathVariable Long clientId) {
		if (this.clientRepository.existsById(clientId)) {
			
			this.clientRepository.deleteById(clientId);
			return ResponseEntity.noContent().build();
			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping
	public List<Client> listar() {
		return this.clientRepository.findAll();
	}

	@GetMapping("/{clientId}")
	public ResponseEntity<Client> find(@PathVariable Long clientId) {
		Optional<Client> client = this.clientRepository.findById(clientId);
		
		if (client.isPresent()) {
			return ResponseEntity.ok(client.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
