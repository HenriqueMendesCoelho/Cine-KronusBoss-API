package com.kronusboss.cine.wishlist.usecase;

import java.util.List;
import java.util.UUID;

import com.kronusboss.cine.movie.domain.Movie;

public interface SearchMoviesAlreadyRated {

	List<Movie> findMovies(UUID wishlistId);

}
