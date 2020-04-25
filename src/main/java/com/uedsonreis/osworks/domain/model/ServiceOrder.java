package com.uedsonreis.osworks.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.uedsonreis.osworks.domain.exception.DomainException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@Entity
public class ServiceOrder {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double price;
	private String description;
	private OffsetDateTime openingDate;
	private OffsetDateTime closingDate;
	
	@Enumerated(EnumType.STRING)
	private ServiceOrderStatus status;

	@ManyToOne
	private Client client;

	@Builder.Default
	@OneToMany(mappedBy = "serviceOrder")
	private List<Comment> comments = new ArrayList<>();
	
	private boolean isOpen() {
		return ServiceOrderStatus.OPENED.equals(this.status);
	}
	
	public void close() {
		if (!this.isOpen()) {
			throw new DomainException("Service Order cannot be closed, it is not opened!");
		}
		this.setStatus(ServiceOrderStatus.CLOSED);
		this.setClosingDate(OffsetDateTime.now());
	}
	
	public void cancel() {
		if (!this.isOpen()) {
			throw new DomainException("Service Order cannot be canceled, it is not opened!");
		}
		this.setStatus(ServiceOrderStatus.CANCELED);
	}
	
}