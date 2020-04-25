package com.uedsonreis.osworks.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uedsonreis.osworks.domain.exception.DomainException;
import com.uedsonreis.osworks.domain.model.Client;
import com.uedsonreis.osworks.domain.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	public Client create(Client client) throws DomainException {
		return this.save(client);
	}
	
	public Client update(Client client) throws DomainException {
		if (this.repository.existsById(client.getId())) {
			return this.save(client);
		} else {
			return null;
		}
	}
	
	private Client save(Client client) throws DomainException {
		Client savedClient = this.repository.findByEmail(client.getEmail());

		if (savedClient != null && !savedClient.equals(client)) {
			throw new DomainException("There is a registered client with this e-mail.");
		}
		
		return this.repository.save(client);			
	}
	
	public boolean delete(Long id) {
		if (this.repository.existsById(id)) {	
			this.repository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Client get(Long id) {
		Optional<Client> client = this.repository.findById(id);
		return client.orElse(null);
	}
	
	public List<Client> list() {
		return this.repository.findAll();
	}

}