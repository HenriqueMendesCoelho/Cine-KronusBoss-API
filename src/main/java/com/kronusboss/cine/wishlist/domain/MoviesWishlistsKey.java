package com.kronusboss.cine.wishlist.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoviesWishlistsKey {

	@Column
	private UUID wishlistId;

	@Column
	private Long movieId;

}
