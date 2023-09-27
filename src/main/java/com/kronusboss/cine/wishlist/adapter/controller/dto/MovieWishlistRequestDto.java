package com.kronusboss.cine.wishlist.adapter.controller.dto;

import java.time.LocalDate;

import com.kronusboss.cine.wishlist.domain.MovieWishlist;

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

	public MovieWishlist toDomain() {
		return MovieWishlist.builder()
				.tmdbId(tmdbId)
				.title(title)
				.titleEnglish(titleEnglish)
				.releaseDate(releaseDate)
				.build();
	}

}
