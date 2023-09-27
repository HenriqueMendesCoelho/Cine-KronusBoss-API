package com.kronusboss.cine.wishlist.adapter.controller.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;

import com.kronusboss.cine.wishlist.domain.Wishlist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishlistRequestDto {

	private UUID id;

	@NotBlank
	@Length(min = 3, max = 30)
	private String name;

	private List<MovieWishlistRequestDto> moviesWishlists;
	private boolean shareable;

	public Wishlist toDomain() {
		return Wishlist.builder()
				.id(id)
				.name(name)
				.moviesWishlists(
						moviesWishlists.stream().map(MovieWishlistRequestDto::toDomain).collect(Collectors.toList()))
				.shareable(shareable)
				.build();
	}

}
