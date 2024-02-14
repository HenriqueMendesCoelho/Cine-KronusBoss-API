package com.kronusboss.cine.wishlist.usecase.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.wishlist.adapter.repository.MovieWishlistRepository;
import com.kronusboss.cine.wishlist.adapter.repository.MoviesWishlistsRepository;
import com.kronusboss.cine.wishlist.adapter.repository.WishlistRepository;
import com.kronusboss.cine.wishlist.domain.MovieWishlist;
import com.kronusboss.cine.wishlist.domain.MoviesWishlists;
import com.kronusboss.cine.wishlist.domain.MoviesWishlistsKey;
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
	private MoviesWishlistsRepository moviesWishlistsRepository;

	@Autowired
	private KronusIntegrationToolRepository kitRepository;

	@Override
	public Wishlist update(Wishlist userWishlist, UUID userId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException, UserNotAuthorizedException {
		Wishlist userWishlistToUpdate = repository.findById(userWishlist.getId()).orElse(null);

		if (userWishlistToUpdate == null || userWishlistToUpdate.getMoviesWishlists().size() > LIMIT) {
			throw new WishlistNotFoundException();
		}

		if (!userWishlistToUpdate.getUser().getId().equals(userId)) {
			throw new UserNotAuthorizedException();
		}

		List<MovieWishlist> moviesToCheck = userWishlistToUpdate.getMoviesWishlists()
				.stream()
				.map(MoviesWishlists::getMovieWishlist)
				.toList();
		createIfNotExistMovieWishlist(moviesToCheck);

		userWishlistToUpdate = userWishlistToUpdate.toBuilder()
				.name(userWishlist.getName())
				.moviesWishlists(setOrder(userWishlist.getMoviesWishlists()))
				.shareable(userWishlist.isShareable())
				.updatedAt(OffsetDateTime.now())
				.build();

		try {
			moviesWishlistsRepository.saveAll(userWishlistToUpdate.getMoviesWishlists());
			return repository.save(userWishlistToUpdate);
		} catch (DataIntegrityViolationException e) {
			throw new WishlistMovieAlreadyExistsException(userWishlist.getName());
		}
	}

	@Override
	public Wishlist addMovieToWishlist(UUID wishlistId, UUID userId, Long tmdbId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException {
		Wishlist userWishlistToUpdate = repository.findById(wishlistId).orElse(null);

		if (userWishlistToUpdate == null || userWishlistToUpdate.getMoviesWishlists().size() >= LIMIT) {
			throw new WishlistNotFoundException();
		}

		MovieWishlist movieToAdd = MovieWishlist.builder().tmdbId(tmdbId).build();
		List<MovieWishlist> movies = createIfNotExistMovieWishlist(List.of(movieToAdd));
		userWishlistToUpdate.getMoviesWishlists().addAll(createMoviesWishlists(wishlistId, movies));
		List<MoviesWishlists> moviesWishlistsDistict = userWishlistToUpdate.getMoviesWishlists()
				.stream()
				.distinct()
				.toList();
		userWishlistToUpdate.setUpdatedAt(OffsetDateTime.now());

		try {
			List<MoviesWishlists> list = moviesWishlistsRepository.saveAll(setOrder(moviesWishlistsDistict));
			userWishlistToUpdate.setMoviesWishlists(list);
			return repository.save(userWishlistToUpdate);
		} catch (DataIntegrityViolationException e) {
			throw new WishlistMovieAlreadyExistsException(userWishlistToUpdate.getName());
		}

	}

	private List<MovieWishlist> createIfNotExistMovieWishlist(List<MovieWishlist> movies) {
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

	private List<MoviesWishlists> setOrder(List<MoviesWishlists> moviesToAdd) {
		for (int i = 0; i < moviesToAdd.size(); i++) {
			moviesToAdd.get(i).setWishlistOrder(i + 1);
		}

		return moviesToAdd;
	}

	private List<MoviesWishlists> createMoviesWishlists(UUID wishlistId, List<MovieWishlist> movies) {

		return movies.stream()
				.map(m -> MoviesWishlists.builder()
						.id(new MoviesWishlistsKey(wishlistId, m.getTmdbId()))
						.wishlist(Wishlist.builder().id(wishlistId).build())
						.movieWishlist(MovieWishlist.builder().tmdbId(m.getTmdbId()).build())
						.build())
				.toList();
	}

}
