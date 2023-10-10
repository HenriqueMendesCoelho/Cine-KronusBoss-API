package com.kronusboss.cine.wishlist.application.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.util.CredentialUtil;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.wishlist.adapter.controller.WishlistController;
import com.kronusboss.cine.wishlist.adapter.controller.dto.MoviesAlreadyRatedResponseDto;
import com.kronusboss.cine.wishlist.adapter.controller.dto.WishlistRequestDto;
import com.kronusboss.cine.wishlist.adapter.controller.dto.WishlistResponseDto;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistDuplicatedException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistMovieAlreadyExistsException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistNotFoundException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistUserReachedLimitException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/wishlist")
public class SpringUserWishlistController {

	@Autowired
	private WishlistController controller;

	@GetMapping
	public ResponseEntity<List<WishlistResponseDto>> listUserWishlists(@RequestHeader("Authorization") String token) {
		UserTokenDto user = CredentialUtil.getUserFromToken(token);
		List<WishlistResponseDto> response = controller.getUserWishlists(user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			WishlistResponseDto response = controller.findById(id, user);
			return ResponseEntity.ok(response);
		} catch (WishlistNotFoundException e) {
			return ResponseEntity.noContent().build();
		}

	}

	@GetMapping("/{id}/movies-rated")
	public ResponseEntity<?> findById(@PathVariable UUID id) {

		MoviesAlreadyRatedResponseDto response = controller.searchMoviesAlreadyRatedImpl(id);

		if (CollectionUtils.isEmpty(response.getMovieTmdbIds())) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);

	}

	@PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<?> createWishlist(@RequestHeader("Authorization") String token,
			@RequestParam(required = true) String name) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			return ResponseEntity.ok(controller.createUserWishlist(name, user));
		} catch (WishlistDuplicatedException | WishlistUserReachedLimitException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@PatchMapping(path = "/{id}", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<?> addMovieToWishlist(@RequestHeader("Authorization") String token, @PathVariable UUID id,
			@RequestParam(required = true) Long tmdbId) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			WishlistResponseDto response = controller.addMovieToWishlist(id, user, tmdbId);
			return ResponseEntity.ok(response);
		} catch (WishlistNotFoundException | WishlistMovieAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@PutMapping("/{id}/update")
	public ResponseEntity<?> updateWishlist(@RequestHeader("Authorization") String token, @PathVariable UUID id,
			@RequestBody @Valid WishlistRequestDto request) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			WishlistResponseDto response = controller.updateUserWishlist(request, user);
			return ResponseEntity.ok(response);
		} catch (WishlistNotFoundException | WishlistMovieAlreadyExistsException | UserNotAuthorizedException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "status", 400, "message", e.getMessage()));
		}
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity<?> deleteWishlist(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
		UserTokenDto user = CredentialUtil.getUserFromToken(token);
		controller.deleteUserWishlist(id, user);
		return ResponseEntity.ok().build();
	}
}
