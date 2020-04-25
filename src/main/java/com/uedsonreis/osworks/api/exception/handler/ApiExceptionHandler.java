package com.uedsonreis.osworks.api.exception.handler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.uedsonreis.osworks.api.exception.response.Problem;
import com.uedsonreis.osworks.api.exception.response.ProblemField;
import com.uedsonreis.osworks.domain.exception.DomainException;
import com.uedsonreis.osworks.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFount(DomainException exception, WebRequest request) {
		return this.handle(exception, request, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomain(DomainException exception, WebRequest request) {		
		return this.handle(exception, request, HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<Object> handle(DomainException exception, WebRequest request, HttpStatus status) {
		var problem = Problem.builder()
				.title(exception.getMessage())
				.status(status.value())
				.dateTime(OffsetDateTime.now())
			.build();
		
		return super.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

		var fields = new ArrayList<ProblemField>();
		
		for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			
			var field = ProblemField.builder()
					.message(this.messageSource.getMessage(error, LocaleContextHolder.getLocale()))
					.name(fieldError.getField())
				.build();
			
			fields.add(field);
		}
		
		var problem = Problem.builder()
				.status(status.value())
				.title("Um ou mais campos estão inválidos, faça o preenchimento correto e tente novamente.")
				.dateTime(OffsetDateTime.now())
				.fields(fields)
			.build();
		
		return super.handleExceptionInternal(exception, problem, headers, status, request);
	}

}