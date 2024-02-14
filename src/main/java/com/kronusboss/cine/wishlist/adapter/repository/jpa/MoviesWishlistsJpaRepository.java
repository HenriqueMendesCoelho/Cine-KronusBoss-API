package com.kronusboss.cine.wishlist.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kronusboss.cine.wishlist.domain.MoviesWishlists;
import com.kronusboss.cine.wishlist.domain.MoviesWishlistsKey;

public interface MoviesWishlistsJpaRepository extends JpaRepository<MoviesWishlists, MoviesWishlistsKey> {

}
