package com.kronusboss.cine.wishlist.domain;

import java.time.LocalDate;
import java.util.List;

import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_wishlist")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieWishlist {

	@Id
	@Column
	private Long tmdbId;

	@Column(length = 150)
	private String title;

	@Column(length = 150)
	private String titleEnglish;

	@Column(columnDefinition = "text", nullable = false)
	private String urlImage;

	@Column
	private LocalDate releaseDate;

	@ManyToMany(mappedBy = "moviesWishlists", fetch = FetchType.EAGER)
	private List<Wishlist> wishlists;

	public MovieWishlist(MovieSummary movie) {
		tmdbId = movie.getTmdbId();
		title = movie.getPortugueseTitle();
		titleEnglish = movie.getEnglishTitle();
		urlImage = movie.getUrlImagePortuguese() != null ? movie.getUrlImagePortuguese() : movie.getUrlImageEnglish();
		releaseDate = movie.getReleaseDate();
	}

}
