package com.kronusboss.cine.movie.adapter.repository.jpa.specification;

import java.util.List;

import com.kronusboss.cine.movie.domain.MovieGenre;

public interface MovieGenreSpecification {

	List<MovieGenre> findAllGenresHaveMovieAssociated();

}
