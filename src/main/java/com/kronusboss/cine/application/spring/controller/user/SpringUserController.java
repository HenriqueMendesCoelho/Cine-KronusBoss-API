package com.kronusboss.cine.application.spring.controller.user;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.user.controller.UserController;
import com.kronusboss.cine.adapter.user.controller.dto.InviteResponseDto;
import com.kronusboss.cine.adapter.user.controller.dto.UserRequestDto;
import com.kronusboss.cine.adapter.user.controller.dto.UserResponseDto;
import com.kronusboss.cine.adapter.util.CredentialUtil;
import com.kronusboss.cine.user.usecase.exception.DuplicatedUserException;
import com.kronusboss.cine.user.usecase.exception.InviteNotValidException;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class SpringUserController {

	@Autowired
	private UserController controller;
	
	@GetMapping
	public ResponseEntity<?> getUser(@RequestParam String email,
			@RequestHeader("Authorization") String token) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			UserResponseDto response = controller.getUserByEmail(user, email);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		} catch ( JsonProcessingException e) {
			return ResponseEntity.status(412)
					.body(Map.of("error", true, "status", 412, "message", "error to recover token"));
		} catch (UserNotAuthorizedException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		try {
			List<UserResponseDto> response = controller.getAllUsers();
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("/invite")
	public ResponseEntity<List<InviteResponseDto>> listAllInvites() {
		return ResponseEntity.ok(controller.getAllInvites());
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDto user) {
		UserResponseDto response;
		try {
			response = controller.createUser(user);
			return ResponseEntity.ok(response);
		} catch (DuplicatedUserException | InviteNotValidException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}

	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequestDto request,
			@RequestHeader("Authorization") String token) {

		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			UserResponseDto response = controller.update(request, id, user);
			return ResponseEntity.ok(response);
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(412)
					.body(Map.of("error", true, "status", 412, "message", "error to recover token"));
		} catch (UserNotFoundException | UserNotAuthorizedException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
		controller.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/invite")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<InviteResponseDto> createInvite() {
		return ResponseEntity.ok(controller.createUserInvite());
	}
}
