package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;

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
public class MovieSearchResponseDto {

	private Integer page;
	private List<ResultSearchResponseDto> results;
	private Integer totalPages;
	private Integer totalResults;

	public MovieSearch toDomain() {
		return MovieSearch.builder()
				.page(page)
				.results(this.results.stream().map(ResultSearchResponseDto::toDomain).collect(Collectors.toList()))
				.totalPages(totalPages)
				.totalResults(totalResults)
				.build();
	}

}
