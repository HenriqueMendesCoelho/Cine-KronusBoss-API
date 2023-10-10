package com.kronusboss.cine.wishlist.adapter.controller.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.wishlist.adapter.controller.WishlistController;
import com.kronusboss.cine.wishlist.adapter.controller.dto.MoviesAlreadyRatedResponseDto;
import com.kronusboss.cine.wishlist.adapter.controller.dto.WishlistRequestDto;
import com.kronusboss.cine.wishlist.adapter.controller.dto.WishlistResponseDto;
import com.kronusboss.cine.wishlist.domain.Wishlist;
import com.kronusboss.cine.wishlist.usecase.CreateUserWishlistUseCase;
import com.kronusboss.cine.wishlist.usecase.DeleteUserWishlistUseCase;
import com.kronusboss.cine.wishlist.usecase.SearchMoviesAlreadyRated;
import com.kronusboss.cine.wishlist.usecase.SearchUserWishlistUseCase;
import com.kronusboss.cine.wishlist.usecase.UpdateUserWishlistUseCase;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistDuplicatedException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistMovieAlreadyExistsException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistNotFoundException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistUserReachedLimitException;

@Controller
public class WishlistControllerImpl implements WishlistController {

	@Autowired
	private SearchUserWishlistUseCase searchUserWishlistUseCase;

	@Autowired
	private CreateUserWishlistUseCase createUserWishlistUseCase;

	@Autowired
	private UpdateUserWishlistUseCase updateUserWishlistUseCase;

	@Autowired
	private DeleteUserWishlistUseCase deleteUserWishlistUseCase;

	@Autowired
	private SearchMoviesAlreadyRated searchMoviesAlreadyRated;

	@Override
	public List<WishlistResponseDto> getUserWishlists(UserTokenDto request) {
		List<Wishlist> response = searchUserWishlistUseCase.list(request.getId());
		return response.stream().map(WishlistResponseDto::new).collect(Collectors.toList());
	}

	@Override
	public WishlistResponseDto findById(UUID wishlistId, UserTokenDto user) throws WishlistNotFoundException {
		Wishlist response = searchUserWishlistUseCase.findById(wishlistId, user.getId());
		return new WishlistResponseDto(response);
	}

	@Override
	public WishlistResponseDto createUserWishlist(String name, UserTokenDto user)
			throws WishlistDuplicatedException, WishlistUserReachedLimitException {
		Wishlist response = createUserWishlistUseCase.create(name, user.getId());
		return new WishlistResponseDto(response);
	}

	@Override
	public WishlistResponseDto updateUserWishlist(WishlistRequestDto request, UserTokenDto user)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException, UserNotAuthorizedException {
		Wishlist response = updateUserWishlistUseCase.update(request.toDomain(), user.getId());
		return new WishlistResponseDto(response);
	}

	@Override
	public WishlistResponseDto addMovieToWishlist(UUID wishlistId, UserTokenDto user, Long tmdbId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException {
		Wishlist response = updateUserWishlistUseCase.addMovieToWishlist(wishlistId, user.getId(), tmdbId);
		return new WishlistResponseDto(response);
	}

	@Override
	public void deleteUserWishlist(UUID wishlistId, UserTokenDto user) {
		deleteUserWishlistUseCase.delete(wishlistId, user.getId());
	}

	@Override
	public MoviesAlreadyRatedResponseDto searchMoviesAlreadyRatedImpl(UUID wishlistId) {
		List<Movie> response = searchMoviesAlreadyRated.findMovies(wishlistId);
		return new MoviesAlreadyRatedResponseDto(response);
	}

}
