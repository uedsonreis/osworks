package com.uedsonreis.osworks.api.dto.service.order;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.uedsonreis.osworks.domain.model.ServiceOrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ServiceOrderOutput {

	private Long id;
	
	private Long clientId;
	private String clientName;
	
	private String description;
	
	private Double price;
	
	private OffsetDateTime openingDate;
	private OffsetDateTime closingDate;
	
	private ServiceOrderStatus serviceOrderStatus;

}