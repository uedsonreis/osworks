package com.uedsonreis.osworks.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uedsonreis.osworks.api.dto.comment.CommentInput;
import com.uedsonreis.osworks.api.dto.comment.CommentOutput;
import com.uedsonreis.osworks.api.dto.service.order.ServiceOrderInput;
import com.uedsonreis.osworks.api.dto.service.order.ServiceOrderOutput;
import com.uedsonreis.osworks.domain.model.Comment;
import com.uedsonreis.osworks.domain.model.ServiceOrder;
import com.uedsonreis.osworks.domain.service.ServiceOrderService;
import com.uedsonreis.osworks.util.ConvertComment;
import com.uedsonreis.osworks.util.ConvertServiceOrder;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController {

	@Autowired
	private ConvertServiceOrder soMap;

	@Autowired
	private ConvertComment commentMap;
	
	@Autowired
	private ServiceOrderService serviceOrderService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceOrderOutput create(@Valid @RequestBody ServiceOrderInput serviceOrderInput) {
		ServiceOrder serviceOrder = this.soMap.input(serviceOrderInput);
		return this.soMap.output(this.serviceOrderService.create(serviceOrder));
	}
	
	@PutMapping("/{id}/finish")
	public ServiceOrderOutput finish(@PathVariable Long id) {
		return this.soMap.output(this.serviceOrderService.close(id));
	}
	
	@PutMapping("/{id}/cancel")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable Long id) {
		this.serviceOrderService.cancel(id);
	}
	
	@GetMapping
	public List<ServiceOrderOutput> list() {
		return this.soMap.output(this.serviceOrderService.list());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ServiceOrderOutput> get(@PathVariable Long id) {
		ServiceOrder serviceOrder = this.serviceOrderService.get(id);
		if (serviceOrder == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(this.soMap.output(serviceOrder));
		}
	}
	
	@GetMapping("/{id}/comments")
	public List<CommentOutput> commentList(@PathVariable Long id) {
		return this.commentMap.output(this.serviceOrderService.commentList(id));
	}
	
	@PostMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public CommentOutput createComment(@PathVariable Long id, @RequestBody CommentInput commentInput) {
		Comment comment = this.serviceOrderService.addComment(id, commentInput.getDescription());
		return this.commentMap.output(comment);
	}

}