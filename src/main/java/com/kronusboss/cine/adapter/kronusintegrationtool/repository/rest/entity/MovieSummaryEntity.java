package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MovieSummaryEntity {

	@JsonProperty("tmdb_id")
	private Long tmdbId;

	@JsonProperty("imdb_id")
	private String imdbId;

	@JsonProperty("portuguese_title")
	private String portugueseTitle;

	@JsonProperty("english_title")
	private String englishTitle;

	@JsonProperty("original_title")
	private String originalTitle;

	private String director;

	@JsonProperty("url_image_portuguese")
	private String urlImagePortuguese;

	@JsonProperty("url_image_english")
	private String urlImageEnglish;

	@JsonProperty("portuguese_url_trailer")
	private String portugueseUrlTrailer;

	@JsonProperty("english_url_trailer")
	private String englishUrlTrailer;

	private String description;

	private List<String> genres;

	@JsonProperty("release_date")
	private LocalDate releaseDate;

	public MovieSummary toDomain() {
		return MovieSummary.builder().tmdbId(tmdbId).imdbId(imdbId).portugueseTitle(portugueseTitle)
				.englishTitle(englishTitle).originalTitle(originalTitle).director(director)
				.urlImageEnglish(urlImagePortuguese).portugueseUrlTrailer(portugueseUrlTrailer)
				.englishUrlTrailer(englishUrlTrailer).genres(genres).description(description).releaseDate(releaseDate)
				.build();
	}
}
