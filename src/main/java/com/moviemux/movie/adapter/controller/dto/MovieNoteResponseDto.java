package com.moviemux.movie.adapter.controller.dto;

import java.time.OffsetDateTime;

import com.moviemux.movie.domain.MovieNote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieNoteResponseDto {

	private Integer note;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private MovieNoteUserResponseDto user;

	public MovieNoteResponseDto(MovieNote movieNote) {
		note = movieNote.getNote();
		createdAt = movieNote.getCreatedAt();
		updatedAt = movieNote.getUpdatedAt();
		user = new MovieNoteUserResponseDto(movieNote.getUser());
	}

}
