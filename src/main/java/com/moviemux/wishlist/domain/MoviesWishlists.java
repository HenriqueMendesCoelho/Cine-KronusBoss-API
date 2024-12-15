package com.moviemux.wishlist.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_wishlist_user_wishlist",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "wishlist_id", "movie_id" }) })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MoviesWishlists {

	@ManyToOne
	@MapsId("wishlistId")
	@JoinColumn(name = "wishlist_id")
	Wishlist wishlist;

	@ManyToOne
	@MapsId("movieId")
	@JoinColumn(name = "movie_id")
	MovieWishlist movieWishlist;

	@EmbeddedId
	private MoviesWishlistsKey id;

	@Column
	private Integer wishlistOrder;

}
