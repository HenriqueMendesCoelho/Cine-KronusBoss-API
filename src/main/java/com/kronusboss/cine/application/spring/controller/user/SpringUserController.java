package com.kronusboss.cine.application.spring.controller.user;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.user.controller.UserController;
import com.kronusboss.cine.adapter.user.controller.dto.InviteResponseDto;
import com.kronusboss.cine.adapter.user.controller.dto.UserRequestDto;
import com.kronusboss.cine.adapter.user.controller.dto.UserResponseAdmDto;
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
	public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			UserResponseDto response = controller.getUserByEmail(user, user.getLogin());
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		} catch (UserNotAuthorizedException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
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

	@PatchMapping(path = "/update", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<?> updateUserProfile(@RequestParam(required = true) String name,
			@RequestParam(required = true) String email, @RequestParam(required = true) boolean notify,
			@RequestHeader("Authorization") String token) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			UserResponseDto response = controller.updateUserProfile(user.getId(), name, email, notify);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.noContent().build();
		}
	}

	@PatchMapping(path = "/p/update", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<?> updateUserPassword(@RequestParam(required = true) String password,
			@RequestParam(required = true) String newPassword, @RequestHeader("Authorization") String token) {

		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			UserResponseDto response = controller.updateUserPassoword(user.getId(), password, newPassword);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
		} catch (UserNotAuthorizedException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@GetMapping("/adm")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> getUserAdm(@RequestParam(required = true) @Valid String email,
			@RequestHeader("Authorization") String token) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			UserResponseAdmDto response = controller.getUserByEmailAdm(user, email);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.noContent().build();
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

	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequestDto request,
			@RequestHeader("Authorization") String token) {

		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			UserResponseDto response = controller.update(request, id, user);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException | UserNotAuthorizedException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@PatchMapping("/{id}/promote")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> promoteUser(@PathVariable UUID id) {

		try {
			UserResponseAdmDto response = controller.promoteUserToAdmin(id);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@PatchMapping("/{id}/demote")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> demoteUser(@PathVariable UUID id) {

		try {
			UserResponseAdmDto response = controller.demoteUserToAdmin(id);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@PatchMapping("/{id}/block")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> blockUser(@PathVariable UUID id) {

		try {
			UserResponseAdmDto response = controller.blockUser(id);
			return ResponseEntity.ok(response);
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@PostMapping("/{id}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> blockUserAdm(@PathVariable UUID id) {
		controller.delete(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
		controller.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/invite")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<List<InviteResponseDto>> listAllInvites() {
		return ResponseEntity.ok(controller.getAllInvites());
	}

	@PostMapping("/invite")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<InviteResponseDto> createInvite() {
		return ResponseEntity.ok(controller.createUserInvite());
	}

	@DeleteMapping("/invite/{code}/delete")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> deleteInvite(@PathVariable String code) {
		controller.deleteInvite(code);
		return ResponseEntity.ok().build();
	}
}
