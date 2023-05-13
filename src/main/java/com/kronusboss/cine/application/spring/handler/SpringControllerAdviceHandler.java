package com.kronusboss.cine.application.spring.handler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kronusboss.cine.adapter.util.exception.TokenInvalidException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class SpringControllerAdviceHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timestamp", new Date(System.currentTimeMillis()).toString());
		response.put("status", 400);
		response.put("error", "Bad Request");
		response.put("path", request.getRequestURI());

		Map<String, Object> errors = new LinkedHashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		response.put("errors", errors);

		return response;
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(TokenInvalidException.class)
	public Map<String, Object> handleValidationExceptions(TokenInvalidException ex, HttpServletRequest request) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timestamp", new Date(System.currentTimeMillis()).toString());
		response.put("status", HttpStatus.UNAUTHORIZED.value());
		response.put("error", "Unauthorized");
		response.put("message", ex.getMessage());
		response.put("path", request.getRequestURI());

		return response;
	}
}
