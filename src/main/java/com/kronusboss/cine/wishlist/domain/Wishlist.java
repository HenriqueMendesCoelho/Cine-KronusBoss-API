package com.kronusboss.cine.wishlist.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.kronusboss.cine.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_wishlist", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "name" }) })
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist implements Serializable {

	private static final long serialVersionUID = 1763928844019419528L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private UUID id;

	@Column(length = 30)
	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "movie_wishlist_user_wishlist",
			joinColumns = { @JoinColumn(name = "wishlist_id", referencedColumnName = "id") },
			inverseJoinColumns = @JoinColumn(name = "movie_id"),
			uniqueConstraints = { @UniqueConstraint(columnNames = { "wishlist_id", "movie_id" }) })
	private List<MovieWishlist> moviesWishlists;

	@Column(nullable = false)
	private boolean shareable;

	@CreationTimestamp
	@Column
	private OffsetDateTime createdAt;

	@Column
	private OffsetDateTime updatedAt;

}
