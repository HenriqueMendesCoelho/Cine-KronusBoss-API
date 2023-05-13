package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenresResponseDto {

	List<MovieGenreResponseDto> genres;

}
