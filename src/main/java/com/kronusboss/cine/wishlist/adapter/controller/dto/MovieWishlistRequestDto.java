package com.kronusboss.cine.wishlist.adapter.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.kronusboss.cine.wishlist.domain.MovieWishlist;
import com.kronusboss.cine.wishlist.domain.MoviesWishlists;
import com.kronusboss.cine.wishlist.domain.MoviesWishlistsKey;
import com.kronusboss.cine.wishlist.domain.Wishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieWishlistRequestDto {

	private Long tmdbId;
	private String title;
	private String titleEnglish;
	private String urlImage;
	private LocalDate releaseDate;

	public MoviesWishlists toDomain(UUID wishlistId) {
		return MoviesWishlists.builder()
				.id(new MoviesWishlistsKey(wishlistId, tmdbId))
				.wishlist(Wishlist.builder().id(wishlistId).build())
				.movieWishlist(MovieWishlist.builder()
						.tmdbId(tmdbId)
						.title(title)
						.titleEnglish(titleEnglish)
						.releaseDate(releaseDate)
						.build())
				.build();
	}

}
