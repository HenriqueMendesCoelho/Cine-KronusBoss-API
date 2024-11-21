package com.moviemux.wishlist.usecase;

import java.util.List;
import java.util.UUID;

import com.moviemux.movie.domain.Movie;

public interface SearchMoviesAlreadyRated {

	List<Movie> findMovies(UUID wishlistId);

}
