package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kronusboss.cine.kronusintegrationtool.domain.ResultSearch;

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
public class ResultSearchEntity {

	private boolean adult;
	private String backdropPath;
	private List<Integer> genreIds;
	private Integer id;
	private String originalLanguage;
	private String originalTitle;
	private String overview;
	private String popularity;
	private String posterPath;
	private LocalDate releaseDate;
	private String title;
	private boolean video;
	private double voteAverage;
	private Integer voteCount;

	public ResultSearch toDomain() {
		return ResultSearch.builder()
				.adult(adult)
				.backdropPath(backdropPath)
				.genreIds(genreIds)
				.id(id)
				.originalLanguage(originalLanguage)
				.originalTitle(originalTitle)
				.overview(overview)
				.popularity(popularity)
				.posterPath(posterPath)
				.releaseDate(releaseDate)
				.title(title)
				.video(video)
				.voteAverage(voteAverage)
				.voteCount(voteCount)
				.build();
	}

}
