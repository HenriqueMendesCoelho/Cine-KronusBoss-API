package com.moviemux.wishlist.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviemux.wishlist.domain.MoviesWishlists;
import com.moviemux.wishlist.domain.MoviesWishlistsKey;

public interface MoviesWishlistsJpaRepository extends JpaRepository<MoviesWishlists, MoviesWishlistsKey> {

}
