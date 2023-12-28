package com.kronusboss.cine.wishlist.adapter.controller.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.wishlist.domain.Wishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponseDto {

	private UUID id;
	private String name;
	private List<MovieWishlistResponseDto> moviesWishlists;
	private boolean shareable;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private WishlistUserResponseDto user;

	public WishlistResponseDto(Wishlist wishlist) {
		id = wishlist.getId();
		name = wishlist.getName();
		shareable = wishlist.isShareable();
		createdAt = wishlist.getCreatedAt();
		updatedAt = wishlist.getUpdatedAt();
		if (wishlist.getMoviesWishlists() != null) {
			moviesWishlists = wishlist.getMoviesWishlists()
					.stream()
					.map(MovieWishlistResponseDto::new)
					.collect(Collectors.toList());
		}
		user = new WishlistUserResponseDto(wishlist.getUser());

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class WishlistUserResponseDto {
		public UUID id;
		public String name;

		public WishlistUserResponseDto(User user) {
			id = user.getId();
			name = user.getName();
		}
	}

}
