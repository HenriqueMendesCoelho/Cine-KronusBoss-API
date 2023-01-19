package com.kronusboss.cine.domain.movie;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kronusboss.cine.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "movie_note")
@Data
public class MovieNote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private UUID id;
	
	@Column
	private int note;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column
	private LocalDateTime updatedAt;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="movie_id", nullable=false)
	private Movie movie;

}
