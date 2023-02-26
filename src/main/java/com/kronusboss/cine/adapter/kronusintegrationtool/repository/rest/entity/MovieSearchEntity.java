package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class MovieSearchEntity {

	private Integer page;

	private List<ResultSearchEntity> results;

	@JsonProperty("total_pages")
	private Integer totalPages;

	@JsonProperty("total_results")
	private Integer totalResults;

}
