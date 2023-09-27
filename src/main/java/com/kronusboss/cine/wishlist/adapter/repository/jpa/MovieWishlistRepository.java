package com.kronusboss.cine.wishlist.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kronusboss.cine.wishlist.domain.MovieWishlist;

public interface MovieWishlistRepository extends JpaRepository<MovieWishlist, Long> {

}
