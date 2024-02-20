package com.kronusboss.cine.wishlist.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_wishlist_user_wishlist",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "wishlist_id", "movie_id" }),
				@UniqueConstraint(columnNames = { "movie_id", "wishlist_order" }) })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MoviesWishlists {

	@EmbeddedId
	private MoviesWishlistsKey id;

	@ManyToOne
	@MapsId("wishlistId")
	@JoinColumn(name = "wishlist_id")
	Wishlist wishlist;

	@ManyToOne
	@MapsId("movieId")
	@JoinColumn(name = "movie_id")
	MovieWishlist movieWishlist;

	@Column
	private Integer wishlistOrder;

}
