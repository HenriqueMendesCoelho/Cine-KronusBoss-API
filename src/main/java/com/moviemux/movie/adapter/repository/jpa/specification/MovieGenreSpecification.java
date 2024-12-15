package com.moviemux.movie.adapter.repository.jpa.specification;

import java.util.List;

import com.moviemux.movie.domain.MovieGenre;

public interface MovieGenreSpecification {

	List<MovieGenre> findAllGenresHaveMovieAssociated();

}
