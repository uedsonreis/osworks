package com.uedsonreis.osworks.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uedsonreis.osworks.api.dto.client.ClientInput;
import com.uedsonreis.osworks.api.dto.client.ClientOutput;
import com.uedsonreis.osworks.domain.model.Client;

@Component
public class ConvertClient {
	
	@Autowired
	private ModelMapper modelMapper;

	public Client input(ClientInput input) {
		return this.modelMapper.map(input, Client.class);
	}
	
	public List<Client> input(List<ClientInput> listInput) {
		return listInput.stream().map(ci -> this.input(ci)).collect(Collectors.toList());
	}
	
	public ClientOutput output(Client client) {
		return this.modelMapper.map(client, ClientOutput.class);
	}
	
	public List<ClientOutput> output(List<Client> list) {
		return list.stream().map(client -> this.output(client)).collect(Collectors.toList());
	}
	
}