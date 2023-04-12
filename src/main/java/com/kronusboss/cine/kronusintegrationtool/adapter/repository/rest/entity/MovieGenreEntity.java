package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.entity;

import com.kronusboss.cine.movie.domain.MovieGenre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenreEntity {

	private Long id;
	private String name;

	public MovieGenre toDomain() {
		return MovieGenre.builder().id(id).name(name).build();
	}
}
