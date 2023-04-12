package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenresEntity {

	List<MovieGenreEntity> genres;

}
