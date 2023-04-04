package com.kronusboss.cine.adapter.movie.controller.dto;

import java.time.LocalDate;
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
	private String englishTitle;
	private String originalTitle;
	private String director;
	private String urlImage;
	private String portugueseUrlTrailer;
	private String englishUrlTrailer;
	private String description;
	private LocalDate releaseDate;
	private Long tmdbId;
	private String imdbId;
	private Integer runtime;
	private UUID userId;
	List<MovieNoteResponseDto> notes;
	List<MovieGenreResponseDto> genres;

	public MovieResponseDto(Movie movie) {
		id = movie.getId();
		portugueseTitle = movie.getPortugueseTitle();
		englishTitle = movie.getEnglishTitle();
		originalTitle = movie.getOriginalTitle();
		director = movie.getDirector();
		urlImage = movie.getUrlImage();
		portugueseUrlTrailer = movie.getPortugueseUrlTrailer();
		englishUrlTrailer = movie.getEnglishUrlTrailer();
		description = movie.getDescription();
		releaseDate = movie.getReleaseDate();
		tmdbId = movie.getTmdbId();
		if (movie.getNotes() != null) {
			notes = movie.getNotes().stream().map(MovieNoteResponseDto::new).collect(Collectors.toList());
		}
		genres = movie.getGenres().stream().map(MovieGenreResponseDto::new).collect(Collectors.toList());
		imdbId = movie.getImdbId();
		userId = movie.getUser() != null ? movie.getUser().getId() : null;
		runtime = movie.getRuntime();
	}
}
