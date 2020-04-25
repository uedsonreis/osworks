package com.uedsonreis.osworks.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uedsonreis.osworks.api.dto.service.order.ServiceOrderInput;
import com.uedsonreis.osworks.api.dto.service.order.ServiceOrderOutput;
import com.uedsonreis.osworks.domain.model.ServiceOrder;

@Component
public class ConvertServiceOrder {
	
	@Autowired
	private ModelMapper modelMapper;

	public ServiceOrder input(ServiceOrderInput input) {
		return this.modelMapper.map(input, ServiceOrder.class);
	}
	
	public List<ServiceOrder> input(List<ServiceOrderInput> listInput) {
		return listInput.stream().map(so -> this.input(so)).collect(Collectors.toList());
	}
	
	public ServiceOrderOutput output(ServiceOrder so) {
		return this.modelMapper.map(so, ServiceOrderOutput.class);
	}
	
	public List<ServiceOrderOutput> output(List<ServiceOrder> list) {
		return list.stream().map(client -> this.output(client)).collect(Collectors.toList());
	}
	
}