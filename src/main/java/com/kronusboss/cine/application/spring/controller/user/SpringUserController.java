package com.kronusboss.cine.application.spring.controller.user;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kronusboss.cine.adapter.controller.core.dto.UserTokenDto;
import com.kronusboss.cine.adapter.controller.user.UserController;
import com.kronusboss.cine.adapter.controller.user.dto.UserRequestDto;
import com.kronusboss.cine.adapter.controller.user.dto.UserResponseDto;
import com.kronusboss.cine.adapter.util.CredentialUtil;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class SpringUserController {
	
	@Autowired
	private UserController controller;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable UUID id,
			@RequestHeader("Authorization") String token) {
		try {
			@SuppressWarnings("unused")
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			return ResponseEntity.ok(controller.getUserById(id));
		} catch (JsonMappingException e) {
			return ResponseEntity.status(412).body(Map.of("error", true, "status", 412, "message", "error to recover token"));
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(412).body(Map.of("error", true, "status", 412, "message", "error to recover token"));
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDto user) {
		UserResponseDto response;
		try {
			response = controller.createUser(user);
			return ResponseEntity.ok(response);
		} catch (DuplicatedUserException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", "email already taken"));
		}
		
	}
}
