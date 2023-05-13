package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieSummaryResponseDto {

	private Long tmdbId;
	private String imdbId;
	private String portugueseTitle;
	private String englishTitle;
	private String originalTitle;
	private String director;
	private String urlImagePortuguese;
	private String urlImageEnglish;
	private String portugueseUrlTrailer;
	private String englishUrlTrailer;
	private String description;
	private List<String> genres;
	private LocalDate releaseDate;
	private Integer runtime;

	public MovieSummary toDomain() {
		return MovieSummary.builder()
				.tmdbId(tmdbId)
				.imdbId(imdbId)
				.portugueseTitle(portugueseTitle)
				.englishTitle(englishTitle)
				.originalTitle(originalTitle)
				.director(director)
				.urlImageEnglish(urlImagePortuguese)
				.portugueseUrlTrailer(portugueseUrlTrailer)
				.englishUrlTrailer(englishUrlTrailer)
				.genres(genres)
				.description(description)
				.releaseDate(releaseDate)
				.runtime(runtime)
				.build();
	}
}
