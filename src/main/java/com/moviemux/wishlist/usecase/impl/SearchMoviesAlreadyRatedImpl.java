package com.moviemux.wishlist.usecase.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.movie.adapter.repository.MovieRepository;
import com.moviemux.movie.domain.Movie;
import com.moviemux.wishlist.adapter.repository.WishlistRepository;
import com.moviemux.wishlist.domain.Wishlist;
import com.moviemux.wishlist.usecase.SearchMoviesAlreadyRated;

@Component
public class SearchMoviesAlreadyRatedImpl implements SearchMoviesAlreadyRated {

	@Autowired
	private WishlistRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> findMovies(UUID wishlistId) {
		Wishlist wishlist = repository.findById(wishlistId).orElse(null);
		if (wishlist == null || CollectionUtils.isEmpty(wishlist.getMoviesWishlists())) {
			return new ArrayList<>();
		}
		List<Movie> moviesRated = movieRepository.findAll();

		return moviesRated.stream()
				.filter(m -> wishlist.getMoviesWishlists()
						.stream()
						.anyMatch(mw -> mw.getMovieWishlist().getTmdbId().equals(m.getTmdbId())))
				.toList();
	}

}
