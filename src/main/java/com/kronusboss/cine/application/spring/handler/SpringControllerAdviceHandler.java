package com.kronusboss.cine.application.spring.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kronusboss.cine.application.spring.handler.dto.HandlerErrorDto;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SpringControllerAdviceHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public Map<String, Object> handleValidationExceptions(
			MethodArgumentNotValidException ex, HttpServletRequest request) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timestamp", new Date(System.currentTimeMillis()).toString());
		response.put("status", 400);
		response.put("error", "Bad Request");

		List<HandlerErrorDto> errorsDTO = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			HandlerErrorDto dto = HandlerErrorDto
					.builder()
					.key(((FieldError) error).getField())
					.error(error.getDefaultMessage())
					.build();
			errorsDTO.add(dto);
		});
		response.put("message", errorsDTO);
		response.put("path", request.getRequestURI());
		return response;
	}
}
