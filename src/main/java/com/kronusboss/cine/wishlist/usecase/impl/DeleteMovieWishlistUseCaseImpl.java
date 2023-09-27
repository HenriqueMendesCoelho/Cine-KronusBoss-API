package com.kronusboss.cine.wishlist.usecase.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.wishlist.adapter.repository.jpa.MovieWishlistRepository;
import com.kronusboss.cine.wishlist.domain.MovieWishlist;
import com.kronusboss.cine.wishlist.usecase.DeleteMovieWishlistUseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeleteMovieWishlistUseCaseImpl implements DeleteMovieWishlistUseCase {

	@Autowired
	private MovieWishlistRepository repository;

	@Scheduled(initialDelay = 10000, fixedDelayString = "P1D")
	@Override
	public void delete() {
		List<MovieWishlist> movies = repository.findAll();

		log.info("Started the validation process for deleting movies from the wishlist");

		int num = 0;
		for (MovieWishlist movie : movies) {
			if (movie.getWishlists() == null || movie.getWishlists().isEmpty()) {
				num++;
				repository.delete(movie);
			}
		}

		log.info("%s movies wishlist deleted".formatted(num));
	}

}
