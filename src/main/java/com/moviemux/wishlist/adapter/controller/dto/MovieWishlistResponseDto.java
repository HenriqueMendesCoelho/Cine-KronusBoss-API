package com.moviemux.wishlist.adapter.controller.dto;

import java.time.LocalDate;

import com.moviemux.wishlist.domain.MovieWishlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieWishlistResponseDto {

	private Long tmdbId;
	private String title;
	private String titleEnglish;
	private String urlImage;
	private LocalDate releaseDate;

	public MovieWishlistResponseDto(MovieWishlist movie) {
		tmdbId = movie.getTmdbId();
		title = movie.getTitle();
		titleEnglish = movie.getTitleEnglish();
		urlImage = movie.getUrlImage();
		releaseDate = movie.getReleaseDate();
	}

}
