package com.kronusboss.cine.adapter.movie.controller.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kronusboss.cine.movie.domain.Movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDto {

	private UUID id;
	private String portugueseTitle;
	private String originalTitle;
	private String director;
	private String urlImage;
	private String portugueseUrlTrailer;
	private String englishUrlTrailer;
	private String description;
	private int year;
	private Long tmdbId;
	List<MovieNoteResponseDto> notes;

	public MovieResponseDto(Movie movie) {
		id = movie.getId();
		portugueseTitle = movie.getPortugueseTitle();
		originalTitle = movie.getOriginalTitle();
		director = movie.getDirector();
		urlImage = movie.getUrlImage();
		portugueseUrlTrailer = movie.getPortugueseUrlTrailer();
		englishUrlTrailer = movie.getEnglishUrlTrailer();
		description = movie.getDescription();
		year = movie.getYear();
		tmdbId = movie.getTmdbId();
		notes = movie.getNotes() != null ? movie.getNotes().stream().map(n -> new MovieNoteResponseDto(n)).collect(Collectors.toList()) : null;
	}
}
