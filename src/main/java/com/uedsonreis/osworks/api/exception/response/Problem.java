package com.uedsonreis.osworks.api.exception.response;

import java.time.LocalDateTime;

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
public class Problem {

	private Integer status;
	private LocalDateTime dateTime;
	private String title;
	
}