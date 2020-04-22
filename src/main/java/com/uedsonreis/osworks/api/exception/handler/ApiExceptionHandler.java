package com.uedsonreis.osworks.api.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.uedsonreis.osworks.api.exception.response.Problem;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		var problem = Problem.builder()
				.status(status.value())
				.title("Um ou mais campos estão inválidos, faça o preenchimento correto e tente novamente.")
				.dateTime(LocalDateTime.now())
				.build();
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}

}