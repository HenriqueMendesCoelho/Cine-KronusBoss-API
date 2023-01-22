package com.kronusboss.cine.domain.movie;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kronusboss.cine.domain.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
