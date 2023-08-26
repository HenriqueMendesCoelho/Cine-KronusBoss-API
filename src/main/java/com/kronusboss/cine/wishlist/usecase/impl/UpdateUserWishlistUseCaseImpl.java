package com.kronusboss.cine.wishlist.usecase.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.wishlist.adapter.repository.jpa.MovieWishlistRepository;
import com.kronusboss.cine.wishlist.adapter.repository.jpa.WishlistRepository;
import com.kronusboss.cine.wishlist.domain.MovieWishlist;
import com.kronusboss.cine.wishlist.domain.Wishlist;
import com.kronusboss.cine.wishlist.usecase.UpdateUserWishlistUseCase;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistMovieAlreadyExistsException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistNotFoundException;

@Component
public class UpdateUserWishlistUseCaseImpl implements UpdateUserWishlistUseCase {

	private static final int LIMIT = 100;

	@Autowired
	private WishlistRepository repository;

	@Autowired
	private MovieWishlistRepository movieWishlistRepository;

	@Autowired
	private KronusIntegrationToolRepository kitRepository;

	@Override
	public Wishlist update(Wishlist userWishlist, UUID userId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException, UserNotAuthorizedException {
		Wishlist userWishlistToUpdate = repository.findById(userWishlist.getId()).orElse(null);

		if (userWishlistToUpdate == null) {
			throw new WishlistNotFoundException();
		}

		if (userWishlistToUpdate.getMoviesWishlists().size() > LIMIT) {
			throw new WishlistNotFoundException();
		}

		if (userWishlistToUpdate.getUser().getId() != userId) {
			throw new UserNotAuthorizedException();
		}

		createMovieWishlist(userWishlistToUpdate.getMoviesWishlists());

		userWishlistToUpdate = userWishlistToUpdate.toBuilder()
				.name(userWishlist.getName())
				.moviesWishlists(userWishlist.getMoviesWishlists())
				.shareable(userWishlist.isShareable())
				.updatedAt(LocalDateTime.now())
				.build();

		try {
			return repository.save(userWishlistToUpdate);
		} catch (DataIntegrityViolationException e) {
			throw new WishlistMovieAlreadyExistsException(userWishlist.getName());
		}
	}

	@Override
	public Wishlist addMovieToWishlist(UUID wishlistId, UUID userId, Long tmdbId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException {
		Wishlist userWishlistToUpdate = repository.findById(wishlistId).orElse(null);

		if (userWishlistToUpdate == null) {
			throw new WishlistNotFoundException();
		}

		if (userWishlistToUpdate.getMoviesWishlists().size() >= LIMIT) {
			throw new WishlistNotFoundException();
		}

		MovieWishlist movieToAdd = MovieWishlist.builder().tmdbId(tmdbId).build();
		List<MovieWishlist> movies = createMovieWishlist(List.of(movieToAdd));

		userWishlistToUpdate.getMoviesWishlists().addAll(movies);
		userWishlistToUpdate.setUpdatedAt(LocalDateTime.now());

		try {
			return repository.save(userWishlistToUpdate);
		} catch (DataIntegrityViolationException e) {
			throw new WishlistMovieAlreadyExistsException(userWishlistToUpdate.getName());
		}

	}

	private List<MovieWishlist> createMovieWishlist(List<MovieWishlist> movies) {
		List<MovieWishlist> result = new ArrayList<>();
		for (MovieWishlist movie : movies) {
			MovieWishlist exists = movieWishlistRepository.findById(movie.getTmdbId()).orElse(null);

			if (exists != null) {
				result.add(exists);
				continue;
			}

			MovieSummary movieCreate = kitRepository.movieSummary(movie.getTmdbId());

			if (movieCreate == null) {
				continue;
			}

			result.add(movieWishlistRepository.saveAndFlush(new MovieWishlist(movieCreate)));
		}
		return result;
	}

}
