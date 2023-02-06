package com.kronusboss.cine.adapter.controller.movie.dto;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;

public class MovieRequestDto {
	
	@NotBlank
	private String portugueseTitle;
	
	@NotBlank
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
	
	@NotBlank
	private int year;
	
	@Nullable
	private Long tmdbId;
	
}
