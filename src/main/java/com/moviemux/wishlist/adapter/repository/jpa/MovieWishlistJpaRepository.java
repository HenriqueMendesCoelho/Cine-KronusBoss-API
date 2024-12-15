package com.moviemux.wishlist.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviemux.wishlist.domain.MovieWishlist;

public interface MovieWishlistJpaRepository extends JpaRepository<MovieWishlist, Long> {

}
