package com.uedsonreis.osworks.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uedsonreis.osworks.domain.exception.DomainException;
import com.uedsonreis.osworks.domain.exception.EntityNotFoundException;
import com.uedsonreis.osworks.domain.model.Client;
import com.uedsonreis.osworks.domain.model.Comment;
import com.uedsonreis.osworks.domain.model.ServiceOrder;
import com.uedsonreis.osworks.domain.model.ServiceOrderStatus;
import com.uedsonreis.osworks.domain.repository.ClientRepository;
import com.uedsonreis.osworks.domain.repository.CommentRepository;
import com.uedsonreis.osworks.domain.repository.ServiceOrderRepository;

@Service
public class ServiceOrderService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	public ServiceOrder create(ServiceOrder serviceOrder) {
		Client client = clientRepository.findById(serviceOrder.getClient().getId())
				.orElseThrow(() -> new DomainException("Client not found"));
		
		serviceOrder.setClient(client);
		
		serviceOrder.setStatus(ServiceOrderStatus.OPENED);
		serviceOrder.setOpeningDate(OffsetDateTime.now());
		
		return this.serviceOrderRepository.save(serviceOrder);
	}
	
	public ServiceOrder get(Long id) {
		Optional<ServiceOrder> serviceOrder = this.serviceOrderRepository.findById(id);
		return serviceOrder.orElse(null);
	}
	
	public List<ServiceOrder> list() {
		return this.serviceOrderRepository.findAll();
	}
	
	public ServiceOrder close(Long serviceOrderId) {
		ServiceOrder serviceOrder = this.getServiceOrder(serviceOrderId);
		serviceOrder.close();
		return this.serviceOrderRepository.save(serviceOrder);
	}
	
	public void cancel(Long serviceOrderId) {
		ServiceOrder serviceOrder = this.getServiceOrder(serviceOrderId);
		serviceOrder.cancel();
		this.serviceOrderRepository.save(serviceOrder);
	}
	
	public Comment addComment(Long serviceOrderId, String description) {
		ServiceOrder serviceOrder = this.getServiceOrder(serviceOrderId);
		
		Comment comment = Comment.builder()
				.sendDate(OffsetDateTime.now())
				.serviceOrder(serviceOrder)
				.description(description)
				.build();
		
		return this.commentRepository.save(comment);
	}
	
	public List<Comment> commentList(Long serviceOrderId) {
		this.getServiceOrder(serviceOrderId);
		return this.commentRepository.findByServiceOrderId(serviceOrderId);
	}
	
	private ServiceOrder getServiceOrder(Long id) throws EntityNotFoundException {
		return this.serviceOrderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Service Order does not exist!"));
	}

}