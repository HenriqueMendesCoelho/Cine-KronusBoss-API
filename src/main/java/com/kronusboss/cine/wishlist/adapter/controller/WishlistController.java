package com.kronusboss.cine.wishlist.adapter.controller;

import java.util.List;
import java.util.UUID;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.wishlist.adapter.controller.dto.WishlistRequestDto;
import com.kronusboss.cine.wishlist.adapter.controller.dto.WishlistResponseDto;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistDuplicatedException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistMovieAlreadyExistsException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistNotFoundException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistUserReachedLimitException;

import jakarta.validation.Valid;

public interface WishlistController {

	List<WishlistResponseDto> getUserWishlists(UserTokenDto request);

	WishlistResponseDto findById(UUID wishlistId, UserTokenDto user) throws WishlistNotFoundException;

	WishlistResponseDto createUserWishlist(String name, UserTokenDto user)
			throws WishlistDuplicatedException, WishlistUserReachedLimitException;

	WishlistResponseDto updateUserWishlist(@Valid WishlistRequestDto request, UserTokenDto user)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException, UserNotAuthorizedException;

	WishlistResponseDto addMovieToWishlist(UUID wishlistId, UserTokenDto user, Long tmdbId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException;

	void deleteUserWishlist(UUID wishlistId, UserTokenDto user);

}
