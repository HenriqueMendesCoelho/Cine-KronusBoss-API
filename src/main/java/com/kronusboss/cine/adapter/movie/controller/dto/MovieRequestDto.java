package com.kronusboss.cine.adapter.movie.controller.dto;

import java.time.LocalDate;

import com.kronusboss.cine.movie.domain.Movie;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDto {
	
	@NotBlank
	private String portugueseTitle;
	
	@NotBlank
	private String englishTitle;
	
	private String originalTitle;
	
	@NotBlank
	private String director;
	
	@NotBlank
	private String urlImage;
	
	@NotBlank
	private String portugueseUrlTrailer;
	
	@NotBlank
	private String englishUrlTrailer;
	
	@NotBlank
	private String description;

	private LocalDate releaseDate;
	
	@Nullable
	private Long tmdbId;
	
	public Movie toDomain() {
		return Movie
				.builder()
				.portugueseTitle(portugueseTitle)
				.originalTitle(originalTitle)
				.director(director)
				.urlImage(urlImage)
				.portugueseUrlTrailer(portugueseUrlTrailer)
				.englishUrlTrailer(englishUrlTrailer)
				.description(description)
				.releaseDate(releaseDate)
				.tmdbId(tmdbId)
				.build();
	}
}
